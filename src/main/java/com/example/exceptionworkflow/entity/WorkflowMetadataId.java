package com.example.exceptionworkflow.entity;

import java.io.Serializable;
import java.util.Objects;

public class WorkflowMetadataId implements Serializable {
    
    private Long workflowId;
    private String stepCode;
    
    public WorkflowMetadataId() {}
    
    public WorkflowMetadataId(Long workflowId, String stepCode) {
        this.workflowId = workflowId;
        this.stepCode = stepCode;
    }
    
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public String getStepCode() { return stepCode; }
    public void setStepCode(String stepCode) { this.stepCode = stepCode; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowMetadataId that = (WorkflowMetadataId) o;
        return Objects.equals(workflowId, that.workflowId) && Objects.equals(stepCode, that.stepCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(workflowId, stepCode);
    }
}
