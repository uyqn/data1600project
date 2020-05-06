package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class Computer {

    public SimpleStringProperty Name;

    public SimpleDoubleProperty Price;
    public ArrayList<Component> Content = new ArrayList<Component>();

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
        return Content;
    }

    public void setContent(ArrayList<Component> content) {
        Content = content;
    }

    public String getName() {
        return Name.get();
    }


    public void setName(String name) {
        this.Name.set(name);
    }

    public String toCSV() {
        return Formatter.toCSV(
                getContent(),
                getName(),
                getPrice()
        );
    }
}