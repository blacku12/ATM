package com.ing.tech.atm;

public class IllegalAmountException extends Exception {
    public IllegalAmountException(String message) {
        super(message);
    }
}
