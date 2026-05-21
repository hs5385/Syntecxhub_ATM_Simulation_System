package com.atmsim;

import java.util.Scanner;

public class ATM {
    private final BankDatabase bankDatabase;
    private ATMState currentState;
    private Account currentAccount;
    private final Scanner scanner;
    private int pinAttempts;
    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final int ADMIN_PASSWORD = 8888; // Default admin password

    public ATM() {
        this.bankDatabase = new BankDatabase();
        this.currentState = ATMState.IDLE;
        this.scanner = new Scanner(System.in);
        this.pinAttempts = 0;
    }

    public void run() {
        System.out.println("Welcome to the ATM Simulation!");
        while (currentState != ATMState.EXIT) {
            switch (currentState) {
                case IDLE:
                    promptForAccountNumber();
                    break;
                case AUTHENTICATING:
                    authenticateUser();
                    break;
                case MAIN_MENU:
                    displayMainMenu();
                    break;
                case ADMIN_MENU:
                    displayAdminMenu();
                    break;
                case LOCKED:
                    System.err.println("Your account is locked due to too many failed attempts.");
                    currentState = ATMState.IDLE;
                    break;
                default:
                    currentState = ATMState.IDLE;
                    break;
            }
        }
        System.out.println("Thank you for using our ATM. Goodbye!");
    }

    private void promptForAccountNumber() {
        System.out.println("\nOptions: [Account Number] or [0] for Admin Menu");
        System.out.print("Please enter your choice: ");
        if (scanner.hasNextInt()) {
            int input = scanner.nextInt();
            if (input == 0) {
                System.out.print("Enter Admin Password: ");
                if (scanner.hasNextInt()) {
                    int pass = scanner.nextInt();
                    if (pass == ADMIN_PASSWORD) {
                        currentState = ATMState.ADMIN_MENU;
                    } else {
                        System.err.println("Incorrect Admin Password!");
                    }
                } else {
                    System.err.println("Invalid numeric password.");
                    scanner.next();
                }
                return;
            }
            currentAccount = bankDatabase.getAccount(input);
            if (currentAccount != null) {
                currentState = ATMState.AUTHENTICATING;
            } else {
                System.err.println("Invalid account number. Please try again.");
            }
        } else {
            System.err.println("Please enter a valid numeric input.");
            scanner.next(); // Clear invalid input
        }
    }

    private void displayAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Add New Account");
        System.out.println("2. Delete Account");
        System.out.println("3. View All Accounts");
        System.out.println("4. Back to Login");
        System.out.print("Choose an option: ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleAddAccount();
                    break;
                case 2:
                    handleDeleteAccount();
                    break;
                case 3:
                    handleViewAccounts();
                    break;
                case 4:
                    currentState = ATMState.IDLE;
                    break;
                default:
                    System.err.println("Invalid choice. Please select 1-4.");
            }
        } else {
            System.err.println("Please enter a numeric choice.");
            scanner.next();
        }
    }

    private void handleViewAccounts() {
        System.out.println("\n--- All Accounts ---");
        System.out.printf("%-15s %-10s%n", "Account No.", "Balance");
        bankDatabase.getAllAccounts().forEach((accNum, account) -> {
            System.out.printf("%-15d $%-10.2f%n", accNum, account.getBalance());
        });
    }

    private void handleAddAccount() {
        System.out.print("Enter new account number: ");
        int accNum = scanner.nextInt();
        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();

        try {
            bankDatabase.addAccount(accNum, pin, balance);
            System.out.println("Account added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleDeleteAccount() {
        System.out.print("Enter account number to delete: ");
        int accNum = scanner.nextInt();

        try {
            bankDatabase.deleteAccount(accNum);
            System.out.println("Account deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void authenticateUser() {
        System.out.print("Enter your PIN: ");
        if (scanner.hasNextInt()) {
            int pin = scanner.nextInt();
            if (currentAccount.validatePIN(pin)) {
                System.out.println("Authentication successful.");
                currentState = ATMState.MAIN_MENU;
                pinAttempts = 0;
            } else {
                pinAttempts++;
                System.err.printf("Incorrect PIN. Attempts remaining: %d%n", MAX_PIN_ATTEMPTS - pinAttempts);
                if (pinAttempts >= MAX_PIN_ATTEMPTS) {
                    currentState = ATMState.LOCKED;
                }
            }
        } else {
            System.err.println("Please enter a valid numeric PIN.");
            scanner.next(); // Clear invalid input
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    new BalanceInquiry(currentAccount).execute();
                    break;
                case 2:
                    handleWithdrawal();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    currentState = ATMState.IDLE;
                    currentAccount = null;
                    break;
                default:
                    System.err.println("Invalid choice. Please select 1-4.");
            }
        } else {
            System.err.println("Please enter a numeric choice.");
            scanner.next();
        }
    }

    private void handleWithdrawal() {
        System.out.print("Enter amount to withdraw: ");
        if (scanner.hasNextDouble()) {
            double amount = scanner.nextDouble();
            new Withdrawal(currentAccount, amount).execute();
            bankDatabase.saveAccounts();
        } else {
            System.err.println("Invalid amount.");
            scanner.next();
        }
    }

    private void handleDeposit() {
        System.out.print("Enter amount to deposit: ");
        if (scanner.hasNextDouble()) {
            double amount = scanner.nextDouble();
            new Deposit(currentAccount, amount).execute();
            bankDatabase.saveAccounts();
        } else {
            System.err.println("Invalid amount.");
            scanner.next();
        }
    }
}
