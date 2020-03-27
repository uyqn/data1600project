package main;

import components.CPU;

public class TestEnvironment {
    public static void main(String[] args){
        CPU test = new CPU("Intel", "Core i9-9920X", 7599, "LGA2066", 12, "3.5/4.4",165);
        System.out.println(test.toCSV());
    }
}
