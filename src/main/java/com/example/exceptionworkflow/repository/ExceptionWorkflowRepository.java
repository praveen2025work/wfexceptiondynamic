package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.ExceptionWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExceptionWorkflowRepository extends JpaRepository<ExceptionWorkflow, Long> {
    Optional<ExceptionWorkflow> findByExceptionId(String exceptionId);
    List<ExceptionWorkflow> findByStatus(String status);
    List<ExceptionWorkflow> findByRegulator(String regulator);
    List<ExceptionWorkflow> findByInstrumentId(Long instrumentId);
    List<ExceptionWorkflow> findByLegalEntity(String legalEntity);
    List<ExceptionWorkflow> findByAgingGreaterThan(Integer minDays);
    List<ExceptionWorkflow> findBySystem(String system);
}
