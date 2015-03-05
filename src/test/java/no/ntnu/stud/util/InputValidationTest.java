package no.ntnu.stud.util;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by sklirg on 02/03/15.
 */
public class InputValidationTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNoTextInput() {
        String input = "";
        InputValidator.textInputValidator(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooShortTextInput() {
        String input = "te";
        InputValidator.textInputValidator(input);
    }

    @Test
    public void testLongEnoughTextInput() {
        String input = "abcdefghijklmnopqrstuv";
        InputValidator.textInputValidator(input);
        assertEquals(input, "abcdefghijklmnopqrstuv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongTextInput() {
        String input = "abcdefghijklmnopqrstuv123456789012345678901234567890";
        InputValidator.textInputValidator(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCharacters() {
        String[] inputs = new String[10];
        inputs[0] = "~";
        inputs[1] = "±";
        inputs[2] = "*";
        inputs[3] = "¡";
        inputs[4] = "¿";
        inputs[5] = "#";
        inputs[6] = "$";
        inputs[7] = "=";
        inputs[8] = "@";
        inputs[9] = "\\";
        for (String s : inputs) {
            String str = "Something" + s;
            InputValidator.textInputValidator(str);
        }
    }
}
