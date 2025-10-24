package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "EXCEPTION_ACCESS")
public class ExceptionAccess {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXCEPTION_ACCESS_ID")
    private Long exceptionAccessId;
    
    @Column(name = "ROLE")
    private String role;
    
    @Column(name = "BRID")
    private String brid;
    
    @Column(name = "PRODUCT_AREA")
    private String productArea;
    
    @Column(name = "BUSINESS_AREA")
    private String businessArea;

    // Constructors
    public ExceptionAccess() {}

    public ExceptionAccess(String role, String brid, String productArea, String businessArea) {
        this.role = role;
        this.brid = brid;
        this.productArea = productArea;
        this.businessArea = businessArea;
    }

    // Getters and setters
    public Long getExceptionAccessId() { return exceptionAccessId; }
    public void setExceptionAccessId(Long exceptionAccessId) { this.exceptionAccessId = exceptionAccessId; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getBrid() { return brid; }
    public void setBrid(String brid) { this.brid = brid; }
    
    public String getProductArea() { return productArea; }
    public void setProductArea(String productArea) { this.productArea = productArea; }
    
    public String getBusinessArea() { return businessArea; }
    public void setBusinessArea(String businessArea) { this.businessArea = businessArea; }
}
