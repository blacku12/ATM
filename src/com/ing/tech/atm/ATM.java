package com.ing.tech.atm;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ATM {
    private static Map<UUID, Account> accounts;
    private static DBOperations operations;
    private Keyboard kb = new Keyboard();
    private Screen screen = new Screen(Locale.FRENCH);

    public ATM() {
        try {
            operations = new DBOperations();
            accounts = operations.readAccounts();
        } catch (IOException e) {
            screen.showMessage("ATM out of service");
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.mainScreen();
    }

    private void mainScreen() {

        int tries = 3;
        while (tries != 0) {
            screen.showMessage("Please insert the account number");
            String accountNumber = kb.getLine();
            screen.showEmptyLine();
            screen.showMessage("Please insert the pin:");
            String pin = kb.getLine();
            if (!logInValidation(accountNumber, pin)) {
                tries--;
                screen.showNumberOfTries(tries);
            } else {
                Account account = accounts.get(UUID.fromString(accountNumber));
                ATMMenu(account);
                return;
            }
        }
        screen.showMessage("Your account has been locked!");
    }

    private boolean logInValidation(String accountNumber, String pin) {
        try {
            UUID accountNr = UUID.fromString(accountNumber);
            Account account = accounts.get(accountNr);
            if (account == null || !account.pinCheck(pin)) {
                screen.showErrorMessage();
                return false;
            }
        } catch (IllegalArgumentException e) {
            screen.showErrorMessage();
            return false;
        }
        return true;
    }

    private void deposit(Account account) {
        while (true) {
            screen.showMessage("Please insert the deposit amount: ");
            try {
                BigDecimal amount = new BigDecimal(kb.getLine());
                account.addMoney(amount);
                break;
            } catch (IllegalAmountException | NumberFormatException e) {
                screen.showMessage("Incorrect amount");
            }
        }
    }

    private void withdraw(Account account) {
        boolean condition = true;
        while (condition) {
            screen.showWithdrawOptions();
            try {
                int option = kb.getOption();
                kb.getLine();
                switch (option) {
                    case 1:
                        account.withdraw(new BigDecimal(20));
                        condition = false;
                        break;
                    case 2:
                        account.withdraw(new BigDecimal(40));
                        condition = false;
                        break;
                    case 3:
                        account.withdraw(new BigDecimal(60));
                        condition = false;
                        break;
                    case 4:
                        account.withdraw(new BigDecimal(100));
                        condition = false;
                        break;
                    case 5:
                        account.withdraw(new BigDecimal(200));
                        condition = false;
                        break;
                    default:
                        screen.showOptionNotAvailable();
                        break;
                }
                screen.showBalance(account.getBalance());
            } catch (IllegalAmountException e) {
                screen.showMessage("Sarakule! Insufficient fonds!");
            } catch (InputMismatchException e) {
                screen.showOptionNotAvailable();
                kb.getLine();
            }
        }
    }

    private void ATMMenu(Account account) {
        screen.showAccount(account.getId());
        screen.showMainMenu();
        while (true) {
            try {
                int option = kb.getOption();
                kb.getLine();
                switch (option) {
                    case 1:
                        screen.showBalance(account.getBalance());
                        screen.showMainMenu();
                        break;
                    case 2:
                        deposit(account);
                        screen.showBalance(account.getBalance());
                        screen.showMainMenu();
                        break;
                    case 3:
                        withdraw(account);
                        screen.showBalance(account.getBalance());
                        screen.showMainMenu();
                        break;
                    case 9:
                        mainScreen();
                        break;
                    case 0:
                        operations.writeAccount(accounts);
                        System.exit(0);
                        break;
                    default:
                        screen.showOptionNotAvailable();
                }
            } catch (InputMismatchException e) {
                screen.showOptionNotAvailable();
                kb.getLine();
            } catch (IOException e) {
                screen.showMessage("ATM out of service");
            }
        }
    }

}
