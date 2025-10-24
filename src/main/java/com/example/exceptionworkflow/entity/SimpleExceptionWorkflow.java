package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXCEPTION_WORKFLOW")
@Audited
public class SimpleExceptionWorkflow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "EXCEPTION_ID", length = 250)
    private String exceptionId;
    
    @Column(name = "STATUS", length = 50)
    private String status;
    
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getExceptionId() { return exceptionId; }
    public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
