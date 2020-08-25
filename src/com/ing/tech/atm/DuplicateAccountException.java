package com.ing.tech.atm;

public class DuplicateAccountException extends Throwable {
    public DuplicateAccountException() {
        super("Existing account");
    }
}
