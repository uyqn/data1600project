package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GPUTest {
    GPU test = new GPU("Manufacturer","Model", "PCIe 3.0x16",0, "DDR", 1000,2);

    @Test
    void setManufacturer(){
        test.setManufacturer("Nvidia");
        test.setManufacturer("AMD");

        assertThrows(IllegalArgumentException.class, ()-> test.setManufacturer(""));
        assertThrows(IllegalArgumentException.class, ()-> test.setManufacturer(" "));
    }

    @Test
    void setModel(){
        test.setModel("GeForce GTX 1650 SUPER");
        test.setModel("GeForce GTX 1660");
        test.setModel("GeForce RTX 2060");
        test.setModel("Radeon RX 570");
        test.setModel("Radeon RX 5700 XT");
        test.setModel("GeForce RTX 2080 Ti");
        test.setModel("GeForce RTX 2070 SUPER");

        assertThrows(IllegalArgumentException.class, () -> test.setModel(""));
        assertThrows(IllegalArgumentException.class, () -> test.setModel(" "));
    }

    @Test
    void setMemory(){
        test.setMemory(1);


    }

    @Test
    void setMemoryType(){
        test.setMemoryType("DDR");
        test.setMemoryType("DDR2");
        test.setMemoryType("GDDR2");
        test.setMemoryType("GDDR5X");
        test.setMemoryType("HBM");
        test.setMemoryType("HBM2");

        assertThrows(IllegalArgumentException.class, ()->test.setMemoryType(""));
        assertThrows(IllegalArgumentException.class, ()->test.setMemoryType(" "));
    }


    @Test
    void setClockSpeed() {
        test.setClockSpeed(1.2,4.99);
        test.setClockSpeed("1.2/4.99 GHz");
        test.setClockSpeed("1.2 / 4.99");
        test.setClockSpeed("1.2GHz 4.9GHz");
        test.setClockSpeed("1.2");

        assertThrows(IllegalArgumentException.class, () -> test.setClockSpeed(4.99, 1.2));
        assertThrows(IllegalArgumentException.class, () -> test.setClockSpeed(1.09,5.01));
        assertThrows(IllegalArgumentException.class, () -> test.setClockSpeed("-1.2"));
    }

}