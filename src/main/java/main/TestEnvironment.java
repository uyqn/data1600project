package main;

import components.*;
import javafx.beans.property.SimpleStringProperty;

public class TestEnvironment {
    public static void main(String[] args){
        Cooler test = new Cooler("Cooler Master", "MasterFan SF360R", "36x12x2.5", "650-1800", "8-30", 483);

        SimpleStringProperty str = new SimpleStringProperty();

        System.out.println(str.getValue());
    }
}
