package no.ntnu.stud.util;

/**
 * Created by adrianh on 27.02.15.
 */
public class InputValidator {

    public static String textInputValidator(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text is null");
        }
        else if (text.length() <= 3 || text.length() >= 45)
            throw new IllegalArgumentException("Text cannot be shorter than 3 or longer than 45");

        String allowed_chars = "abcdefghijklmnopqrstuvwxyzæøå1234567890.,?!'\"";
        for (Character c : text.toCharArray()) {
            if (allowed_chars.indexOf(c) == -1)
                throw new IllegalArgumentException("Illegal character " + c);
        }

        return text;
    }
}
