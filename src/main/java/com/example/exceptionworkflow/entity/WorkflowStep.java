package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "WORKFLOW_STEP")
@Audited
public class WorkflowStep {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STEP_ID")
    private Long stepId;
    
    @Column(name = "WORKFLOW_ID")
    private Long workflowId;
    
    @Column(name = "STEP_ORDER")
    private Integer stepOrder;
    
    @Column(name = "STEP_NAME", length = 255)
    private String stepName;
    
    @Column(name = "ROLE_CODE", length = 50)
    private String roleCode;
    
    @Column(name = "ACTIONS_ALLOWED", length = 255)
    private String actionsAllowed;
    
    @Column(name = "NEXT_STEP_IF_APPROVED")
    private Long nextStepIfApproved;
    
    @Column(name = "NEXT_STEP_IF_REJECTED")
    private Long nextStepIfRejected;
    
    @Column(name = "SLA_ID")
    private Long slaId;
    
    @Column(name = "IS_FINAL_STEP", length = 1)
    private String isFinalStep;
    
    // Getters and Setters
    public Long getStepId() { return stepId; }
    public void setStepId(Long stepId) { this.stepId = stepId; }
    
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public Integer getStepOrder() { return stepOrder; }
    public void setStepOrder(Integer stepOrder) { this.stepOrder = stepOrder; }
    
    public String getStepName() { return stepName; }
    public void setStepName(String stepName) { this.stepName = stepName; }
    
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    
    public String getActionsAllowed() { return actionsAllowed; }
    public void setActionsAllowed(String actionsAllowed) { this.actionsAllowed = actionsAllowed; }
    
    public Long getNextStepIfApproved() { return nextStepIfApproved; }
    public void setNextStepIfApproved(Long nextStepIfApproved) { this.nextStepIfApproved = nextStepIfApproved; }
    
    public Long getNextStepIfRejected() { return nextStepIfRejected; }
    public void setNextStepIfRejected(Long nextStepIfRejected) { this.nextStepIfRejected = nextStepIfRejected; }
    
    public Long getSlaId() { return slaId; }
    public void setSlaId(Long slaId) { this.slaId = slaId; }
    
    public String getIsFinalStep() { return isFinalStep; }
    public void setIsFinalStep(String isFinalStep) { this.isFinalStep = isFinalStep; }
}
