package components;

import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CPU extends Component implements Serializable, Compatible {
    public static final transient String COMPONENT_TYPE = "CPU";

    private transient SimpleStringProperty socket = new SimpleStringProperty();
    private transient SimpleIntegerProperty coreCount = new SimpleIntegerProperty();
    private transient SimpleDoubleProperty coreClock = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty boostClock = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty powerConsumption = new SimpleDoubleProperty();

    public CPU(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setSocket(csv[3]);
        setCoreCount(Integer.parseInt(csv[4]));
        setClockSpeed(csv[5]);
        setPowerConsumption(Double.parseDouble(csv[6]));
    }

    public CPU(String manufacturer,
               String model,
               String socket,
               int coreCount,
               String clockSpeed,
               double powerConsumption,
               double price) {
        super(manufacturer, model, price);

        setSocket(socket);
        setCoreCount(coreCount);
        setClockSpeed(clockSpeed);
        setPowerConsumption(powerConsumption);
    }

    public CPU(String manufacturer,
               String model,
               String socket,
               int coreCount,
               double coreClock,
               double boostClock,
               double powerConsumption,
               double price) {
        super(manufacturer, model, price);

        setSocket(socket);
        setCoreCount(coreCount);
        setCoreClock(coreClock);
        setBoostClock(boostClock);
        setPowerConsumption(powerConsumption);
    }

    @Override
    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public String getSocket() {
        return socket.getValue();
    }

    public void setSocket(String socket) {
        if(!socket.matches("[a-z]?[A-Z]{1,3}(\\s)?([1-9][+]|[1-9][0-9]{0,3}([-][1-9])?)")){
            throw new IllegalArgumentException("Format of socket type is invalid");
        }

        this.socket.set(socket);
    }

    public int getCoreCount() {
        return coreCount.getValue();
    }

    public void setCoreCount(int coreCount) {
        boolean invalid = true;

        for(int i = 1 ; i < 4 ; i++){
            if(coreCount == i){
                invalid = false;
                break;
            }
        }

        for(int i = 4 ; i < 25 ; i += 2){
            if(coreCount == i){
                invalid = false;
                break;
            }
        }

        if(invalid && coreCount !=32 && coreCount !=64){
            throw new IllegalArgumentException("Number of cores must be either one of these:\n" +
                    "- A number between 1 and 3\n" +
                    "- An even number between 4 and 24\n" +
                    "- 32 or 64");
        }
        this.coreCount.set(coreCount);
    }

    public double getCoreClock() {
        return coreClock.getValue();
    }

    public void setCoreClock(double coreClock) {
        if(coreClock < 1.1 || coreClock > 5.0){
            throw new IllegalArgumentException("Core clock frequency should be between or equal to 1.1 and "
                    + getBoostClock() + " GHz");
        }
        if(coreClock > getBoostClock()){
            setBoostClock(coreClock);
        }

        this.coreClock.set(coreClock);
    }

    public double getBoostClock() {
        return boostClock.getValue();
    }

    public void setBoostClock(double boostClock) {
        if(boostClock < 1.1 || boostClock > 5.0){
            throw new IllegalArgumentException("Overclocked speed should be between or equal to " + getCoreClock() +
                    " and 5.0 GHz");
        }
        if(boostClock < getCoreClock()){
            setCoreClock(boostClock);
        }

        this.boostClock.set(boostClock);
    }

    @Override
    public String getBussType() {
        return null;
    }

    @Override
    public void setBussType(String bussType) {

    }

    @Override
    public int getMemory() {
        return 0;
    }

    @Override
    public void setMemory(int memory) {

    }

    @Override
    public String getMemoryType() {
        return null;
    }

    @Override
    public void setMemoryType(String memoryType) {

    }

    @Override
    public boolean isTactile() {
        return false;
    }

    @Override
    public void setTactile(boolean tactile) {

    }

    @Override
    public int getRam() {
        return 0;
    }

    @Override
    public void setRam(int ram) {

    }

    @Override
    public String getMemoryTech() {
        return null;
    }

    @Override
    public void setMemoryTech(String memoryTech) {

    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(int speed) {

    }

    @Override
    public int getRefreshRate() {
        return 0;
    }

    @Override
    public void setRefreshRate(int refreshRate) {

    }

    @Override
    public int getRamSlots() {
        return 0;
    }

    @Override
    public void setRamSlots(int ramSlots) {

    }

    @Override
    public int getMaxRamSize() {
        return 0;
    }

    @Override
    public void setMaxRamSize(int maxRamSize) {

    }

    @Override
    public int getNumberButtons() {
        return 0;
    }

    @Override
    public void setNumberButtons(int numberButtons) {

    }

    @Override
    public int getDpi() {
        return 0;
    }

    @Override
    public void setDpi(int dpi) {

    }

    @Override
    public boolean isErgonomic() {
        return false;
    }

    @Override
    public void setErgonomic(boolean ergonomic) {

    }

    @Override
    public boolean isWireless() {
        return false;
    }

    @Override
    public void setWireless(boolean wireless) {

    }

    @Override
    public int getPowerCapacity() {
        return 0;
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {

    }

    public double getPowerConsumption() {
        return powerConsumption.getValue();
    }

    public void setPowerConsumption(double powerConsumption) {
        if(powerConsumption < 10 || powerConsumption > 300){
            throw new IllegalArgumentException("Thermal design power must be between 10 and 300");
        }
        this.powerConsumption.set(powerConsumption);
    }

    public String getClockSpeed() {
        return (getCoreClock() != getBoostClock()) ?
                getCoreClock() + "/" + getBoostClock():
                String.valueOf(getCoreClock());
    }

    public void setClockSpeed(double core, double boost){
        if(core > boost){
            throw new IllegalArgumentException("Core clock speed should be greater than the boost clock speed");
        }

        setCoreClock(core);
        setBoostClock(boost);
    }

    public void setClockSpeed(String clockSpeed) {
        if(clockSpeed.matches("/")){
            throw new IllegalArgumentException("Core clock speed and boost clock speed are empty\n" +
            "One of the fields must be filled");
        }

        if(Extract.doubles(clockSpeed).size() == 1){
            setCoreClock(Extract.doubles(clockSpeed).get(0));
            setBoostClock(Extract.doubles(clockSpeed).get(0));
        }
        else {
            setCoreClock(
                    Math.min(Extract.doubles(clockSpeed).get(0),
                            Extract.doubles(clockSpeed).get(Extract.doubles(clockSpeed).size() - 1))
            );
            setBoostClock(
                    Math.max(Extract.doubles(clockSpeed).get(0),
                            Extract.doubles(clockSpeed).get(Extract.doubles(clockSpeed).size() - 1))
            );
        }
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                COMPONENT_TYPE,
                getManufacturer(),
                getModel(),
                getSocket(),
                getCoreCount(),
                getClockSpeed(),
                getPowerConsumption(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                "Socket: %s\n" +
                "Number of cores: %s cores\n" +
                "Clock speed: %s GHz\n" +
                "Power usage: %s W\n" +
                "Price: %s NOK",
                COMPONENT_TYPE, getName(), getSocket(), getCoreCount(), getClockSpeed(), getPowerConsumption(),
                String.format("%.2f",getPrice()));
    }



    //Serialisering:
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeUTF(getSocket());
        objectOutputStream.writeInt(getCoreCount());
        objectOutputStream.writeDouble(getCoreClock());
        objectOutputStream.writeDouble(getBoostClock());
        objectOutputStream.writeDouble(getPowerConsumption());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        String socket = objectInputStream.readUTF();
        int coreCount = objectInputStream.readInt();
        double coreClock = objectInputStream.readDouble();
        double boostClock = objectInputStream.readDouble();
        double powerConsumption = objectInputStream.readDouble();

        this.socket = new SimpleStringProperty();
        this.coreCount = new SimpleIntegerProperty();
        this.coreClock = new SimpleDoubleProperty();
        this.boostClock = new SimpleDoubleProperty();
        this.powerConsumption = new SimpleDoubleProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setSocket(socket);
        setCoreCount(coreCount);
        setCoreClock(coreClock);
        setBoostClock(boostClock);
        setPowerConsumption(powerConsumption);
    }

    @Override
    public int getRpm() {
        return 0;
    }

    @Override
    public void setRpm(int rpm) {

    }

    @Override
    public double getCapacity() {
        return 0;
    }

    @Override
    public void setCapacity(double capacity) {

    }

    @Override
    public String getFormFactor() {
        return null;
    }

    @Override
    public void setFormFactor(String formFactor) {

    }

    @Override
    public int getCoreRpm() {
        return 0;
    }

    @Override
    public void setCoreRpm(int coreRPM) {

    }

    @Override
    public int getMaxRpm() {
        return 0;
    }

    @Override
    public void setMaxRpm(int maxRPM) {

    }

    @Override
    public double getCoreNoise() {
        return 0;
    }

    @Override
    public void setCoreNoise(double coreNoise) {

    }

    @Override
    public double getMaxNoise() {
        return 0;
    }

    @Override
    public void setMaxNoise(double noise) {

    }

    @Override
    public boolean compatible(Compatible motherboard) {
        if(motherboard.getClass() != Motherboard.class){
            throw new IllegalArgumentException("CPU can only connect to a Motherboard");
        }
        return ((Motherboard) motherboard).getSocket().equals(getSocket());
    }
}
