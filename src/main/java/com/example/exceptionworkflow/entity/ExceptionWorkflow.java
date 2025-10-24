package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXCEPTION_WORKFLOW")
@Audited
public class ExceptionWorkflow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "EXCEPTION_ID", length = 250)
    private String exceptionId;
    
    @Column(name = "EQUITY_CLASS_TYPE", length = 250)
    private String equityClassType;
    
    @Column(name = "REGULATOR", length = 250)
    private String regulator;
    
    @Column(name = "AGING")
    private Integer aging;
    
    @Column(name = "AS_OF_TIME")
    private LocalDateTime asOfTime;
    
    @Column(name = "BB_UNDERLYING", length = 250)
    private String bbUnderlying;
    
    @Column(name = "ESM_SECURITY_TYPE", length = 50)
    private String esmSecurityType;
    
    @Column(name = "INSTRUMENT_ID")
    private Long instrumentId;
    
    @Column(name = "INSTRUMENT_NAME", length = 250)
    private String instrumentName;
    
    @Column(name = "INSTRUMENT_TYPE", length = 50)
    private String instrumentType;
    
    @Column(name = "LEGAL_ENTITY", length = 50)
    private String legalEntity;
    
    @Column(name = "LOOK_THROUGH", length = 50)
    private String lookThrough;
    
    @Column(name = "POSITION_AV", length = 1000)
    private String positionAv;
    
    @Column(name = "POSITION_QTY")
    private Long positionQty;
    
    @Column(name = "POSITION_TBBB_CLASSIFICATION", length = 250)
    private String positionTbbbClassification;
    
    @Column(name = "PROCESSED_ON")
    private LocalDateTime processedOn;
    
    @Column(name = "SDS_BOOK_CODE")
    private Long sdsBookCode;
    
    @Column(name = "SDS_BOOK_PATH", length = 1000)
    private String sdsBookPath;
    
    @Column(name = "SOD_DELTA_BB_UNDERLYING", length = 1000)
    private String sodDeltaBbUnderlying;
    
    @Column(name = "STATUS", length = 50)
    private String status;
    
    @Column(name = "SYSTEM", length = 50)
    private String system;
    
    @Column(name = "REASON", columnDefinition = "CLOB")
    private String reason;
    
    @Column(name = "ORIGINAL_QTY")
    private BigDecimal originalQty;
    
    @Column(name = "CATEGORY_ID")
    private Long categoryId;
    
    @Column(name = "SOURCE", length = 255)
    private String source;
    
    @Column(name = "TETB_AV", length = 1000)
    private String tetbAv;
    
    @Column(name = "TETB_QUANTITY")
    private BigDecimal tetbQuantity;
    
    @Column(name = "LEVEL_1", length = 50)
    private String level1;
    
    @Column(name = "LEVEL_2", length = 50)
    private String level2;
    
    @Column(name = "LEVEL_3", length = 50)
    private String level3;
    
    @Column(name = "LEVEL_4", length = 50)
    private String level4;
    
    @Column(name = "LEVEL_5", length = 50)
    private String level5;
    
    @Column(name = "LEVEL_6", length = 50)
    private String level6;
    
    @Column(name = "LEVEL_7", length = 50)
    private String level7;
    
    @Column(name = "ACTION_OWNER", length = 255)
    private String actionOwner;
    
    @Column(name = "REPORTING_CCY", length = 5)
    private String reportingCcy;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getExceptionId() { return exceptionId; }
    public void setExceptionId(String exceptionId) { this.exceptionId = exceptionId; }
    
    public String getEquityClassType() { return equityClassType; }
    public void setEquityClassType(String equityClassType) { this.equityClassType = equityClassType; }
    
    public String getRegulator() { return regulator; }
    public void setRegulator(String regulator) { this.regulator = regulator; }
    
    public Integer getAging() { return aging; }
    public void setAging(Integer aging) { this.aging = aging; }
    
    public LocalDateTime getAsOfTime() { return asOfTime; }
    public void setAsOfTime(LocalDateTime asOfTime) { this.asOfTime = asOfTime; }
    
    public String getBbUnderlying() { return bbUnderlying; }
    public void setBbUnderlying(String bbUnderlying) { this.bbUnderlying = bbUnderlying; }
    
    public String getEsmSecurityType() { return esmSecurityType; }
    public void setEsmSecurityType(String esmSecurityType) { this.esmSecurityType = esmSecurityType; }
    
    public Long getInstrumentId() { return instrumentId; }
    public void setInstrumentId(Long instrumentId) { this.instrumentId = instrumentId; }
    
    public String getInstrumentName() { return instrumentName; }
    public void setInstrumentName(String instrumentName) { this.instrumentName = instrumentName; }
    
    public String getInstrumentType() { return instrumentType; }
    public void setInstrumentType(String instrumentType) { this.instrumentType = instrumentType; }
    
    public String getLegalEntity() { return legalEntity; }
    public void setLegalEntity(String legalEntity) { this.legalEntity = legalEntity; }
    
    public String getLookThrough() { return lookThrough; }
    public void setLookThrough(String lookThrough) { this.lookThrough = lookThrough; }
    
    public String getPositionAv() { return positionAv; }
    public void setPositionAv(String positionAv) { this.positionAv = positionAv; }
    
    public Long getPositionQty() { return positionQty; }
    public void setPositionQty(Long positionQty) { this.positionQty = positionQty; }
    
    public String getPositionTbbbClassification() { return positionTbbbClassification; }
    public void setPositionTbbbClassification(String positionTbbbClassification) { this.positionTbbbClassification = positionTbbbClassification; }
    
    public LocalDateTime getProcessedOn() { return processedOn; }
    public void setProcessedOn(LocalDateTime processedOn) { this.processedOn = processedOn; }
    
    public Long getSdsBookCode() { return sdsBookCode; }
    public void setSdsBookCode(Long sdsBookCode) { this.sdsBookCode = sdsBookCode; }
    
    public String getSdsBookPath() { return sdsBookPath; }
    public void setSdsBookPath(String sdsBookPath) { this.sdsBookPath = sdsBookPath; }
    
    public String getSodDeltaBbUnderlying() { return sodDeltaBbUnderlying; }
    public void setSodDeltaBbUnderlying(String sodDeltaBbUnderlying) { this.sodDeltaBbUnderlying = sodDeltaBbUnderlying; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getSystem() { return system; }
    public void setSystem(String system) { this.system = system; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public BigDecimal getOriginalQty() { return originalQty; }
    public void setOriginalQty(BigDecimal originalQty) { this.originalQty = originalQty; }
    
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getTetbAv() { return tetbAv; }
    public void setTetbAv(String tetbAv) { this.tetbAv = tetbAv; }
    
    public BigDecimal getTetbQuantity() { return tetbQuantity; }
    public void setTetbQuantity(BigDecimal tetbQuantity) { this.tetbQuantity = tetbQuantity; }
    
    public String getLevel1() { return level1; }
    public void setLevel1(String level1) { this.level1 = level1; }
    
    public String getLevel2() { return level2; }
    public void setLevel2(String level2) { this.level2 = level2; }
    
    public String getLevel3() { return level3; }
    public void setLevel3(String level3) { this.level3 = level3; }
    
    public String getLevel4() { return level4; }
    public void setLevel4(String level4) { this.level4 = level4; }
    
    public String getLevel5() { return level5; }
    public void setLevel5(String level5) { this.level5 = level5; }
    
    public String getLevel6() { return level6; }
    public void setLevel6(String level6) { this.level6 = level6; }
    
    public String getLevel7() { return level7; }
    public void setLevel7(String level7) { this.level7 = level7; }
    
    public String getActionOwner() { return actionOwner; }
    public void setActionOwner(String actionOwner) { this.actionOwner = actionOwner; }
    
    public String getReportingCcy() { return reportingCcy; }
    public void setReportingCcy(String reportingCcy) { this.reportingCcy = reportingCcy; }
}
