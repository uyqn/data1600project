package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Component {

    private SimpleStringProperty name, manufacturer, model;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty height;
    private SimpleDoubleProperty width;
    private SimpleDoubleProperty length;

    public Component(String name, String manufacturer, String model,
                     double price) {
        this.name = new SimpleStringProperty(name);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getManufacturer() {
        return manufacturer.getValue();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public String getModel() {
        return model.getValue();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public double getPrice() {
        return price.getValue();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getHeight() {
        return height.getValue();
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public double getWidth() {
        return width.getValue();
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getLength() {
        return length.getValue();
    }

    public void setLength(double length) {
        this.length.set(length);
    }


    @Override
    public String toString(){
        return "Component" + name +":";
    }

}
