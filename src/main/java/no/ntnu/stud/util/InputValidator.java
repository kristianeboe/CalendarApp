package no.ntnu.stud.util;

/**
 * Created by adrianh on 27.02.15.
 */
public class InputValidator {
    final static int maxLength = 45;
    final static int minLength = 3;

    public static String textInputValidator(String text) {
        return textInputValidator(text, minLength, maxLength);
    }

    public static String textInputValidator(String text, int maxLength) {
        return textInputValidator(text, minLength, maxLength);
    }

    public static String textInputValidator(String text, int minLength, int maxLength) {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException("Text is null");
        }
        else if (text.length() <= minLength || text.length() >= maxLength)
            throw new IllegalArgumentException("Text cannot be shorter than " + minLength + " or longer than " + maxLength);

        String allowed_chars = "abcdefghijklmnopqrstuvwxyzæøåABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ1234567890.,?!'\" -–";
        for (Character c : text.toCharArray()) {
            if (allowed_chars.indexOf(c) == -1)
                throw new IllegalArgumentException("Illegal character `" + c + "`");
        }

        return text;
    }
}
