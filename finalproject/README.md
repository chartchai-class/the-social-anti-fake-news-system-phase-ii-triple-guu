# 331-backend API Testing Guide

This README provides example requests for all main API routes in your Spring Boot backend. Use tools like Postman, Apidog, or curl to test these endpoints.

---

## Authentication & Registration

### Register a New User
- **POST** `/api/v1/auth/register`
- **Body (JSON):**
```json
{
  "username": "testuser",
  "password": "testpass",
  "firstname": "Test",
  "lastname": "User",
  "email": "test@example.com"
}
```

### Authenticate (Login)
- **POST** `/api/v1/auth/authenticate`
- **Body (JSON):**
```json
{
  "username": "testuser",
  "password": "testpass"
}
```
- **Response:** JWT token

### Refresh Token
- **POST** `/api/v1/auth/refresh-token`
- **Headers:**
  - `Authorization: Bearer <your_refresh_token>`

---

## News


### Get All News
- **GET** `/news`
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

### Create News
- **POST** `/news`
- **Body (JSON):**
```json
{
  "title": "Sample News",
  "content": "This is a news item.",
  "authorId": 1
}
```
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

---

## Comments

### Add Comment to News
- **POST** `/news/{id}/comment`
- **Body (JSON):**
```json
{
  "content": "Nice article!"
}
```
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

---

## Votes

### Vote on News
- **POST** `/news/{id}/vote`
- **Body (JSON):**
```json
{
  "type": "UPVOTE"  // or "DOWNVOTE"
}
```
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

---

## Organizers


### Get All Organizers
- **GET** `/organizers`
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

### Create Organizer
- **POST** `/organizers`
- **Body (JSON):**
```json
{
  "name": "Organizer Name"
}
```
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

---

## Events


### Get All Events
- **GET** `/events`
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

### Create Event
- **POST** `/events`
- **Body (JSON):**
```json
{
  "title": "Event Title",
  "description": "Event Description",
  "organizerId": 1
}
```
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

---

## Supabase Storage

### Upload File
- **POST** `/uploadFile`
- **Form Data:**
  - `file`: (select file)
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

### Upload Image
- **POST** `/uploadImage`
- **Form Data:**
  - `image`: (select image)
- **Headers:**
  - `Authorization: Bearer <your_jwt_token>`

---


## Notes
- All endpoints except `/api/v1/auth/register` and `/api/v1/auth/authenticate` require authentication with a valid JWT token in the `Authorization` header.
- Replace `<your_jwt_token>` with the token received from authentication.
- Some endpoints may require additional fields or parameters depending on your implementation.
- If you get a 403 error, make sure you are authenticated and have the correct roles/permissions.

---

For more details, see your controller and DTO classes.
