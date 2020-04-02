package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTest {
    Component test = new Component("Manufacturer", "Model", 0);

    @Test
    void setManufacturer() {
        test.setManufacturer("AMD");
        test.setManufacturer("Intel");

        assertThrows(IllegalArgumentException.class, () -> test.setManufacturer(""));
        assertThrows(IllegalArgumentException.class, () -> test.setManufacturer(" "));
    }

    @Test
    void setModel() {
        test.setModel("Ryzen 5 3600");
        test.setModel("Ryzen 5 PRO 2600");
        test.setModel("Ryzen 5 3400G");
        test.setModel("Ryzen 9 3950X");
        test.setModel("Core i7-4960X Extreme Edition");
        test.setModel("Core i9-9900K");
        test.setModel("Core i9-9900KS");
        test.setModel("Core i9-10900X");

        assertThrows(IllegalArgumentException.class, () -> test.setModel(""));
        assertThrows(IllegalArgumentException.class, () -> test.setModel(" "));
    }

    @Test
    void setPrice() {
        test.setPrice(0.0);

        assertThrows(IllegalArgumentException.class, () -> test.setPrice(-1.0));
    }

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

        test.setDimension(new Dimension(0,0));
        test.setDimension(new Dimension(0,0,0));

        assertThrows(IllegalArgumentException.class, () -> test.setDimension(-1,-1));
        assertThrows(IllegalArgumentException.class, () -> test.setDimension(-1,-1,-1));

        assertThrows(IllegalArgumentException.class, () -> test.setDimension("0x0x"));
        assertThrows(IllegalArgumentException.class, () -> test.setDimension("-1x-1x-1"));

        assertThrows(IllegalArgumentException.class, () -> test.setDimension(new Dimension(-1,-1)));
        assertThrows(IllegalArgumentException.class, () -> test.setDimension(new Dimension(-1,-1,-1)));
    }
}