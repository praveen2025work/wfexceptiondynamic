package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {
    
    List<WorkflowInstance> findByExceptionId(String exceptionId);
    
    Optional<WorkflowInstance> findByExceptionIdAndActiveStepFlag(String exceptionId, String activeStepFlag);
    
    List<WorkflowInstance> findByWorkflowId(Long workflowId);
    
    List<WorkflowInstance> findByAssignedToBrid(String assignedToBrid);
}
