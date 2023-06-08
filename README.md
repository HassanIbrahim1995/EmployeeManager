# EmployeeManager
Simple request to register to http://localhost:8080/register + json body: 
    {
        "firstName": "John",
        "lastName": "Doe",
        "birthdate": "1985-12-30",
        "bsn": "123456789",
        "iban": "NL20INGB0001234567",
        "street": "Main Street",
        "city": "Amsterdam",
        "state": "Noord-Holland",
        "postalCode": "1011 AA",
        "roles": ["ROLE_USER", "ROLE_ADMIN"],
        "username": "johndoe",
        "password": "strongpassword123",
        "email": "john.doe@example.com",
        "department":"IT"
    }
response: 
  User registration successful!
  
Authentication http://localhost:8080/api/auth/login: 
Json body
  {
    "username":"johndoe",
    "password":"strongpassword123"
  }
response: 
  {
      "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNjg2MjYwODYxLCJleHAiOjE2ODY4NjU2NjF9.1hnZHvU0r31dUhSFINFUyegdS04ksZN5Xwi2KMcV02aOqDsZ4giuwuELjh8nkCmr"
  }
