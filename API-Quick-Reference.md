# 🚀 Exception Workflow API - Quick Reference

## 🎯 Core Endpoints

| Method | Endpoint | Purpose | Response |
|--------|----------|---------|----------|
| `POST` | `/api/workflow/start` | Start new workflow | `{instanceId, status, currentStep}` |
| `POST` | `/api/workflow/transition` | Move to next step | `{instanceId, status, currentStep}` |
| `GET` | `/api/workflow/status/{exceptionId}` | Get workflow status | `{instanceId, status, currentStep}` |
| `GET` | `/api/workflow/active/{exceptionId}` | Get active workflow | `{instanceId, status, currentStep}` |

## 🎨 UI Integration Endpoints

| Method | Endpoint | Purpose | Response |
|--------|----------|---------|----------|
| `GET` | `/api/workflow/exists/{exceptionId}` | Check if workflow exists | `{exists, instanceCount, lastStatus}` |
| `GET` | `/api/workflow/actions/{exceptionId}?userRole=ROLE` | Get user actions | `{hasActiveWorkflow, availableActions[], currentStep, currentRole}` |
| `GET` | `/api/workflow/metadata-enhanced/{workflowId}` | Get metadata with arrays | `[{actionsAllowed[], nextStepOnSubmit[], ...}]` |
| `GET` | `/api/workflow/metadata/{workflowId}` | Get raw metadata | `[{actionsAllowed: string, nextStepOnSubmit: string, ...}]` |

## 🔧 Utility Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| `POST` | `/api/workflow/init-data` | Initialize sample data |
| `GET` | `/api/workflow/active` | List all active workflows |

## 📋 Request/Response Examples

### Start Workflow
```bash
curl -X POST http://localhost:8080/api/workflow/start \
  -H "Content-Type: application/json" \
  -d '{
    "exceptionId": "EXC_20251022001",
    "workflowId": 101,
    "stepCode": "STEP1",
    "comments": "Initial challenge"
  }'
```

### Transition Workflow
```bash
curl -X POST http://localhost:8080/api/workflow/transition \
  -H "Content-Type: application/json" \
  -d '{
    "exceptionId": "EXC_20251022001",
    "action": "SUBMIT",
    "comments": "Submitted for review"
  }'
```

### Check User Actions
```bash
curl "http://localhost:8080/api/workflow/actions/EXC_20251022001?userRole=FO_OWNER"
```

### Get Enhanced Metadata
```bash
curl http://localhost:8080/api/workflow/metadata-enhanced/101
```

## 🎨 UI Integration Flow

1. **Check if workflow exists**: `GET /api/workflow/exists/{exceptionId}`
2. **Get user actions**: `GET /api/workflow/actions/{exceptionId}?userRole={role}`
3. **Get metadata for dropdowns**: `GET /api/workflow/metadata-enhanced/{workflowId}`
4. **Start workflow**: `POST /api/workflow/start` (if no workflow exists)
5. **Transition workflow**: `POST /api/workflow/transition` (if user has actions)

## 🔑 Key Differences

- **`/metadata/`** → Raw database values (strings, nulls)
- **`/metadata-enhanced/`** → UI-ready arrays (parsed from comma-separated strings)
- **`/actions/`** → User-specific actions based on role and workflow state
- **`/exists/`** → Quick check if workflow exists for an exception

## 🚀 Quick Start

1. **Initialize data**: `curl -X POST http://localhost:8080/api/workflow/init-data`
2. **Access Swagger**: `http://localhost:8080/swagger-ui.html`
3. **Test workflow**: Use the examples above

## 📝 Notes

- Use **enhanced metadata** endpoint for UI dropdowns
- **Role-based access**: Users only see actions they can perform
- **Dynamic transitions**: Next steps determined by metadata
- **Array parsing**: Comma-separated strings automatically parsed into arrays
