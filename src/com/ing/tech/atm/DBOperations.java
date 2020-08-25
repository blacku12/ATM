package com.ing.tech.atm;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class DBOperations {
    public static final String BANK_DB = ".\\work\\tema_curs-14\\bankDB.txt";
    private static Set<UUID> idSet = new HashSet<>();


    public DBOperations() throws IOException {
        idSet = readAccounts().keySet();
    }

    public void writeAccount(Account account) throws IOException, DuplicateAccountException {
        try (FileWriter writer = new FileWriter(BANK_DB, true)) {
            if (idSet.add(account.getId())) {
                writer.write(account.toString() + "\n");
            } else {
                throw new DuplicateAccountException();
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public void writeAccount(Map<UUID, Account> accountsMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BANK_DB, false));) {
            for (Account account : accountsMap.values()) {
                writer.write(account.toString() + "\n");
            }
        } catch (IOException e) {
            throw e;
        }

    }

    public Map<UUID, Account> readAccounts() throws IOException {
        Map<UUID, Account> accountHashMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BANK_DB));
            String line = reader.readLine();
            while (line != null) {
                String[] splittedLine = line.split(",");
                Account account = new Account(splittedLine[0], splittedLine[1], new BigDecimal(splittedLine[2]));
                accountHashMap.put(account.getId(), account);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw e;
        }
        return accountHashMap;
    }

}
