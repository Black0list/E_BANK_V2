package main.java.utils;

import main.java.application.Main;

import java.math.BigDecimal;

public class Validation {
    public static boolean AuthCheck(){
        return Main.USER.isPresent();
    }

    public static boolean isValidAmount(BigDecimal amount, int scale) {
        if (amount == null) {
            return false;
        }
        try {
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                return false;
            }

            return amount.scale() <= scale;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
