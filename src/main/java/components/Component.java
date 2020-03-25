package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Component {

    private SimpleStringProperty name, manufacturer, model;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty height;
    private SimpleDoubleProperty width;
    private SimpleDoubleProperty length;


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getHeight() {
        return height.get();
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public double getWidth() {
        return width.get();
    }


    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getLength() {
        return length.get();
    }

    public void setLength(double length) {
        this.length.set(length);
    }


    @Override
    public String toString(){
        return "Component" + name +":";
    }

}
