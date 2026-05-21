# ATM Simulation System

A console-based Automated Teller Machine (ATM) simulation written in Java. This project provides a basic banking interface with user authentication, account management (balance, withdrawal, deposit), and administrative capabilities.

## Features

- **User Authentication:** Secure PIN validation with a maximum of 3 failed attempts.
- **Account Operations:**
  - Balance Inquiry
  - Cash Withdrawal
  - Cash Deposit
- **Administrative Tools:**
  - Add/Delete accounts
  - View all existing accounts
- **Persistence:** Account data is saved to a file (`accounts.txt`), allowing data to persist between runs.

## Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher is required. Ensure `javac` and `java` are available in your system PATH.
- **IDE (Optional):** IntelliJ IDEA, Eclipse, or any other Java-compatible IDE if you prefer not to use the command line.

## Getting Started

### Using an IDE (e.g., IntelliJ IDEA)
1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the project directory `E:\Projects\ATMsim`.
3. Ensure the project structure is correctly recognized (Java source files are in `src/main/java/com/atmsim`).
4. Locate the `Main.java` file and run it.

### Using Command Line (Terminal)
1. Navigate to the project root directory where you cloned the repository:
   ```bash
   cd path/to/ATMsim
   ```
2. Compile the source code:
   ```bash
   javac -d bin src/main/java/com/atmsim/*.java
   ```
3. Run the application:
   ```bash
   java -cp bin com.atmsim.Main
   ```

## Usage

- **User Access:** Enter an account number to begin the authentication process.
- **Admin Access:** Enter `0` at the login prompt and provide the default admin password (`8888`) to access the Admin Menu.

### Sample Data
You can use the following credentials for testing:

| Role | Username / Account No. | PIN / Password |
| :--- | :--- | :--- |
| **Admin** | 0 | 8888 |
| **User** | 12345 | 1111 |
| **User** | 67890 | 2222 |
| **User** | 68333 | 3333 |

## Project Structure

- `com.atmsim.Main`: Entry point of the application.
- `com.atmsim.ATM`: Manages the ATM state machine and user interaction loop.
- `com.atmsim.BankDatabase`: Handles account data storage and retrieval.
- `com.atmsim.Account`: Represents a single user account.
- `com.atmsim.BalanceInquiry`, `com.atmsim.Withdrawal`, `com.atmsim.Deposit`: Transaction classes for specific operations.

---

