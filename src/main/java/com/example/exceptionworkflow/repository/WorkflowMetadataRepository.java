package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.WorkflowMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowMetadataRepository extends JpaRepository<WorkflowMetadata, Long> {
    
    List<WorkflowMetadata> findByWorkflowId(Long workflowId);
    
    Optional<WorkflowMetadata> findByWorkflowIdAndStepCode(Long workflowId, String stepCode);
}
