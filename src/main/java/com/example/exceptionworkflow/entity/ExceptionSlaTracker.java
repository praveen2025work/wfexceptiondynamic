package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXCEPTION_SLA_TRACKER")
@Audited
public class ExceptionSlaTracker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACKER_ID")
    private Long trackerId;
    
    @Column(name = "EXCEPTION_ID", length = 250)
    private String exceptionId;
    
    @Column(name = "WORKFLOW_INSTANCE_ID")
    private Long workflowInstanceId;
    
    @Column(name = "SLA_ID")
    private Long slaId;
    
    @Column(name = "STATUS", length = 50)
    private String status;
    
    @Column(name = "EMAIL_SENT_ON")
    private LocalDateTime emailSentOn;
    
    @Column(name = "REMINDER_COUNT")
    private Integer reminderCount;
    
    // Getters and Setters
    public Long getTrackerId() { return trackerId; }
    public void setTrackerId(Long trackerId) { this.trackerId = trackerId; }
    
    public String getExceptionId() { return exceptionId; }
    public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
    
    public Long getWorkflowInstanceId() { return workflowInstanceId; }
    public void setWorkflowInstanceId(Long workflowInstanceId) { this.workflowInstanceId = workflowInstanceId; }
    
    public Long getSlaId() { return slaId; }
    public void setSlaId(Long slaId) { this.slaId = slaId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getEmailSentOn() { return emailSentOn; }
    public void setEmailSentOn(LocalDateTime emailSentOn) { this.emailSentOn = emailSentOn; }
    
    public Integer getReminderCount() { return reminderCount; }
    public void setReminderCount(Integer reminderCount) { this.reminderCount = reminderCount; }
}
