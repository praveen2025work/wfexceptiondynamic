package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.WorkflowSlaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowSlaConfigRepository extends JpaRepository<WorkflowSlaConfig, Long> {
    
    List<WorkflowSlaConfig> findByWorkflowId(Long workflowId);
    
    Optional<WorkflowSlaConfig> findByWorkflowIdAndStepCode(Long workflowId, String stepCode);
}
