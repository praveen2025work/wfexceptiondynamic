package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "WORKFLOW_METADATA")
@IdClass(WorkflowMetadataId.class)
public class WorkflowMetadata {
    
    @Id
    @Column(name = "WORKFLOW_ID")
    private Long workflowId;
    
    @Id
    @Column(name = "STEP_CODE")
    private String stepCode;
    
    @Column(name = "STEP_NAME")
    private String stepName;
    
    @Column(name = "ROLE")
    private String role;
    
    @Column(name = "ACTIONS_ALLOWED")
    private String actionsAllowed;
    
    @Column(name = "NEXT_STEP_ON_SUBMIT")
    private String nextStepOnSubmit;
    
    @Column(name = "NEXT_STEP_ON_APPROVE")
    private String nextStepOnApprove;
    
    @Column(name = "NEXT_STEP_ON_REJECT")
    private String nextStepOnReject;
    
    @Column(name = "DESCRIPTION")
    private String description;

    // Constructors
    public WorkflowMetadata() {}

    public WorkflowMetadata(Long workflowId, String stepCode, String stepName, String role, 
                           String actionsAllowed, String nextStepOnSubmit, String nextStepOnApprove, 
                           String nextStepOnReject, String description) {
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

    // Getters and setters
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public String getStepCode() { return stepCode; }
    public void setStepCode(String stepCode) { this.stepCode = stepCode; }
    
    public String getStepName() { return stepName; }
    public void setStepName(String stepName) { this.stepName = stepName; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getActionsAllowed() { return actionsAllowed; }
    public void setActionsAllowed(String actionsAllowed) { this.actionsAllowed = actionsAllowed; }
    
    public String getNextStepOnSubmit() { return nextStepOnSubmit; }
    public void setNextStepOnSubmit(String nextStepOnSubmit) { this.nextStepOnSubmit = nextStepOnSubmit; }
    
    public String getNextStepOnApprove() { return nextStepOnApprove; }
    public void setNextStepOnApprove(String nextStepOnApprove) { this.nextStepOnApprove = nextStepOnApprove; }
    
    public String getNextStepOnReject() { return nextStepOnReject; }
    public void setNextStepOnReject(String nextStepOnReject) { this.nextStepOnReject = nextStepOnReject; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
