package com.ing.tech.atm;

public class InvalidPinException extends Exception {
    public InvalidPinException() {
        super("Invalid pin, must be 4 digits");
    }
}
