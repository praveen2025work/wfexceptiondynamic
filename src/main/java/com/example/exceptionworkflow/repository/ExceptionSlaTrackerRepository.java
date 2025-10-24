package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.ExceptionSlaTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExceptionSlaTrackerRepository extends JpaRepository<ExceptionSlaTracker, Long> {
    List<ExceptionSlaTracker> findByExceptionId(String exceptionId);
    List<ExceptionSlaTracker> findByStatus(String status);
    List<ExceptionSlaTracker> findByWorkflowInstanceId(Long workflowInstanceId);
}
