package com.atmsim;

public class BalanceInquiry implements Transaction {
    private final Account account;

    public BalanceInquiry(Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        System.out.printf("Current balance: $%.2f%n", account.getBalance());
    }
}
