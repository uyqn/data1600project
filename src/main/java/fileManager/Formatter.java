package fileManager;

import components.CPU;
import components.Component;
import components.Cooler;
import javafx.scene.control.Alert;

public class Formatter<T> {
    public static final String DELIMITER = ",";

    public static String toCSV(String DELIMITER, Object... args){
        StringBuilder format = new StringBuilder();

        for (Object object : args) {
            format.append("%s;");
        }

        return String.format(format.substring(0,format.length() - 1).replaceAll(";", DELIMITER), args);
    }

    public static String toCSV(Object... args){
        StringBuilder format = new StringBuilder();

        for (Object object : args) {
            format.append("%s;");
        }

        return String.format(format.substring(0,format.length() - 1).replaceAll(";", DELIMITER), args);
    }

    public Component fromCSV(String[] csv){
        switch (csv[0]){
            case "CPU": return fromCSVtoCPU(csv);
            case "Cooler": return fromCSVtoCooler(csv);
            default: return null;
        }
    }

    private CPU fromCSVtoCPU(String[] csv){
        try {
            String manufacturer = csv[1];
            String model = csv[2];
            String socket = csv[3];
            int coreCount = Integer.parseInt(csv[4]);
            String clockSpeed = csv[5];
            int powerConsumption = Integer.parseInt(csv[6]);
            double price = Double.parseDouble(csv[7]);

            return new CPU(manufacturer, model, socket, coreCount, clockSpeed, powerConsumption, price);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Cooler fromCSVtoCooler(String[] csv){
        try{
            String manufacturer = csv[1];
            String model = csv[2];
            String dimension = csv[3];
            String rpm = csv[4];
            String noise = csv[5];
            double price = Double.parseDouble(csv[6]);

            return new Cooler(manufacturer, model, dimension, rpm, noise, price);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
