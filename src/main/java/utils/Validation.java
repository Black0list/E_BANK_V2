package main.java.utils;

import main.java.application.Main;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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
    public static Optional<UUID> safeParseUUID(String input) {
        try {
            return Optional.of(UUID.fromString(input));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
