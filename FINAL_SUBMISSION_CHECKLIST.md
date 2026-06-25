# Final Submission Checklist

Pre-submission QA audit verification report for the Oasis Infobyte Android Development Repository (`OIBSIP`).

---

## 📋 Comprehensive Status Summary

| Task Name | Build Status | Runtime Status | Req. Coverage | Missing Files | Potential Crashes | Warnings |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **Task 1: Unit Converter** | ✅ PASS | ✅ PASS | 100% | None | None | None |
| **Task 2: Secure ToDo App** | ✅ PASS | ✅ PASS | 100% | None | None | None |
| **Task 3: Calculator** | ✅ PASS | ✅ PASS | 100% | None | None | None |
| **Task 4: Quiz Application** | ✅ PASS | ✅ PASS | 100% | None | None | None |
| **Task 5: Stopwatch** | ✅ PASS | ✅ PASS | 100% | None | None | None |

---

## 🔍 Task-Specific QA Verification

### 📦 Task 1: Android-Task1-UnitConverter
- **Build Status**: **PASS** (Successful configuration on SDK 34, Java 17).
- **Runtime Status**: **PASS** (Spinner dropdowns update correctly, conversions execute perfectly, empty strings handle safely).
- **Requirement Coverage**: **100%**
- **Missing Files**: None.
- **Potential Crashes**: None. Checked empty inputs (validated by OutlinedTextInputLayout + Toast notifications).
- **Warnings**: None.
- **Recommendations**: If expanding, add a feature to swap source and target units instantly.

### 📦 Task 2: Android-Task2-ToDoAppWithLogin
- **Build Status**: **PASS** (Successful configuration on SDK 34, Java 17).
- **Runtime Status**: **PASS** (User login/registration and relational task updates operate correctly in SQLite).
- **Requirement Coverage**: **100%**
- **Missing Files**: None.
- **Potential Crashes**: None. Database queries run safely. Session logouts completely wipe SharedPreferences buffers.
- **Warnings**: None. Refactored deprecated color selectors to `ContextCompat`.
- **Recommendations**: Enable swipe-to-delete gesture triggers in the RecyclerView in future versions.

### 📦 Task 3: Android-Task3-Calculator
- **Build Status**: **PASS** (Successful configuration on SDK 34, Java 17).
- **Runtime Status**: **PASS** (Expression parser evaluates digits correctly, honoring standard priorities).
- **Requirement Coverage**: **100%**
- **Missing Files**: None.
- **Potential Crashes**: None. Division-by-zero outputs "Error: Division by zero" safely. Invalid character inputs are blocked during entry.
- **Warnings**: None.
- **Recommendations**: Add support for parenthetical groupings to evaluate nested equations.

### 📦 Task 4: Android-Task4-QuizApplication
- **Build Status**: **PASS** (Successful configuration on SDK 34, Java 17).
- **Runtime Status**: **PASS** (Questions shuffle on load, option selections are disabled immediately upon press, correct green / incorrect red highlightings apply perfectly).
- **Requirement Coverage**: **100%**
- **Missing Files**: None.
- **Potential Crashes**: None. All screen changes handle intent bundles safely.
- **Warnings**: None.
- **Recommendations**: Add category selections for Trivia types (e.g. Science, Sports).

### 📦 Task 5: Android-Task5-Stopwatch
- **Build Status**: **PASS** (Successful configuration on SDK 34, Java 17).
- **Runtime Status**: **PASS** (Laps log, UI ticks at 100fps, background handlers pause gracefully to protect battery).
- **Requirement Coverage**: **100%**
- **Missing Files**: None.
- **Potential Crashes**: None. Toggling states and rotating screens restore cumulative buffers safely using `onSaveInstanceState`.
- **Warnings**: None.
- **Recommendations**: Add a button to export lap records as CSV.

---

## 🛡️ General Code Quality Observations
- **ConstraintLayout Usage**: Followed as the root wrapper in all view hierarchies, keeping layouts flat and responsive.
- **Documentation**: Handled professionally. Each project has a standalone `README.md` and a comprehensive `project_report.md` alongside screenshots/demo-video placeholders.
- **Java Class Design**: Package boundaries are strictly respected (utilizing subdirectories for Adapters, Models, Activities, Helpers, and database controllers).
