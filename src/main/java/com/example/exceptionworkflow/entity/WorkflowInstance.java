package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "WORKFLOW_INSTANCE")
public class WorkflowInstance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTANCE_ID")
    private Long instanceId;
    
    @Column(name = "EXCEPTION_ID", nullable = false)
    private String exceptionId;
    
    @Column(name = "WORKFLOW_ID", nullable = false)
    private Long workflowId;
    
    @Column(name = "CURRENT_STEP")
    private String currentStep;
    
    @Column(name = "CURRENT_STEP_SEQ")
    private Integer currentStepSeq;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "ACTIVE_STEP_FLAG")
    private String activeStepFlag;
    
    @Column(name = "ASSIGNED_TO_BRID")
    private String assignedToBrid;
    
    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;
    
    @Column(name = "SLA_HOURS")
    private Integer slaHours;
    
    @Column(name = "EMAIL_TEMPLATE_ID")
    private Long emailTemplateId;
    
    @Column(name = "TRIGGER_ON")
    private String triggerOn;
    
    @Column(name = "EMAIL_STATUS")
    private String emailStatus;
    
    @Column(name = "EMAIL_SENT_ON")
    private Timestamp emailSentOn;
    
    @Column(name = "COMMENTS")
    private String comments;

    // Constructors
    public WorkflowInstance() {}

    public WorkflowInstance(String exceptionId, Long workflowId, String currentStep, 
                           Integer currentStepSeq, String status, String activeStepFlag, 
                           String assignedToBrid, Timestamp lastUpdated, String comments) {
        this.exceptionId = exceptionId;
        this.workflowId = workflowId;
        this.currentStep = currentStep;
        this.currentStepSeq = currentStepSeq;
        this.status = status;
        this.activeStepFlag = activeStepFlag;
        this.assignedToBrid = assignedToBrid;
        this.lastUpdated = lastUpdated;
        this.comments = comments;
    }

    // Getters and setters
    public Long getInstanceId() { return instanceId; }
    public void setInstanceId(Long instanceId) { this.instanceId = instanceId; }
    
    public String getExceptionId() { return exceptionId; }
    public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
    
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public String getCurrentStep() { return currentStep; }
    public void setCurrentStep(String currentStep) { this.currentStep = currentStep; }
    
    public Integer getCurrentStepSeq() { return currentStepSeq; }
    public void setCurrentStepSeq(Integer currentStepSeq) { this.currentStepSeq = currentStepSeq; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getActiveStepFlag() { return activeStepFlag; }
    public void setActiveStepFlag(String activeStepFlag) { this.activeStepFlag = activeStepFlag; }
    
    public String getAssignedToBrid() { return assignedToBrid; }
    public void setAssignedToBrid(String assignedToBrid) { this.assignedToBrid = assignedToBrid; }
    
    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }
    
    public Integer getSlaHours() { return slaHours; }
    public void setSlaHours(Integer slaHours) { this.slaHours = slaHours; }
    
    public Long getEmailTemplateId() { return emailTemplateId; }
    public void setEmailTemplateId(Long emailTemplateId) { this.emailTemplateId = emailTemplateId; }
    
    public String getTriggerOn() { return triggerOn; }
    public void setTriggerOn(String triggerOn) { this.triggerOn = triggerOn; }
    
    public String getEmailStatus() { return emailStatus; }
    public void setEmailStatus(String emailStatus) { this.emailStatus = emailStatus; }
    
    public Timestamp getEmailSentOn() { return emailSentOn; }
    public void setEmailSentOn(Timestamp emailSentOn) { this.emailSentOn = emailSentOn; }
    
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
