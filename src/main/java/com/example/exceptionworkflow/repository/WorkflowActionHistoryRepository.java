package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.WorkflowActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowActionHistoryRepository extends JpaRepository<WorkflowActionHistory, Long> {
    List<WorkflowActionHistory> findByInstanceId(Long instanceId);
    List<WorkflowActionHistory> findByActionBy(String actionBy);
}
