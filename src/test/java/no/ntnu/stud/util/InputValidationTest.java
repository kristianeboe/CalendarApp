package no.ntnu.stud.util;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by sklirg on 02/03/15.
 */
public class InputValidationTest {
    @Ignore // @Test(expect = IllegalArgumentException.class)
    public void testNoTextInput() {
        String input = "";
        // WhateverTheMethodisCalled(input);
        assertEquals(input, "");
    }

    @Ignore // @Test(expect = IllegalArgumentException.class)
    public void testTooShortTextInput() {
        String input = "te";
        // WhateverTheMethodisCalled(input);
        assertEquals(input, "");
    }

    @Ignore // @Test(expect = IllegalArgumentException.class)
    public void testLongEnoughTextInput() {
        String input = "abcdefghijklmnopqrstuv";
        // WhateverTheMethodisCalled(input);
        assertEquals(input, "");
    }

    @Ignore // @Test(expect = IllegalArgumentException.class)
    public void testTooLongTextInput() {
        String input = "abcdefghijklmnopqrstuv1234567890";
        // WhateverTheMethodisCalled(input);
        assertEquals(input, "");
    }
}
