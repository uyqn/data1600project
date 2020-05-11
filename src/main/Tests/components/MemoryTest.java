package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {

    Memory test=new Memory("Manufacturer", "Model", 0, 4,"DDR2" , 2000 );

    @Test
    void setManufacturer(){
        test.setManufacturer("Corsair");
        test.setManufacturer("IBM");
        test.setManufacturer("G.Skill");
        test.setManufacturer("GALAX");
        test.setManufacturer("KFA2");

        assertThrows(IllegalArgumentException.class, ()->test.setManufacturer(""));
        assertThrows(IllegalArgumentException.class, ()->test.setManufacturer(" "));


    }

    @Test
    void setModel() {
        test.setModel("Vengeance LPX");
        test.setModel("HyperX Fury");
        test.setModel("Venegeance RGB Pro");
        test.setModel("Ripjaws V Series");
        test.setModel("T-FORCE VULCAN Z");


        assertThrows(IllegalArgumentException.class, () -> test.setModel(""));
        assertThrows(IllegalArgumentException.class, () -> test.setModel(" "));

    }

    @Test
    void setRAM(){
        test.setRam(2);
        test.setRam(4);
        test.setRam(8);
        test.setRam(16);
        test.setRam(32);
        test.setRam(64);
        test.setRam(128);
        test.setRam(256);

        assertThrows(IllegalArgumentException.class, () -> test.setRam(0));
        assertThrows(IllegalArgumentException.class, () -> test.setRam(63));
        assertThrows(IllegalArgumentException.class, ()-> test.setRam(-13));


    }

    @Test
    void setSpeedTech(){
        test.setMemoryTech("DDR4");
        test.setMemoryTech("DDR");
        test.setMemoryTech("");
        test.setMemoryTech("");
        test.setMemoryTech("");
    }


    @Test
    void setSpeed(){
        test.setSpeed(2000);
        test.setSpeed(3455);


        assertThrows(IllegalArgumentException.class, () -> test.setSpeed(1));
        assertThrows(IllegalArgumentException.class, () -> test.setSpeed(-23));
        assertThrows(IllegalArgumentException.class, () -> test.setSpeed(100023));
        assertThrows(IllegalArgumentException.class, () -> test.setSpeed(9999));


    }

}