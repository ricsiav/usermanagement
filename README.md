# Usermanagement
UserManagement is a web application developed with Spring Boot that facilitates efficient and secure user management. The project provides features for user registration, login, and administration, including role and permission management based in JWT security.

## Key Features

- **User Registration**: New users can register for the application.
- **Login Functionality**: Users can log in using their credentials.
- **Role Management**: Supports multiple user roles (ADMIN, MANAGER, USER) with varying access levels.
- **Secure Authentication**: Utilizes **JWT (JSON Web Token)** for secure authentication and authorization.
- **Profile Management**: Users can view and edit their profiles.

## Technologies Used
- **Java 17**: For backend development.
- **Spring Boot 3**: For backend development.
- **Spring Security**: For authentication and authorization.
- **JWT**: For secure authentication handling.
- **PostgreSQL**: For database management.

## User Management API Endpoints

### User Controller

| Method | Endpoint                                    | Description                                      |
|--------|---------------------------------------------|--------------------------------------------------|
| POST   | `/api/v1/management/users/updateUser`     | Updates an existing user with the provided data. |
| POST   | `/api/v1/management/users/newUser`        | Creates a new user with the provided details.   |
| PATCH  | `/api/v1/management/users/updateEmail/{id}` | Updates the email of the user identified by `{id}`. |
| GET    | `/api/v1/management/users`                 | Retrieves a list of all users.                   |
| GET    | `/api/v1/management/users/{id}`            | Retrieves user details by user ID.               |
| DELETE | `/api/v1/management/users/deleteUser/{id}`| Deletes the user identified by `{id}`.           |

### Auth Controller

| Method | Endpoint                     | Description                             |
|--------|------------------------------|-----------------------------------------|
| POST   | `/api/v1/auth/register`      | Registers a new user.                  |
| POST   | `/api/v1/auth/login`         | Authenticates a user and returns a token.|

### DTO Schemas

1. **UserRequestDTO**
   - Represents the request body for updating a user.
   - **Fields**:
     - `id`: Long (User ID)
     - `email`: String (Email address)
     - `username`: String (Username)
     - `role`: String (User role, e.g., ADMIN, USER)

2. **RegisterRequestDTO**
   - Represents the request body for user registration.
   - **Fields**:
     - `name`: String (First name)
     - `surname`: String (Last name)
     - `email`: String (Email address)
     - `username`: String (Username)
     - `password`: String (Password)
     - `role`: String (User role, e.g., ADMIN, USER)

3. **LoginRequestDTO**
   - Represents the request body for user login.
   - **Fields**:
     - `username`: String (Username)
     - `password`: String (Password)

4. **UserEmailRequestDTO**
   - Represents the request body for updating a user's email.
   - **Fields**:
     - `email`: String (New email address)

### Usage Example

#### Create New User

**Endpoint:** `POST /api/v1/management/users/newUser`

**Request Body:**

```json
{
  "username": "newuser",
  "password": "securePassword",
  "email": "newuser@example.com",
  "role": "USER"
}
