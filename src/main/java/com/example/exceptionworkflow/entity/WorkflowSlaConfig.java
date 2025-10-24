package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "WORKFLOW_SLA_CONFIG")
@IdClass(WorkflowSlaConfigId.class)
public class WorkflowSlaConfig {
    
    @Id
    @Column(name = "WORKFLOW_ID")
    private Long workflowId;
    
    @Id
    @Column(name = "STEP_CODE")
    private String stepCode;
    
    @Column(name = "SLA_HOURS")
    private Integer slaHours;
    
    @Column(name = "EMAIL_TEMPLATE_ID")
    private Long emailTemplateId;
    
    @Column(name = "TRIGGER_ON")
    private String triggerOn;

    // Constructors
    public WorkflowSlaConfig() {}

    public WorkflowSlaConfig(Long workflowId, String stepCode, Integer slaHours, 
                            Long emailTemplateId, String triggerOn) {
        this.workflowId = workflowId;
        this.stepCode = stepCode;
        this.slaHours = slaHours;
        this.emailTemplateId = emailTemplateId;
        this.triggerOn = triggerOn;
    }

    // Getters and setters
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public String getStepCode() { return stepCode; }
    public void setStepCode(String stepCode) { this.stepCode = stepCode; }
    
    public Integer getSlaHours() { return slaHours; }
    public void setSlaHours(Integer slaHours) { this.slaHours = slaHours; }
    
    public Long getEmailTemplateId() { return emailTemplateId; }
    public void setEmailTemplateId(Long emailTemplateId) { this.emailTemplateId = emailTemplateId; }
    
    public String getTriggerOn() { return triggerOn; }
    public void setTriggerOn(String triggerOn) { this.triggerOn = triggerOn; }
}
