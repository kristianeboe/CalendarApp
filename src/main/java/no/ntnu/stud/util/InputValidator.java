package no.ntnu.stud.util;

/**
 * Created by adrianh on 27.02.15.
 */
public class InputValidator {

    public static boolean textInputValidator(String text) {
        if (text != null) {
            return (text.length() > 0 && text.length() <= 45);
        }
        return false;
    }
}
