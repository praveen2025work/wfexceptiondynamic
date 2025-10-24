package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "WORKFLOW_DEFINITION")
@Audited
public class WorkflowDefinition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKFLOW_ID")
    private Long workflowId;
    
    @Column(name = "WORKFLOW_NAME", length = 255)
    private String workflowName;
    
    @Column(name = "CLASSIFICATION", length = 50)
    private String classification;
    
    @Column(name = "ACTIVE_FLAG", length = 1)
    private String activeFlag;
    
    // Getters and Setters
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public String getWorkflowName() { return workflowName; }
    public void setWorkflowName(String workflowName) { this.workflowName = workflowName; }
    
    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }
    
    public String getActiveFlag() { return activeFlag; }
    public void setActiveFlag(String activeFlag) { this.activeFlag = activeFlag; }
}
