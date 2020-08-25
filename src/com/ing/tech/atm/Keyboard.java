package com.ing.tech.atm;

import java.util.Scanner;

public class Keyboard {
    private static Scanner scanner = new Scanner(System.in);

    public String getLine() {
        return scanner.nextLine();
    }

    public int getOption(){
        return scanner.nextInt();
    }
}
