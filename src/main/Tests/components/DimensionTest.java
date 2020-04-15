package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DimensionTest {
    Dimension test = new Dimension();

    @Test
    void setWidth() {
        test.setWidth(0.0);

        assertThrows(IllegalArgumentException.class, () -> test.setWidth(-1.0));
    }

    @Test
    void setDepth() {
        test.setDepth(0.0);

        assertThrows(IllegalArgumentException.class, () -> test.setDepth(-1.0));
    }

    @Test
    void setHeight() {
        test.setHeight(0.0);

        assertThrows(IllegalArgumentException.class, () -> test.setHeight(-1.0));
    }

    @Test
    void setDimension() {
        test.setDimension(0,0);
        test.setDimension(0,0,0);

        test.setDimension("0x0");
        test.setDimension("0x0x0");

        assertThrows(IllegalArgumentException.class, () -> test.setDimension(-1,-1));
        assertThrows(IllegalArgumentException.class, () -> test.setDimension(-1,-1,-1));

        assertThrows(IllegalArgumentException.class, () -> test.setDimension("0x0x"));
        assertThrows(IllegalArgumentException.class, () -> test.setDimension("-1x-1x-1"));
    }
}