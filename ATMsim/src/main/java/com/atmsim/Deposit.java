package com.atmsim;

public class Deposit implements Transaction {
    private final Account account;
    private final double amount;

    public Deposit(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        if (amount > 0) {
            account.credit(amount);
            System.out.printf("Successfully deposited $%.2f. New balance: $%.2f%n", amount, account.getBalance());
        } else {
            System.err.println("Transaction failed: Deposit amount must be positive.");
        }
    }
}
