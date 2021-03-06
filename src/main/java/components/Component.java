package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Component implements Serializable, Listable, Interchangeable {
    private transient SimpleStringProperty manufacturer = new SimpleStringProperty();
    private transient SimpleStringProperty model = new SimpleStringProperty();
    private transient SimpleDoubleProperty price = new SimpleDoubleProperty();

    public Component(String manufacturer, String model,
                     double price) {
        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);
    }

    public String getName() {
        return getManufacturer() + " " + getModel();
    }

    public String getManufacturer() {
        return manufacturer.getValue();
    }

    public void setManufacturer(String manufacturer) {
        if(!manufacturer.matches("[A-Za-z. ]+|[A-Z][A-Za-z0-9]+")){
            throw new IllegalArgumentException("Invalid name format for manufacturer");
        }
        this.manufacturer.set(manufacturer.replaceAll("\\s{2,}", " ").trim());
    }

    public String getModel() {
        return model.getValue();
    }

    public void setModel(String model) {
        if(!model.matches("[A-Za-z0-9. \\-]+") || model.isBlank() || model.isEmpty()){
            throw new IllegalArgumentException("Invalid name format for model");
        }
        this.model.set(model.replaceAll("\\s{2,}", " "));
    }

    public double getPrice() {
        return price.getValue();
    }

    public void setPrice(double price) {
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price.set(price);
    }

    public abstract String toCSV();

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        this.manufacturer = new SimpleStringProperty();
        this.model = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();

        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);
    }
}
