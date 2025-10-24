# Exception Workflow Management System

A comprehensive Spring Boot application for managing exception workflows with Hibernate JPA, H2 database for local development, and Oracle database support for different environments.

## Features

- **Workflow Management**: Complete workflow engine with configurable steps and transitions
- **Role-based Access Control**: User roles and permissions management
- **SLA Management**: Configurable SLA hours with email notifications
- **Audit Trail**: Hibernate Envers for complete audit logging
- **Multi-environment Support**: Local (H2), Dev, UAT, and Prod (Oracle) configurations
- **REST API**: Complete REST endpoints for workflow operations

## Database Schema

### Core Tables

1. **EXCEPTION_WORKFLOW**: Static exception data
2. **WORKFLOW_METADATA**: Workflow step definitions and transitions
3. **WORKFLOW_INSTANCE**: Active workflow instances
4. **WORKFLOW_SLA_CONFIG**: SLA configuration per workflow step
5. **EXCEPTION_ACCESS**: Role-based access control
6. **USER_INFO**: User details and contact information

### Challenge Workflow Example

The system includes a pre-configured Challenge Workflow (ID: 201) with the following steps:

1. **FO_CHALLENGE** → FO initiates challenge
2. **FO_BUSINESS_REVIEW** → FO Business reviews (8hr SLA)
3. **REG_POLICY_REVIEW** → Regulatory review (12hr SLA)
4. **FO_OWNER_REVIEW** → Final decision (8hr SLA)

## API Endpoints

### Workflow Management

- `POST /api/workflow/start` - Start a new workflow
- `POST /api/workflow/action` - Perform workflow action
- `GET /api/workflow/status/{exceptionId}` - Get workflow status

### Exception Management

- `GET /api/exceptions` - Get all exceptions
- `GET /api/exceptions/{exceptionId}` - Get exception by ID
- `POST /api/exceptions` - Create new exception

## Sample API Usage

### Start Workflow
```json
POST /api/workflow/start
{
  "exceptionId": "EXC-CH-002",
  "workflowId": 201,
  "startedBy": "BR1001",
  "comments": "Starting challenge workflow"
}
```

### Perform Action
```json
POST /api/workflow/action
{
  "exceptionId": "EXC-CH-002",
  "action": "Approve",
  "performedBy": "BR1002",
  "comments": "Challenge accepted"
}
```

### Get Workflow Status
```json
GET /api/workflow/status/EXC-CH-002
```

## Environment Configuration

### Local Development (H2)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Development Environment (Oracle)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### UAT Environment (Oracle)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=uat
```

### Production Environment (Oracle)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Database Access

### H2 Console (Local Development)
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password

## Sample Data

The application includes sample data for:
- 3 exception records
- Complete Challenge Workflow configuration
- 8 user records with different roles
- 1 active workflow instance

## Build and Run

```bash
# Build the application
mvn clean compile

# Run with local profile (H2 database)
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Testing the Application

1. Start the application with local profile
2. Access H2 console at http://localhost:8080/h2-console
3. Use the REST endpoints to test workflow operations
4. Check audit tables (suffix _AUD) for audit trail

## Audit Trail

All entity changes are automatically audited using Hibernate Envers. Audit tables are created with `_AUD` suffix:
- EXCEPTION_WORKFLOW_AUD
- WORKFLOW_METADATA_AUD
- WORKFLOW_INSTANCE_AUD
- etc.

## Email Configuration

Configure email settings in application properties:
```yaml
spring:
  mail:
    host: your-smtp-host
    port: 587
    username: your-email
    password: your-password
```

## Dependencies

- Spring Boot 3.2.0
- Spring Data JPA
- Hibernate Envers
- H2 Database (local)
- Oracle JDBC Driver
- Lombok
- Spring Mail
