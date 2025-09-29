package main.java.utils;

import main.java.application.Main;

public class Validation {
    public static boolean AuthCheck(){
        return Main.USER.isPresent();
    }
}
