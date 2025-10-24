package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.SimpleExceptionWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleExceptionWorkflowRepository extends JpaRepository<SimpleExceptionWorkflow, Long> {
    List<SimpleExceptionWorkflow> findByStatus(String status);
}
