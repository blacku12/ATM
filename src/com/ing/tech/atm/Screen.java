package com.ing.tech.atm;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

public class Screen {
    private ResourceBundle labels;

    public Screen(Locale locale) {
        labels = ResourceBundle.getBundle("Labels", locale);
    }


    public void showMainMenu() {
        System.out.println("---------------------------");
        System.out.println(labels.getString("menu.myBalance"));
        System.out.println(labels.getString("menu.deposit"));
        System.out.println(labels.getString("menu.withdraw"));
        System.out.println(labels.getString("menu.logOut"));
        System.out.println(labels.getString("menu.exit"));
        System.out.println("---------------------------");
        System.out.println(labels.getString("menu.choice"));
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showEmptyLine() {
        System.out.println();
    }

    public void showNumberOfTries(int tries) {
        System.out.println("You have " + tries + " tries left");
    }

    public void showErrorMessage() {
        System.out.println(labels.getString("err.invalidLoginCredential"));
    }

    public void showWithdrawOptions() {
        System.out.println("Please chose the withdraw amount: ");
        System.out.println("1 -> 20$, 2 -> 40$, 3 -> 60$, 4 -> 100$, 5 -> 200$");
    }

    public void showBalance(BigDecimal balance) {
        System.out.println("Balance: " + balance + " $");
    }

    public void showAccount(UUID id) {
        System.out.println("Account of " + id);
    }

    public void showOptionNotAvailable() {
        System.out.println("Option not available");
    }

}
