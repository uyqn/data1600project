package main;

import components.*;

public class TestEnvironment {
    public static void main(String[] args){
        Cooler test = new Cooler("Cooler Master", "MasterFan SF360R", "36x12x2.5", "650-1800", "8-30", 483);

        System.out.println(test.toCSV());
    }
}
