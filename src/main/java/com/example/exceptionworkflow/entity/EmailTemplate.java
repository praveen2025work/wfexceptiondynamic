package com.example.exceptionworkflow.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "EMAIL_TEMPLATE")
@Audited
public class EmailTemplate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPLATE_ID")
    private Long templateId;
    
    @Column(name = "TEMPLATE_NAME", length = 255)
    private String templateName;
    
    @Column(name = "SUBJECT", length = 500)
    private String subject;
    
    @Column(name = "BODY", columnDefinition = "CLOB")
    private String body;
    
    // Getters and Setters
    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }
    
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
