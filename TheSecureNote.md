# Password Manager Application: Architecture and Security Mechanisms

## Overview

This document provides an in-depth explanation of the password manager application's architecture, focusing on its robust security mechanisms for storing and retrieving encrypted notes.

## Core Security Principles

The application implements a multi-layered security approach to protect user data:
1. **Password Hashing**: User passwords are securely hashed before storage
2. **Request Encryption**: Frontend uses a secret key to encrypt request data
3. **End-to-End Encryption**: Notes are encrypted using the user's password
4. **Encryption Algorithm**: AES-GCM (Galois/Counter Mode) for authenticated encryption
5. **Key Derivation**: Argon2 used for secure key generation

## Request and Response Flow

The individual fields of the request and response are encrypted using the AES-GCM algorithm. The encryption key will be stored at frontend and backend. The key will be used to encrypt and decrypt the data.
1. **Field-Level Encryption**: Each field in the request and response is encrypted individually using AES-GCM, ensuring that even if one part of the data is compromised, the rest remains secure.
2. **Symmetric Key Storage**: The encryption key is stored securely at both the frontend and backend, allowing for seamless encryption and decryption of data during transmission.
3. **Authenticated Encryption**: AES-GCM provides both encryption and authentication, ensuring data integrity and confidentiality.
4. **Key Management**: Proper key management practices are followed to store and protect the encryption keys at both ends.
5. **Data Security**: Encrypting individual fields enhances the overall security of the data, making it more resistant to unauthorized access and tampering

## User Registration and Authentication Flow

### Registration Process
1. User provides username, email, and password
2. Password is hashed using a secure hashing algorithm (e.g., bcrypt or Argon2)
3. Hashed password and user details are stored in the `User` table
4. A unique salt is used during hashing to prevent rainbow table attacks

### Login Process
1. User submits username and password
2. Backend retrieves the stored hashed password
3. Submitted password is hashed and compared with stored hash
4. If credentials are valid, a JWT (JSON Web Token) is generated
5. Token is sent to frontend for subsequent authenticated requests

## Note Encryption Workflow

### Note Creation
1. Frontend Encryption Stage
   - User enters note title and description
   - Frontend encrypts request data using a predefined secret key
   - Encrypted data is sent to backend

2. Backend Decryption and Processing
   - Backend decrypts the incoming request
   - Validates user credentials
   - Retrieves user's original password
   - Derives encryption key using Argon2
     - Input: User's original password
     - Purpose: Generate a cryptographically secure key

3. Note Encryption
   - Uses AES-GCM for authenticated encryption
   - Inputs:
     - Plaintext description
     - Derived key
   - Outputs:
     - Ciphertext
     - Authentication Tag
     - Nonce (Number used once)
   - Concatenates ciphertext + tag + nonce
   - Stores result in `Notes` table

### Note Retrieval
1. Fetch encrypted note from database
2. Separate concatenated string into:
   - Ciphertext
   - Tag
   - Nonce
3. Derive key using same Argon2 process
4. Decrypt using AES-GCM
5. Return decrypted note to frontend

## Database Schema

### `User` Table
| Field      | Type    | Description                      |
|------------|---------|----------------------------------|
| `id`       | INT     | Unique user identifier           |
| `username` | VARCHAR | User's username                  |
| `email`    | VARCHAR | User's email address             |
| `password` | VARCHAR | Securely hashed user password    |

### `Notes` Table
| Field         | Type    | Description                                         |
|---------------|---------|-----------------------------------------------------|
| `id`          | INT     | Unique note identifier                              |
| `user_id`     | INT     | Foreign key referencing `User` table                |
| `title`       | VARCHAR | Note title (stored in plaintext)                    |
| `description` | TEXT    | Encrypted description + Authentication Tag + Nonce  |

## Security Considerations

### Potential Vulnerabilities and Mitigations
- **Man-in-the-Middle Attacks**: Use HTTPS for all communications
- **Brute Force Attacks**: Implement account lockout and rate limiting
- **Key Compromise**: Use short-lived encryption keys, regularly rotate
- **Password Strength**: Enforce strong password policies

### Recommended Enhancements
- Implement multi-factor authentication
- Add time-based key rotation
- Use hardware security modules (HSM) for key management
- Regular security audits and penetration testing

## Encryption Libraries and Recommendations

- **Key Derivation**: Argon2 (Recommended)
- **Hashing**: bcrypt or Argon2id
- **Encryption**: AES-256-GCM
- **Token**: JWT with short expiration

## Conclusion

This architecture provides a robust, multi-layered approach to securing user notes, ensuring confidentiality and integrity of sensitive information.
