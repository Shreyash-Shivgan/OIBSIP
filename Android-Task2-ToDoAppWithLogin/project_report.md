# Project Report: Task 2 - ToDo App With Login

## Overview
This application provides a secure offline task manager that supports multiple user accounts on the same device. Data security and encapsulation are maintained throughout.

## Relational Database Schema
- **`Users` Table**:
  - `id`: INTEGER PRIMARY KEY AUTOINCREMENT
  - `name`: TEXT
  - `email`: TEXT UNIQUE (prevents duplicate registrations)
  - `passwordHash`: TEXT (SHA-256 digested representation)
- **`Tasks` Table**:
  - `id`: INTEGER PRIMARY KEY AUTOINCREMENT
  - `userId`: INTEGER (Foreign key pointing to `Users.id`)
  - `title`: TEXT
  - `notes`: TEXT
  - `completed`: INTEGER (0 for false, 1 for true)

## Architecture & Code Design
- **Package `models`**: Houses `User.java` and `Task.java` mapping directly to the DB schema.
- **Package `database`**: `DatabaseHelper.java` handles table initialization, schema upgrades, and secure CRUD transactions.
- **Package `session`**: `SessionManager.java` uses `SharedPreferences` to manage user login state, user details, and logout clear calls.
- **Package `utils`**: `HashUtil.java` hashes raw user password entries using the standard SHA-256 algorithm.
- **Package `adapters`**: `TaskAdapter.java` connects task arrays with the layout container, managing completed styling (text strike-through/grey colors) and event dispatching.
- **Package `activities`**: Handles navigation flows and form inputs.

## Security Decisions
Password security is handled entirely on-device by computing the SHA-256 hash of the password combined with standard UTF-8 encoding. Plaintext passwords are never stored in memory long-term or committed to SQLite.

## Testing & Validation
- Account creation checks (duplicate emails are caught and flagged).
- Login validations (empty inputs, incorrect password mismatches).
- Context separation (logging in as User A shows only User A's tasks; logging in as User B shows User B's empty state or custom tasks).
- Task persistence (adding/editing/completing tasks maintains state across app restarts).
