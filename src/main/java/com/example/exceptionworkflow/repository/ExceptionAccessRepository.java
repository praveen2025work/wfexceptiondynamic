package com.example.exceptionworkflow.repository;

import com.example.exceptionworkflow.entity.ExceptionAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExceptionAccessRepository extends JpaRepository<ExceptionAccess, Long> {
    
    List<ExceptionAccess> findByRole(String role);
    
    List<ExceptionAccess> findByBrid(String brid);
    
    List<ExceptionAccess> findByProductArea(String productArea);
    
    List<ExceptionAccess> findByBusinessArea(String businessArea);
}
