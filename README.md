URLs:

get all employees```http://localhost:8080/api/v1/employees```
get one employee ```http://localhost:8080/api/v1/employees/{id}```
get all users ```http://localhost:8080/api/v1/users```
get one user ```http://localhost:8080/api/v1/users/{id}```

Headers:
```Content-Type: application/json```

Body → raw/pretty → JSON:
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