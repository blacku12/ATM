package com.ing.tech.atm;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Account {
    private UUID id;
    private String pin;
    private BigDecimal balance;

    public Account(String pin, BigDecimal startingAmount) throws InvalidPinException {
        if (!validationPin(pin)) throw new InvalidPinException();
        this.pin = pin;
        this.balance = startingAmount;
        id = UUID.randomUUID();
    }

    public Account(String id, String pin, BigDecimal startingAmount) {
        this.pin = pin;
        this.balance = startingAmount;
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return id;
    }

    private static boolean validationPin(String s) {
        return Pattern.matches("\\d{4}", s);
    }

    public boolean pinCheck(String s) {
        return validationPin(s) && s.equals(pin);
    }

    public void addMoney(BigDecimal amount) throws IllegalAmountException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAmountException("Value must be positive");
        }
        this.balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) throws IllegalAmountException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAmountException("Value must be positive");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalAmountException("Sarakule! insufficient fonds");
        }
        this.balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return id + "," + pin + "," + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
