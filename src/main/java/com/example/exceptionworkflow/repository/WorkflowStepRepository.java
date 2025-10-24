package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, Long> {
    List<WorkflowStep> findByWorkflowId(Long workflowId);
    List<WorkflowStep> findByWorkflowIdOrderByStepOrder(Long workflowId);
    Optional<WorkflowStep> findByWorkflowIdAndStepOrder(Long workflowId, Integer stepOrder);
    List<WorkflowStep> findByRoleCode(String roleCode);
    List<WorkflowStep> findByIsFinalStep(String isFinalStep);
}
