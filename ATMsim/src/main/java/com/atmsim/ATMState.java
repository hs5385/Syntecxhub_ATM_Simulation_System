package com.atmsim;

public enum ATMState {
    IDLE,
    AUTHENTICATING,
    MAIN_MENU,
    ADMIN_MENU,
    TRANSACTION_WITHDRAWAL,
    TRANSACTION_DEPOSIT,
    TRANSACTION_BALANCE,
    LOCKED,
    EXIT
}
