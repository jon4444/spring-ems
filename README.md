URL:

```http://localhost:8080/api/v1/employees```

Headers:

```Content-Type: application/json```

Body → raw → JSON:
```
{
    "firstname": "John",
    "lastname": "Smith",
    "email": "john@example.com"
}
```

┌─────────────────────────────┐
│         CONTROLLER          │
│                             │
│ HTTP requests/responses     │
└──────────────┬──────────────┘
│
▼
┌─────────────────────────────┐
│            DTO              │
│                             │
│ Controls API data           │
└──────────────┬──────────────┘
│
▼
┌─────────────────────────────┐
│           SERVICE           │
│                             │
│ Business/application logic  │
└──────────────┬──────────────┘
│
▼
┌─────────────────────────────┐
│           ENTITY            │
│                             │
│ Database representation     │
└──────────────┬──────────────┘
│
▼
┌─────────────────────────────┐
│        REPOSITORY            │
└──────────────┬──────────────┘
│
▼
PostgreSQL