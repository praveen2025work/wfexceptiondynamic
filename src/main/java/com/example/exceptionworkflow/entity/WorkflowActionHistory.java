package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Table(name = "WORKFLOW_ACTION_HISTORY")
@Audited
public class WorkflowActionHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_ID")
    private Long actionId;
    
    @Column(name = "INSTANCE_ID")
    private Long instanceId;
    
    @Column(name = "STEP_ID")
    private Long stepId;
    
    @Column(name = "ACTION_TAKEN", length = 50)
    private String actionTaken;
    
    @Column(name = "ACTION_BY", length = 50)
    private String actionBy;
    
    @Column(name = "COMMENTS", columnDefinition = "CLOB")
    private String comments;
    
    @Column(name = "ACTION_TIME")
    private LocalDateTime actionTime;
    
    // Getters and Setters
    public Long getActionId() { return actionId; }
    public void setActionId(Long actionId) { this.actionId = actionId; }
    
    public Long getInstanceId() { return instanceId; }
    public void setInstanceId(Long instanceId) { this.instanceId = instanceId; }
    
    public Long getStepId() { return stepId; }
    public void setStepId(Long stepId) { this.stepId = stepId; }
    
    public String getActionTaken() { return actionTaken; }
    public void setActionTaken(String actionTaken) { this.actionTaken = actionTaken; }
    
    public String getActionBy() { return actionBy; }
    public void setActionBy(String actionBy) { this.actionBy = actionBy; }
    
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    
    public LocalDateTime getActionTime() { return actionTime; }
    public void setActionTime(LocalDateTime actionTime) { this.actionTime = actionTime; }
}
