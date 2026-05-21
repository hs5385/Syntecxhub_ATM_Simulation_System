package com.atmsim;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankDatabase {
    private final Map<Integer, Account> accounts;
    private static final String FILE_PATH = "accounts.txt";

    public BankDatabase() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    private void loadAccounts() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Pre-populate if file doesn't exist
            accounts.put(12345, new Account(12345, 1111, 1000.00));
            accounts.put(67890, new Account(67890, 2222, 5000.00));
            saveAccounts();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int accNum = Integer.parseInt(parts[0]);
                    int pin = Integer.parseInt(parts[1]);
                    double balance = Double.parseDouble(parts[2]);
                    accounts.put(accNum, new Account(accNum, pin, balance));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    public void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Account account : accounts.values()) {
                writer.printf("%d,%d,%.2f%n", account.getAccountNumber(), account.getPin(), account.getBalance());
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    public boolean authenticateUser(int accountNumber, int userPIN) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            return account.validatePIN(userPIN);
        }
        return false;
    }

    public Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public void addAccount(int accountNumber, int pin, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account already exists.");
        }
        accounts.put(accountNumber, new Account(accountNumber, pin, initialBalance));
        saveAccounts();
    }

    public void deleteAccount(int accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account not found.");
        }
        accounts.remove(accountNumber);
        saveAccounts();
    }

    public Map<Integer, Account> getAllAccounts() {
        return new HashMap<>(accounts);
    }
}
