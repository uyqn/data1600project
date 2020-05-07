package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CabinTest {

    Cabin test = new Cabin("Test", "Test", "Test test TEST", 0);

    @Test
    void setFormFactor() {
        test.setFormFactor("ATX");
        test.setFormFactor("Extended-ATX");
        test.setFormFactor("EATX");
        test.setFormFactor("E-ATX");
        test.setFormFactor("Thin Mini ATX");
        test.setFormFactor("AT");
        test.setFormFactor("Flex ATX");
        test.setFormFactor("HPTX");
        test.setFormFactor("Micro ATX");
        test.setFormFactor("microATX");
        test.setFormFactor("mATX");
        test.setFormFactor("Mini ITX");
        test.setFormFactor("SSI CEB");
        test.setFormFactor("SSI EEB");
        test.setFormFactor("XL ATX");

        assertThrows(IllegalArgumentException.class, () -> test.setFormFactor(""));
        assertThrows(IllegalArgumentException.class, () -> test.setFormFactor("        "));
        assertThrows(IllegalArgumentException.class, () -> test.setFormFactor("123"));
        assertThrows(IllegalArgumentException.class, () -> test.setFormFactor("This is not a valid formfactor"));
        assertThrows(IllegalArgumentException.class, () -> test.setFormFactor("ATX1"));
    }
}