# java crud
This is a simple REST API for managing customer data using Spring Boot with JWT authentication. The API supports basic CRUD operations on customer data and includes unit tests to ensure functionality.


## Features
- Create, read, update, and delete customers
- JWT-based authentication and authorization
- Unit tests using JUnit and Mockito


## Application Endpoints
- GET `/api/customers`: Retrieve all customers
- GET `/api/customers/{id}`: Retrieve a specific customer by ID
- POST `/api/customers`: Create a new customer
- PUT `/api/customers/{id}`: Update an existing customer by ID
- DELETE `/api/customers/{id}`: Delete a customer by ID


## Authentication Endpoints
- POST `/authenticate`: Authenticate a user and get a JWT token


## JWT Authentication
### Generate JWT Token
To access the protected endpoints, you need to generate a JWT token. Use the /authenticate endpoint with valid user credentials to obtain the token.
In this program, set the username to `user` and the password to `password`.

### Example Request for Token
```
curl --location 'localhost:8080/authenticate' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user",
    "password": "password"
}'
```

### Example Response with Token
```
{
    "token": "jwt-token"
}
```

### Using the Token
Include the token in the `Authorization` header of your requests to access protected endpoints.
```
Authorization: jwt-token
```


## Example Customer Requests
### GET /api/customers
```
curl --location 'localhost:8080/api/customers' \
--header 'Authorization: jwt-token'
```

### GET /api/customers/{id}
```
curl --location 'localhost:8080/api/customers/1' \
--header 'Authorization: jwt-token'
```

### POST /api/customers
```
curl --location 'localhost:8080/api/customers' \
--header 'Authorization: jwt-token' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Chaiwet",
    "lastName": "Muito",
    "email": "chaiwet.muito@gmail.com"
}'
```

### PUT /api/customers/{id}
```
curl --location --request PUT 'localhost:8080/api/customers/1' \
--header 'Authorization: jwt-token' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Muito",
    "lastName": "Chaiwet",
    "email": "chaiwet.muito@gmail.com"
}'
```

### DELETE /api/customers/{id}
```
curl --location --request DELETE 'localhost:8080/api/customers/1' \
--header 'Authorization: jwt-token'
```







