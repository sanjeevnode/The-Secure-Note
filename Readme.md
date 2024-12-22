# The Secure Note - Password Manager Application

## Basic Use and Key Features

### Purpose

The Secure Note is a robust password manager application designed to securely store and manage user notes and passwords. It employs advanced encryption techniques to ensure data security and integrity.

### Key Features

1. **Email Verification**:
    - Ensures users verify their email during registration.
2. **Master Password Setup**:
    - Adds an additional layer of security for decrypting sensitive data.
3. **Login Timeout Mechanism**:
    - Temporarily locks accounts after multiple failed login attempts with an exponential backoff time.
4. **End-to-End Encryption**:
    - Notes and sensitive data are encrypted using AES-256-GCM with authenticated encryption.
5. **Token-Based Authentication**:
    - Supports access tokens (short-lived) and refresh tokens (24-hour expiration) for secure session management.

---

## Database Schema

### `User` Table

| Field             | Type    | Description                              |
|-------------------|---------|------------------------------------------|
| `id`              | INT     | Unique user identifier.                  |
| `email`           | VARCHAR | User's email address.                    |
| `username`        | VARCHAR | Unique username .                        |
| `email_verified`  | BOOLEAN | Indicates whether the email is verified. |
| `password`        | VARCHAR | Hashed password of the user.             |
| `failedAttempts`  | INT     | Failed Attempts count .                  |
| `lockTime`        | double  | Lock Time .                              |
| `staartLockTime`  | double  | Start of account lock.                   |
| `master_password` | VARCHAR | Hashed master password.                  |

### `Notes` Table

| Field         | Type    | Description                                       |
| ------------- | ------- | ------------------------------------------------- |
| `id`          | INT     | Unique note identifier.                           |
| `user_id`     | INT     | Foreign key referencing `User` table.             |
| `title`       | VARCHAR | Note title (stored in plaintext).                 |
| `description` | TEXT    | Encrypted description (ciphertext + tag + nonce). |

---

## Flow and Architecture

### Registration Process

1. **Enter Email and Usrname**:

    - User provides email and username.
    - backend generates an otp and send to the user via mail.

2. **Verify Email**:

    - User enters the otp manually.
    - Backend validates the otp and marks `email_verified` as `true`.

3. **Set Password**:
    - User sets a password; backend hashes and stores it.

### Login Process

1. **Login Request**:

    - User provides username and password.
    - Backend checks `email_verified` status and validates credentials.
    - If valid, user is prompted to set a master password if logging in for the first time.

2. **Master Password Setup**:

    - User sets a master password for encrypting and decrypting notes.
    - Backend hashes and stores the master password.

3. **Token Issuance**:
    - Backend generates and updates access and refresh token details in the `Metadata` table.

### Note Management

1. **Create Note**:

    - Encrypted notes data send from backend to frontend along with master pin.
    - Backend derives the key from master pin after verifying the pin.
    - The notes data then encrypted and stored in the database.

2. **Retrieve Note**:

    - User send the master pin , backend verify then generate the master key then dcrypt the data and send response the frontend.

### Login Timeout Mechanism

- After 3 failed login attempts, `timeout_until` is updated in the `Metadata` table:
    - First lock: 3 minutes.
    - Second lock: 30 minutes.
    - Subsequent locks: Double the previous timeout duration.
- On successful login, `failed_attempts` is reset to 0.

### Token-Based Authentication

- **Access Token**:
    - Short-lived token for API requests.
    - Expiration tracked in the `Metadata` table.
- **Refresh Token**:
    - Used to obtain a new access token when expired.
    - Expiration: 24 hours, tracked in the `Metadata` table.

---

## Security Mechanisms

1. **Password Hashing**:
    - Argon2 or bcrypt for password and master password hashing.
2. **Encryption**:
    - AES-256-GCM for note encryption with authentication tags.
3. **Key Derivation**:
    - Argon2 for generating keys from master passwords.
4. **HTTPS**:
    - All communication is secured over HTTPS.
5. **Token Expiry**:
    - Token expirations are dynamically tracked in the `Metadata` table.

---

## Conclusion

The Secure Note offers a comprehensive, multi-layered security architecture to protect user data. The updated flow, database schema, and mechanisms ensure the confidentiality, integrity, and availability of sensitive information.
