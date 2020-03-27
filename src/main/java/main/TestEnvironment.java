package main;

import components.*;

public class TestEnvironment {
    public static void main(String[] args){
        Component test = new Component("Manufacturer", "Model", 299);

        System.out.println(test.getHeight());
    }
}
