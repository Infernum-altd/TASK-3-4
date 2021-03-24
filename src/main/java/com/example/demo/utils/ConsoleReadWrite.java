package com.example.demo.utils;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

@Log
public class ConsoleReadWrite {
    private static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    private static final String RED = "\033[0;31m";     // RED
    private static final String GREEN = "\033[0;32m";   // GREEN


    @SneakyThrows
    public static String readConsoleInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String readed = "";
        try {
            readed = reader.readLine();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return readed;
    }

    public static void showErrorMessage(String message) {
        System.out.println(RED + message + RESET);
    }

    @SneakyThrows
    public static void showErrorMessageWithTimePause(String message) {
        System.out.println(RED + message + RESET);
        Thread.sleep(2000);
    }

    @SneakyThrows
    public static void showSuccessfulMessageWithTimePause(String message) {
        System.out.println(GREEN + message + RESET);
        Thread.sleep(2000);
    }

    public static void showSuccessfulMessage(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }
}
