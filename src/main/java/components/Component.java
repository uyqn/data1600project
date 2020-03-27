package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Component {

    private SimpleStringProperty manufacturer = new SimpleStringProperty();
    private SimpleStringProperty model = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleDoubleProperty width = new SimpleDoubleProperty();
    private SimpleDoubleProperty length = new SimpleDoubleProperty();
    private SimpleDoubleProperty height = new SimpleDoubleProperty();

    public Component(String manufacturer, String model,
                     double price) {
        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);

        setName();
    }

    public String getName() {
        return name.getValue();
    }

    public void setName() {
        this.name.set(getManufacturer() + " " + getModel());
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
