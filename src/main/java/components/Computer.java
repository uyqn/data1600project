package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class Computer {

    public SimpleStringProperty Name;
    public SimpleDoubleProperty Price;
    ArrayList<Component> listOfComponents;

    Motherboard mb;
    Case c;

    public Computer(String name, double price){
        Name = new SimpleStringProperty(name);
        Price = new SimpleDoubleProperty(price);
    }

    public Computer(String[] csv) {
        //setContent(csv[0]));
        setName(csv[1]);
        setPrice(Double.parseDouble(csv[2]));
    }

    public Double getPrice() {
        return Price.get();
    }

    public void setPrice(Double price) {
        this.Price.set(price);
    }

    public ArrayList<Component> getContent() {
        return listOfComponents;
    }

    public void setContent(Component... components) {
        listOfComponents.addAll(Arrays.asList(components));
    }

    public String getName() {
        return Name.get();
    }


    public void setName(String name) {
        this.Name.set(name);
    }

    public String toCSV() {
        StringBuilder csv = new StringBuilder(getName() + ",");
        for(Component component : listOfComponents){
            csv.append(component.toCSV()).append("\n");
        }
        csv.append(",").append(getPrice());

        return csv.toString();
    }
}