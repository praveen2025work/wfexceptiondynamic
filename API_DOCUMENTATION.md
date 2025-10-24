# Exception Workflow API Documentation

## Overview
This is a generic workflow controller that can handle workflow transitions based on metadata stored in the `WORKFLOW_METADATA` table. The controller supports the FO Challenge workflow use case and can be extended for other workflows.

## API Endpoints

### 1. Start Workflow
**POST** `/api/workflow/start`

Starts a new workflow for an exception.

**Request Body:**
```json
{
  "exceptionId": "EXC_20251022001",
  "workflowId": 101,
  "stepCode": "STEP1",
  "comments": "FO Owner submitted challenge to FO Business."
}
```

**Response:**
```json
{
  "message": "Workflow started successfully",
  "instanceId": 1001,
  "exceptionId": "EXC_20251022001",
  "currentStep": "STEP1",
  "status": "IN_PROGRESS"
}
```

### 2. Transition Workflow
**POST** `/api/workflow/transition`

Transitions the workflow to the next step based on the action taken.

**Request Body:**
```json
{
  "exceptionId": "EXC_20251022001",
  "action": "SUBMIT",
  "comments": "Submitted to FO Business for review."
}
```

**Response:**
```json
{
  "message": "Workflow transitioned successfully",
  "instanceId": 1002,
  "exceptionId": "EXC_20251022001",
  "currentStep": "STEP2",
  "status": "IN_PROGRESS"
}
```

### 3. Get Workflow Status
**GET** `/api/workflow/status/{exceptionId}`

Retrieves all workflow instances for an exception.

**Response:**
```json
[
  {
    "instanceId": 1001,
    "exceptionId": "EXC_20251022001",
    "workflowId": 101,
    "currentStep": "STEP1",
    "currentStepSeq": 1,
    "status": "COMPLETED",
    "activeStepFlag": "N",
    "assignedToBrid": "BR12345",
    "lastUpdated": "2025-10-22T09:30:00",
    "comments": "FO Owner submitted challenge to FO Business."
  }
]
```

### 4. Get Active Workflow
**GET** `/api/workflow/active/{exceptionId}`

Retrieves the currently active workflow instance for an exception.

**Response:**
```json
{
  "instanceId": 1002,
  "exceptionId": "EXC_20251022001",
  "workflowId": 101,
  "currentStep": "STEP2",
  "currentStepSeq": 2,
  "status": "IN_PROGRESS",
  "activeStepFlag": "Y",
  "assignedToBrid": "BR56789",
  "lastUpdated": "2025-10-22T10:00:00",
  "comments": "FO Business reviewing the challenge."
}
```

### 5. Get Workflow Metadata
**GET** `/api/workflow/metadata/{workflowId}`

Retrieves workflow metadata for a specific workflow.

**Response:**
```json
[
  {
    "workflowId": 101,
    "stepCode": "STEP1",
    "stepName": "FO Raises Challenge",
    "role": "FO_OWNER",
    "actionsAllowed": "SUBMIT",
    "nextStepOnSubmit": "STEP2,STEP3",
    "nextStepOnApprove": null,
    "nextStepOnReject": null,
    "description": "FO owner raises a challenge for exception and can submit either to FO Business or Reg Policy for review"
  }
]
```

## Workflow Behavior

### FO Challenge Workflow (ID: 101)

| Step | Actor | Action | Next Step | Notes |
|------|-------|--------|-----------|-------|
| STEP1 | FO Owner | Submit → choose "FO Business" | STEP2 | Typical path |
| STEP1 | FO Owner | Submit → choose "Reg Policy" | STEP3 | Direct escalation |
| STEP2 | FO Business | Approve | STEP4 | Returns to FO Owner |
| STEP2 | FO Business | Reject | STEP3 | Sent to Reg Policy |
| STEP3 | Reg Policy | Approve | STEP4 | Back to FO Owner |
| STEP3 | Reg Policy | Reject | END | Workflow closed |
| STEP4 | FO Owner | Close | END | Completed |

## How Backend Derives Next Step

When a user submits or approves/rejects, the backend:

1. **Checks workflow metadata:**
   ```sql
   SELECT NEXT_STEP_ON_SUBMIT, NEXT_STEP_ON_APPROVE, NEXT_STEP_ON_REJECT
   FROM WORKFLOW_METADATA
   WHERE WORKFLOW_ID = 101 AND STEP_CODE = :current_step;
   ```

2. **Determines next step based on action:**
   - If `SUBMIT` → use `NEXT_STEP_ON_SUBMIT`
   - If `APPROVE` → use `NEXT_STEP_ON_APPROVE`
   - If `REJECT` → use `NEXT_STEP_ON_REJECT`

3. **Creates new workflow instance:**
   - Marks old row as `COMPLETED` + `ACTIVE_STEP_FLAG = 'N'`
   - Inserts new row for the next step with `ACTIVE_STEP_FLAG = 'Y'`
   - Pulls the next role's assignee from `EXCEPTION_ACCESS` table
   - Sends notification using SLA and email template mappings

## Database Tables

### WORKFLOW_METADATA
Defines workflow steps and transitions.

### WORKFLOW_INSTANCE
Tracks workflow execution instances.

### EXCEPTION_ACCESS
Maps roles to users (BRID).

### WORKFLOW_SLA_CONFIG
Defines SLA and email template configurations for each step.

### EMAIL_TEMPLATE
Email templates for notifications.

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Success
- `400 Bad Request` - Invalid request or business rule violation
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

Error responses include descriptive messages to help with debugging.
