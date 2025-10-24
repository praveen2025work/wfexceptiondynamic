package com.example.exceptionworkflow.controller;

import com.example.exceptionworkflow.entity.WorkflowInstance;
import com.example.exceptionworkflow.entity.WorkflowMetadata;
import com.example.exceptionworkflow.entity.ExceptionAccess;
import com.example.exceptionworkflow.entity.EmailTemplate;
import com.example.exceptionworkflow.repository.WorkflowInstanceRepository;
import com.example.exceptionworkflow.repository.WorkflowMetadataRepository;
import com.example.exceptionworkflow.repository.ExceptionAccessRepository;
import com.example.exceptionworkflow.repository.EmailTemplateRepository;
import com.example.exceptionworkflow.repository.WorkflowSlaConfigRepository;
import com.example.exceptionworkflow.entity.WorkflowSlaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workflow")
@CrossOrigin(origins = "*")
public class WorkflowController {

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private WorkflowMetadataRepository workflowMetadataRepository;

    @Autowired
    private ExceptionAccessRepository exceptionAccessRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private WorkflowSlaConfigRepository workflowSlaConfigRepository;

    /**
     * Start a new workflow for an exception
     */
    @PostMapping("/start")
    public ResponseEntity<?> startWorkflow(@RequestBody WorkflowStartRequest request) {
        try {
            // Get workflow metadata for the first step
            Optional<WorkflowMetadata> firstStepMetadata = workflowMetadataRepository
                .findByWorkflowIdAndStepCode(request.getWorkflowId(), request.getStepCode());
            
            if (!firstStepMetadata.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Workflow metadata not found for workflow ID: " + request.getWorkflowId() + 
                          " and step: " + request.getStepCode());
            }

            WorkflowMetadata metadata = firstStepMetadata.get();
            
            // Get assignee for the role
            String assignedToBrid = getAssigneeForRole(metadata.getRole());
            if (assignedToBrid == null) {
                return ResponseEntity.badRequest()
                    .body("No assignee found for role: " + metadata.getRole());
            }

            // Get SLA configuration
            Optional<WorkflowSlaConfig> slaConfig = workflowSlaConfigRepository
                .findByWorkflowIdAndStepCode(request.getWorkflowId(), request.getStepCode());

            // Create workflow instance
            WorkflowInstance instance = new WorkflowInstance();
            instance.setExceptionId(request.getExceptionId());
            instance.setWorkflowId(request.getWorkflowId());
            instance.setCurrentStep(request.getStepCode());
            instance.setCurrentStepSeq(1);
            instance.setStatus("IN_PROGRESS");
            instance.setActiveStepFlag("Y");
            instance.setAssignedToBrid(assignedToBrid);
            instance.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
            instance.setComments(request.getComments());
            
            if (slaConfig.isPresent()) {
                instance.setSlaHours(slaConfig.get().getSlaHours());
                instance.setEmailTemplateId(slaConfig.get().getEmailTemplateId());
                instance.setTriggerOn(slaConfig.get().getTriggerOn());
            }

            WorkflowInstance savedInstance = workflowInstanceRepository.save(instance);
            
            // Send notification email
            sendNotificationEmail(savedInstance, metadata);

            return ResponseEntity.ok(new WorkflowResponse(
                "Workflow started successfully",
                savedInstance.getInstanceId(),
                savedInstance.getExceptionId(),
                savedInstance.getCurrentStep(),
                savedInstance.getStatus()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error starting workflow: " + e.getMessage());
        }
    }

    /**
     * Transition workflow to next step based on action
     */
    @PostMapping("/transition")
    public ResponseEntity<?> transitionWorkflow(@RequestBody WorkflowTransitionRequest request) {
        try {
            // Get current active workflow instance
            Optional<WorkflowInstance> currentInstance = workflowInstanceRepository
                .findByExceptionIdAndActiveStepFlag(request.getExceptionId(), "Y");
            
            if (!currentInstance.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("No active workflow found for exception: " + request.getExceptionId());
            }

            WorkflowInstance instance = currentInstance.get();
            
            // Get current step metadata
            Optional<WorkflowMetadata> currentMetadata = workflowMetadataRepository
                .findByWorkflowIdAndStepCode(instance.getWorkflowId(), instance.getCurrentStep());
            
            if (!currentMetadata.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Workflow metadata not found for current step: " + instance.getCurrentStep());
            }

            WorkflowMetadata metadata = currentMetadata.get();
            
            // Validate action is allowed
            if (!isActionAllowed(metadata.getActionsAllowed(), request.getAction())) {
                return ResponseEntity.badRequest()
                    .body("Action '" + request.getAction() + "' not allowed for current step: " + instance.getCurrentStep());
            }

            // Mark current step as completed
            instance.setStatus("COMPLETED");
            instance.setActiveStepFlag("N");
            instance.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
            workflowInstanceRepository.save(instance);

            // Determine next step based on action
            String nextStep = determineNextStep(metadata, request.getAction());
            
            if (nextStep == null || "END".equals(nextStep)) {
                return ResponseEntity.ok(new WorkflowResponse(
                    "Workflow completed successfully",
                    instance.getInstanceId(),
                    instance.getExceptionId(),
                    "END",
                    "CLOSED"
                ));
            }

            // Get next step metadata
            Optional<WorkflowMetadata> nextMetadata = workflowMetadataRepository
                .findByWorkflowIdAndStepCode(instance.getWorkflowId(), nextStep);
            
            if (!nextMetadata.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Next step metadata not found: " + nextStep);
            }

            // Get assignee for next step
            String nextAssignee = getAssigneeForRole(nextMetadata.get().getRole());
            if (nextAssignee == null) {
                return ResponseEntity.badRequest()
                    .body("No assignee found for role: " + nextMetadata.get().getRole());
            }

            // Get SLA configuration for next step
            Optional<WorkflowSlaConfig> nextSlaConfig = workflowSlaConfigRepository
                .findByWorkflowIdAndStepCode(instance.getWorkflowId(), nextStep);

            // Create new workflow instance for next step
            WorkflowInstance nextInstance = new WorkflowInstance();
            nextInstance.setExceptionId(instance.getExceptionId());
            nextInstance.setWorkflowId(instance.getWorkflowId());
            nextInstance.setCurrentStep(nextStep);
            nextInstance.setCurrentStepSeq(instance.getCurrentStepSeq() + 1);
            nextInstance.setStatus("IN_PROGRESS");
            nextInstance.setActiveStepFlag("Y");
            nextInstance.setAssignedToBrid(nextAssignee);
            nextInstance.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
            nextInstance.setComments(request.getComments());
            
            if (nextSlaConfig.isPresent()) {
                nextInstance.setSlaHours(nextSlaConfig.get().getSlaHours());
                nextInstance.setEmailTemplateId(nextSlaConfig.get().getEmailTemplateId());
                nextInstance.setTriggerOn(nextSlaConfig.get().getTriggerOn());
            }

            WorkflowInstance savedNextInstance = workflowInstanceRepository.save(nextInstance);
            
            // Send notification email for next step
            sendNotificationEmail(savedNextInstance, nextMetadata.get());

            return ResponseEntity.ok(new WorkflowResponse(
                "Workflow transitioned successfully",
                savedNextInstance.getInstanceId(),
                savedNextInstance.getExceptionId(),
                savedNextInstance.getCurrentStep(),
                savedNextInstance.getStatus()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error transitioning workflow: " + e.getMessage());
        }
    }

    /**
     * Get current workflow status for an exception
     */
    @GetMapping("/status/{exceptionId}")
    public ResponseEntity<?> getWorkflowStatus(@PathVariable String exceptionId) {
        try {
            List<WorkflowInstance> instances = workflowInstanceRepository.findByExceptionId(exceptionId);
            
            if (instances.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(instances);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving workflow status: " + e.getMessage());
        }
    }

    /**
     * Get active workflow for an exception
     */
    @GetMapping("/active/{exceptionId}")
    public ResponseEntity<?> getActiveWorkflow(@PathVariable String exceptionId) {
        try {
            Optional<WorkflowInstance> activeInstance = workflowInstanceRepository
                .findByExceptionIdAndActiveStepFlag(exceptionId, "Y");
            
            if (!activeInstance.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(activeInstance.get());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving active workflow: " + e.getMessage());
        }
    }

    /**
     * Get workflow metadata for a workflow
     */
    @GetMapping("/metadata/{workflowId}")
    public ResponseEntity<?> getWorkflowMetadata(@PathVariable Long workflowId) {
        try {
            List<WorkflowMetadata> metadata = workflowMetadataRepository.findByWorkflowId(workflowId);
            return ResponseEntity.ok(metadata);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving workflow metadata: " + e.getMessage());
        }
    }

    /**
     * Check if workflow exists for an exception
     */
    @GetMapping("/exists/{exceptionId}")
    public ResponseEntity<?> checkWorkflowExists(@PathVariable String exceptionId) {
        try {
            List<WorkflowInstance> instances = workflowInstanceRepository.findByExceptionId(exceptionId);
            boolean exists = !instances.isEmpty();
            
            return ResponseEntity.ok(new WorkflowExistsResponse(
                exists,
                exists ? instances.size() : 0,
                exists ? instances.get(instances.size() - 1).getStatus() : null
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error checking workflow existence: " + e.getMessage());
        }
    }

    /**
     * Get available actions for current user based on role and workflow state
     */
    @GetMapping("/actions/{exceptionId}")
    public ResponseEntity<?> getAvailableActions(@PathVariable String exceptionId, 
                                                @RequestParam(required = false) String userRole) {
        try {
            List<WorkflowInstance> instances = workflowInstanceRepository.findByExceptionId(exceptionId);
            
            if (instances.isEmpty()) {
                // No workflow exists - can start workflow
                return ResponseEntity.ok(new AvailableActionsResponse(
                    false, // no active workflow
                    "START", // action to start workflow
                    new String[]{"START"}, // available actions
                    null, // current step
                    null // current role
                ));
            }
            
            // Find active workflow instance
            Optional<WorkflowInstance> activeInstance = instances.stream()
                .filter(instance -> "Y".equals(instance.getActiveStepFlag()))
                .findFirst();
            
            if (!activeInstance.isPresent()) {
                // Workflow completed
                return ResponseEntity.ok(new AvailableActionsResponse(
                    false, // no active workflow
                    "COMPLETED", // workflow status
                    new String[]{"VIEW"}, // available actions
                    null, // current step
                    null // current role
                ));
            }
            
            WorkflowInstance instance = activeInstance.get();
            
            // Get metadata for current step
            Optional<WorkflowMetadata> metadata = workflowMetadataRepository
                .findByWorkflowIdAndStepCode(instance.getWorkflowId(), instance.getCurrentStep());
            
            if (!metadata.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Metadata not found for current step: " + instance.getCurrentStep());
            }
            
            // Check if user role matches current step role
            boolean canAct = userRole == null || userRole.equals(metadata.get().getRole());
            
            // Parse available actions
            String[] availableActions = parseActions(metadata.get().getActionsAllowed());
            
            return ResponseEntity.ok(new AvailableActionsResponse(
                true, // has active workflow
                instance.getStatus(), // current status
                canAct ? availableActions : new String[]{"VIEW"}, // available actions
                instance.getCurrentStep(), // current step
                metadata.get().getRole() // current role
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error getting available actions: " + e.getMessage());
        }
    }

    /**
     * Get enhanced metadata with parsed actions for UI dropdowns
     */
    @GetMapping("/metadata-enhanced/{workflowId}")
    public ResponseEntity<?> getEnhancedWorkflowMetadata(@PathVariable Long workflowId) {
        try {
            List<WorkflowMetadata> metadataList = workflowMetadataRepository.findByWorkflowId(workflowId);
            
            List<EnhancedWorkflowMetadata> enhancedMetadata = metadataList.stream()
                .map(metadata -> new EnhancedWorkflowMetadata(
                    metadata.getWorkflowId(),
                    metadata.getStepCode(),
                    metadata.getStepName(),
                    metadata.getRole(),
                    parseActions(metadata.getActionsAllowed()),
                    parseNextSteps(metadata.getNextStepOnSubmit()),
                    parseNextSteps(metadata.getNextStepOnApprove()),
                    parseNextSteps(metadata.getNextStepOnReject()),
                    metadata.getDescription()
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(enhancedMetadata);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving enhanced workflow metadata: " + e.getMessage());
        }
    }

    /**
     * Initialize sample data for testing
     */
    @PostMapping("/init-data")
    public ResponseEntity<?> initializeData() {
        try {
            // Insert workflow metadata
            WorkflowMetadata step1 = new WorkflowMetadata(101L, "STEP1", "FO Raises Challenge", "FO_OWNER", 
                "SUBMIT", "STEP2,STEP3", null, null, 
                "FO owner raises a challenge for exception and can submit either to FO Business or Reg Policy for review");
            workflowMetadataRepository.save(step1);

            WorkflowMetadata step2 = new WorkflowMetadata(101L, "STEP2", "FO Business Review", "FO_BUSINESS", 
                "APPROVE, REJECT", null, "STEP4", "STEP3", 
                "FO Business reviews the challenge and either approves (goes to FO Owner) or rejects (goes to Reg Policy)");
            workflowMetadataRepository.save(step2);

            WorkflowMetadata step3 = new WorkflowMetadata(101L, "STEP3", "Reg Policy Hypothesis Review", "REG_POLICY", 
                "APPROVE, REJECT", null, "STEP4", "END", 
                "Reg Policy reviews rejected or escalated challenges and closes workflow on rejection");
            workflowMetadataRepository.save(step3);

            WorkflowMetadata step4 = new WorkflowMetadata(101L, "STEP4", "FO Owner Final Confirmation", "FO_OWNER", 
                "CLOSE", null, "END", "END", 
                "FO Owner confirms resolution and closes the challenge workflow");
            workflowMetadataRepository.save(step4);

            // Insert exception access data
            ExceptionAccess access1 = new ExceptionAccess();
            access1.setExceptionAccessId(101L);
            access1.setRole("FO_OWNER");
            access1.setBrid("BR12345");
            access1.setProductArea("EQ");
            access1.setBusinessArea("APAC");
            exceptionAccessRepository.save(access1);

            ExceptionAccess access2 = new ExceptionAccess();
            access2.setExceptionAccessId(102L);
            access2.setRole("FO_BUSINESS");
            access2.setBrid("BR56789");
            access2.setProductArea("EQ");
            access2.setBusinessArea("APAC");
            exceptionAccessRepository.save(access2);

            ExceptionAccess access3 = new ExceptionAccess();
            access3.setExceptionAccessId(103L);
            access3.setRole("REG_POLICY");
            access3.setBrid("BR98765");
            access3.setProductArea("EQ");
            access3.setBusinessArea("APAC");
            exceptionAccessRepository.save(access3);

            return ResponseEntity.ok("Sample data initialized successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error initializing data: " + e.getMessage());
        }
    }

    private String getAssigneeForRole(String role) {
        List<ExceptionAccess> accessList = exceptionAccessRepository.findByRole(role);
        if (!accessList.isEmpty()) {
            return accessList.get(0).getBrid();
        }
        return null;
    }

    private boolean isActionAllowed(String allowedActions, String action) {
        if (allowedActions == null || action == null) {
            return false;
        }
        
        List<String> allowedList = Arrays.stream(allowedActions.split(","))
            .map(String::trim)
            .collect(Collectors.toList());
        
        return allowedList.contains(action);
    }

    private String determineNextStep(WorkflowMetadata metadata, String action) {
        switch (action.toUpperCase()) {
            case "SUBMIT":
                return metadata.getNextStepOnSubmit();
            case "APPROVE":
                return metadata.getNextStepOnApprove();
            case "REJECT":
                return metadata.getNextStepOnReject();
            case "CLOSE":
                return "END";
            default:
                return null;
        }
    }

    private void sendNotificationEmail(WorkflowInstance instance, WorkflowMetadata metadata) {
        // TODO: Implement email notification logic
        // This would typically involve:
        // 1. Getting the email template
        // 2. Getting user information for the assignee
        // 3. Sending the email
        System.out.println("Notification email sent for workflow instance: " + instance.getInstanceId());
    }

    /**
     * Parse actions string into array
     */
    private String[] parseActions(String actionsString) {
        if (actionsString == null || actionsString.trim().isEmpty()) {
            return new String[0];
        }
        return actionsString.split(",\\s*");
    }

    /**
     * Parse next steps string into array
     */
    private String[] parseNextSteps(String nextStepsString) {
        if (nextStepsString == null || nextStepsString.trim().isEmpty()) {
            return new String[0];
        }
        // Handle comma-separated values and trim whitespace
        return nextStepsString.split(",\\s*");
    }

    // Request/Response DTOs
    public static class WorkflowStartRequest {
        private String exceptionId;
        private Long workflowId;
        private String stepCode;
        private String comments;

        // Getters and setters
        public String getExceptionId() { return exceptionId; }
        public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
        public Long getWorkflowId() { return workflowId; }
        public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
        public String getStepCode() { return stepCode; }
        public void setStepCode(String stepCode) { this.stepCode = stepCode; }
        public String getComments() { return comments; }
        public void setComments(String comments) { this.comments = comments; }
    }

    public static class WorkflowTransitionRequest {
        private String exceptionId;
        private String action;
        private String comments;

        // Getters and setters
        public String getExceptionId() { return exceptionId; }
        public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public String getComments() { return comments; }
        public void setComments(String comments) { this.comments = comments; }
    }

    public static class WorkflowResponse {
        private String message;
        private Long instanceId;
        private String exceptionId;
        private String currentStep;
        private String status;

        public WorkflowResponse(String message, Long instanceId, String exceptionId, String currentStep, String status) {
            this.message = message;
            this.instanceId = instanceId;
            this.exceptionId = exceptionId;
            this.currentStep = currentStep;
            this.status = status;
        }

        // Getters and setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Long getInstanceId() { return instanceId; }
        public void setInstanceId(Long instanceId) { this.instanceId = instanceId; }
        public String getExceptionId() { return exceptionId; }
        public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
        public String getCurrentStep() { return currentStep; }
        public void setCurrentStep(String currentStep) { this.currentStep = currentStep; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class WorkflowExistsResponse {
        private boolean exists;
        private int instanceCount;
        private String lastStatus;

        public WorkflowExistsResponse(boolean exists, int instanceCount, String lastStatus) {
            this.exists = exists;
            this.instanceCount = instanceCount;
            this.lastStatus = lastStatus;
        }

        public boolean isExists() { return exists; }
        public void setExists(boolean exists) { this.exists = exists; }
        public int getInstanceCount() { return instanceCount; }
        public void setInstanceCount(int instanceCount) { this.instanceCount = instanceCount; }
        public String getLastStatus() { return lastStatus; }
        public void setLastStatus(String lastStatus) { this.lastStatus = lastStatus; }
    }

    public static class AvailableActionsResponse {
        private boolean hasActiveWorkflow;
        private String currentStatus;
        private String[] availableActions;
        private String currentStep;
        private String currentRole;

        public AvailableActionsResponse(boolean hasActiveWorkflow, String currentStatus, 
                                      String[] availableActions, String currentStep, String currentRole) {
            this.hasActiveWorkflow = hasActiveWorkflow;
            this.currentStatus = currentStatus;
            this.availableActions = availableActions;
            this.currentStep = currentStep;
            this.currentRole = currentRole;
        }

        public boolean isHasActiveWorkflow() { return hasActiveWorkflow; }
        public void setHasActiveWorkflow(boolean hasActiveWorkflow) { this.hasActiveWorkflow = hasActiveWorkflow; }
        public String getCurrentStatus() { return currentStatus; }
        public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
        public String[] getAvailableActions() { return availableActions; }
        public void setAvailableActions(String[] availableActions) { this.availableActions = availableActions; }
        public String getCurrentStep() { return currentStep; }
        public void setCurrentStep(String currentStep) { this.currentStep = currentStep; }
        public String getCurrentRole() { return currentRole; }
        public void setCurrentRole(String currentRole) { this.currentRole = currentRole; }
    }

    public static class EnhancedWorkflowMetadata {
        private Long workflowId;
        private String stepCode;
        private String stepName;
        private String role;
        private String[] actionsAllowed;
        private String[] nextStepOnSubmit;
        private String[] nextStepOnApprove;
        private String[] nextStepOnReject;
        private String description;

        public EnhancedWorkflowMetadata(Long workflowId, String stepCode, String stepName, String role,
                                      String[] actionsAllowed, String[] nextStepOnSubmit, 
                                      String[] nextStepOnApprove, String[] nextStepOnReject, String description) {
            this.workflowId = workflowId;
            this.stepCode = stepCode;
            this.stepName = stepName;
            this.role = role;
            this.actionsAllowed = actionsAllowed;
            this.nextStepOnSubmit = nextStepOnSubmit;
            this.nextStepOnApprove = nextStepOnApprove;
            this.nextStepOnReject = nextStepOnReject;
            this.description = description;
        }

        public Long getWorkflowId() { return workflowId; }
        public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
        public String getStepCode() { return stepCode; }
        public void setStepCode(String stepCode) { this.stepCode = stepCode; }
        public String getStepName() { return stepName; }
        public void setStepName(String stepName) { this.stepName = stepName; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String[] getActionsAllowed() { return actionsAllowed; }
        public void setActionsAllowed(String[] actionsAllowed) { this.actionsAllowed = actionsAllowed; }
        public String[] getNextStepOnSubmit() { return nextStepOnSubmit; }
        public void setNextStepOnSubmit(String[] nextStepOnSubmit) { this.nextStepOnSubmit = nextStepOnSubmit; }
        public String[] getNextStepOnApprove() { return nextStepOnApprove; }
        public void setNextStepOnApprove(String[] nextStepOnApprove) { this.nextStepOnApprove = nextStepOnApprove; }
        public String[] getNextStepOnReject() { return nextStepOnReject; }
        public void setNextStepOnReject(String[] nextStepOnReject) { this.nextStepOnReject = nextStepOnReject; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
