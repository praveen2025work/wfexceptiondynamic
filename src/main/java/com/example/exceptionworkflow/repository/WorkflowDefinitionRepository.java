package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition, Long> {
    List<WorkflowDefinition> findByActiveFlag(String activeFlag);
    List<WorkflowDefinition> findByClassification(String classification);
}
