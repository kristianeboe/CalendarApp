package no.ntnu.stud.util;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by sklirg on 02/03/15.
 */
public class InputValidationTest {
    private static int minLength, maxLength;

    @BeforeClass
    public static void setUp() {
        minLength = 1;
        maxLength = 3;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoTextInput() {
        String input = "";
        InputValidator.textInputValidator(input, maxLength);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooShortTextInput() {
        String input = "a";
        InputValidator.textInputValidator(input, 2, maxLength);
    }

    @Test
    public void testLongEnoughTextInput() {
        String input = "ab";
        InputValidator.textInputValidator(input, minLength, maxLength);
        assertEquals(input, "ab");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongTextInput() {
        String input = "abcd";
        InputValidator.textInputValidator(input, maxLength);
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
            String str = "a" + s;
            InputValidator.textInputValidator(str, maxLength);
        }
    }
}
