package com.pino.java_unit_test_sample.util;

import java.util.regex.Pattern;

public class IdentityNumberUtils {
    private IdentityNumberUtils() {}

    public static boolean isValid(String identityNumber) {
        // Check rule ref: https://web.fg.tp.edu.tw/~anny/idtest.htm
        var pattern = "^[A-Z][1-2]\\d{8}$";
        var matcher = Pattern.compile(pattern).matcher(identityNumber);
        return matcher.matches() && isValidChecksum(identityNumber);
    }

    private static boolean isValidChecksum(String identityNumber) {
        if (identityNumber.length() != 10) {
            return false;
        }

        int[] weights = { 1, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        char[] idChars = identityNumber.toCharArray();

        var total = 0;
        var firstCharValue = letterToCode(idChars[0]);
        total += (firstCharValue / 10) * weights[0];
        total += (firstCharValue % 10) * weights[1];
        for (int i = 1; i < 9; i++) {
            int digit = Character.getNumericValue(idChars[i]);
            total += digit * weights[i + 1];
        }

        int checksum = Character.getNumericValue(idChars[9]);

        return (total + checksum) % 10 == 0;
    }

    private static int letterToCode(char c) {
        var letterSequence = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        return letterSequence.indexOf(c) + 10;
    }
}
