package com.atmsim;

public class Withdrawal implements Transaction {
    private final Account account;
    private final double amount;

    public Withdrawal(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        try {
            account.debit(amount);
            System.out.printf("Successfully withdrew $%.2f. New balance: $%.2f%n", amount, account.getBalance());
        } catch (IllegalArgumentException e) {
            System.err.println("Transaction failed: " + e.getMessage());
        }
    }
}
