package com.paymentapp.gfg.entity;

import java.util.UUID;

public class SendAmountData {

    private UUID userId;
    private String accountNumber;
    private long amount;

    public SendAmountData() {
    }

    public SendAmountData(UUID userId, String accountNumber, long amount) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
