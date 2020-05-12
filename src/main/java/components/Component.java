package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Component implements Serializable {
    private transient SimpleStringProperty manufacturer = new SimpleStringProperty();
    private transient SimpleStringProperty model = new SimpleStringProperty();
    private transient SimpleDoubleProperty price = new SimpleDoubleProperty();
    private transient SimpleObjectProperty<Dimension> dimension = new SimpleObjectProperty<>(new Dimension());

    public Component(String manufacturer, String model,
                     double price) {
        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);
    }

    public abstract String getComponentType();

    public String getName() {
        return getManufacturer() + " " + getModel();
    }

    public String getManufacturer() {
        return manufacturer.getValue();
    }

    public void setManufacturer(String manufacturer) {
        if(!manufacturer.matches("[A-Z][A-Za-z. ]+|[A-Z][A-Za-z0-9]+")){
            throw new IllegalArgumentException("Invalid name format for manufacturer");
        }
        this.manufacturer.set(manufacturer.replaceAll("\\s{2,}", " ").trim());
    }

    public String getModel() {
        return model.getValue();
    }

    public void setModel(String model) {
        if(!model.matches("[A-Za-z0-9 \\-]+") || model.isBlank() || model.isEmpty()){
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

    public void setWidth(double width){
        dimension.getValue().setWidth(width);
    }

    public double getWidth(){
        return dimension.getValue().getWidth();
    }

    public void setDepth(double depth){
        dimension.getValue().setDepth(depth);
    }

    public double getDepth(){
        return dimension.getValue().getDepth();
    }

    public void setHeight(double height){
        dimension.getValue().setHeight(height);
    }

    public double getHeight(){
        return dimension.getValue().getHeight();
    }

    public double getArea(){
        return dimension.getValue().getArea();
    }

    public double getVolume(){
        return dimension.getValue().getVolume();
    }

    public String getDimension() {
        return dimension.getValue().toString();
    }

    public void setDimension(double width, double depth){
        dimension.getValue().setWidth(width);
        dimension.getValue().setDepth(depth);
    }

    public void setDimension(double width, double depth, double height){
        dimension.getValue().setWidth(width);
        dimension.getValue().setDepth(depth);
        dimension.getValue().setHeight(height);
    }

    public void setDimension(String dimension){
        this.dimension.getValue().setDimension(dimension);
    }

    public void setDimension(Dimension dimension) {
        this.dimension.set(dimension);
    }

    public abstract String toCSV();

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
        objectOutputStream.writeObject(this.dimension.getValue());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        Dimension dimension = (Dimension) objectInputStream.readObject();

        this.manufacturer = new SimpleStringProperty();
        this.model = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.dimension = new SimpleObjectProperty<>();

        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);
        setDimension(dimension);
    }

    //"Dummy" methods for tableview:
    public abstract int getRpm();
    public abstract void setRpm(int rpm);

    public abstract double getCapacity();
    public abstract void setCapacity(double capacity);

    public abstract String getFormFactor();
    public abstract void setFormFactor(String formFactor);

    public abstract int getCoreRpm();
    public abstract void setCoreRpm(int coreRPM);
    public abstract int getMaxRpm();
    public abstract void setMaxRpm(int maxRPM);
    public abstract double getCoreNoise();
    public abstract void setCoreNoise(double coreNoise);
    public abstract double getMaxNoise();
    public abstract void setMaxNoise(double noise);
    public abstract double getPowerConsumption();
    public abstract void setPowerConsumption(double powerConsumption);

    public abstract String getSocket();
    public abstract void setSocket(String socket);
    public abstract int getCoreCount();
    public abstract void setCoreCount(int coreCount);
    public abstract double getCoreClock();
    public abstract void setCoreClock(double coreClock);
    public abstract double getBoostClock();
    public abstract void setBoostClock(double boostClock);

    public abstract String getBussType();
    public abstract void setBussType(String bussType);
    public abstract int getMemory();
    public abstract void setMemory(int memory);
    public abstract String getMemoryType();
    public abstract void setMemoryType(String memoryType);

    public abstract boolean isTactile();
    public abstract void setTactile(boolean tactile);

    public abstract int getRam();
    public abstract void setRam(int ram);
    public abstract String getMemoryTech();
    public abstract void setMemoryTech(String memoryTech);
    public abstract int getSpeed();
    public abstract void setSpeed(int speed);

    public abstract int getRefreshRate();
    public abstract void setRefreshRate(int refreshRate);

    public abstract int getRamSlots();
    public abstract void setRamSlots(int ramSlots);
    public abstract int getMaxRamSize();
    public abstract void setMaxRamSize(int maxRamSize);

    public abstract int getNumberButtons();
    public abstract void setNumberButtons(int numberButtons);
    public abstract int getDpi();
    public abstract void setDpi(int dpi);
    public abstract boolean isErgonomic();
    public abstract void setErgonomic(boolean ergonomic);
    public abstract boolean isWireless();
    public abstract void setWireless(boolean wireless);

    public abstract int getPowerCapacity();
    public abstract void setPowerCapacity(int powerCapacity);
}
