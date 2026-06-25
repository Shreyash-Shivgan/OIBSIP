# Task 2 - Secure To-Do App with Login

A secure local To-Do list manager for Android featuring user authentication, password hashing, and user-specific local data storage.

## Features
- **User Authentication**: Secure Register and Login screens.
- **Security Protocols**: SHA-256 password hashing. Zero plain-text passwords stored in the database.
- **Session Management**: Session persistence using `SharedPreferences` (remains logged in until explicit logout).
- **SQLite Local DB**: Fully customized databases containing two relational tables: `Users` and `Tasks`.
- **User-Specific Tasks**: Users only see and modify their own tasks.
- **Complete CRUD Operations**: Users can add tasks, edit existing task details, mark tasks as complete/incomplete, and delete tasks.
- **Empty State UX**: Beautiful fallback messaging when there are no tasks in the list.

## Screenshots

<p align="center">
  <img src="asset/st1.jpg" width="160" />
  <img src="asset/st2.jpg" width="160" />
  <img src="asset/st3.jpg" width="160" />
  <img src="asset/st4.jpg" width="160" />
  <img src="asset/st5.jpg" width="160" />
  <img src="asset/st6.jpg" width="160" />
  <img src="asset/st7.jpg" width="160" />
  <img src="asset/st8.jpg" width="160" />
  <img src="asset/st9.jpg" width="160" />
  <img src="asset/st10.jpg" width="160" />
  <img src="asset/st11.jpg" width="160" />
</p>

## Tech Stack
- **Languages**: Java, XML
- **Minimum SDK**: 24
- **Target SDK**: 34
- **Database**: SQLite
- **Session Storage**: SharedPreferences
- **Layout**: ConstraintLayout, CardView, RecyclerView

## Installation
1. Download or clone this folder.
2. Open Android Studio and select **Open**.
3. Select the `Android-Task2-ToDoAppWithLogin` directory.
4. Let Gradle sync completely.
5. Build and run.

## Future Improvements
- Integrate Firebase Auth / Firestore for cloud syncing.
- Add task categorization and search tags.
- Support push notification task reminders.
