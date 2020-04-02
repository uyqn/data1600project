package components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    CPU test = new CPU("Manufacturer", "Model", "S1", 1, 1.1, 2, 10,0);

    @Test
    void setSocket() {
        test.setSocket("AM1");
        test.setSocket("AM2+");
        test.setSocket("AM3");
        test.setSocket("AM3+");
        test.setSocket("AM4");
        test.setSocket("BGA413");
        test.setSocket("BGA559");
        test.setSocket("BGA1023");
        test.setSocket("C32");
        test.setSocket("FM1");
        test.setSocket("FM2");
        test.setSocket("FM2+");
        test.setSocket("G34");
        test.setSocket("LGA771");
        test.setSocket("LGA775");
        test.setSocket("LGA1150");
        test.setSocket("LGA1151");
        test.setSocket("LGA1555");
        test.setSocket("LGA1156");
        test.setSocket("LGA1356");
        test.setSocket("LGA1366");
        test.setSocket("LGA2011");
        test.setSocket("LGA2011-3");
        test.setSocket("LGA2066");
        test.setSocket("PGA988");
        test.setSocket("sTR4");
        test.setSocket("sTRX4");

        assertThrows(IllegalArgumentException.class, () -> test.setSocket(""));
        assertThrows(IllegalArgumentException.class, () -> test.setSocket(" "));
    }

    @Test
    void setCoreCount() {
        test.setCoreCount(1);
        test.setCoreCount(2);
        test.setCoreCount(3);
        test.setCoreCount(32);
        test.setCoreCount(64);

        assertThrows(IllegalArgumentException.class, () -> test.setCoreCount(0));
        assertThrows(IllegalArgumentException.class, () -> test.setCoreCount(63));
    }

    @Test
    void setClockSpeed() {
        test.setClockSpeed(1.2,4.99);
        test.setClockSpeed("1.2/4.99 GHz");
        test.setClockSpeed("1.2 / 4.99");
        test.setClockSpeed("1.2GHz-4.9GHz");
        test.setClockSpeed("1.2");

        assertThrows(IllegalArgumentException.class, () -> test.setClockSpeed(4.99, 1.2));
        assertThrows(IllegalArgumentException.class, () -> test.setClockSpeed(1.09,5.01));
        assertThrows(IllegalArgumentException.class, () -> test.setClockSpeed("-1.2"));
    }

    @Test
    void setTdp() {
        test.setPowerConsumption(10);
        test.setPowerConsumption(300);

        assertThrows(IllegalArgumentException.class, () -> test.setPowerConsumption(9));
        assertThrows(IllegalArgumentException.class, () -> test.setPowerConsumption(301));
    }
}