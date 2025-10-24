package com.example.exceptionworkflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exception Workflow Management System")
                        .version("1.0.0")
                        .description("A comprehensive workflow management system for handling exceptions with role-based access control, SLA management, and audit trails.")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev@company.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server"),
                        new Server().url("https://dev.company.com").description("Development Environment"),
                        new Server().url("https://uat.company.com").description("UAT Environment"),
                        new Server().url("https://prod.company.com").description("Production Environment")
                ));
    }
}
