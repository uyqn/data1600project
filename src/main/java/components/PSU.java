package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PSU extends Component implements Serializable {

    public transient static final String COMPONENT_TYPE = "PSU";
    private transient SimpleIntegerProperty powerCapacity = new SimpleIntegerProperty();

    public PSU(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[4]));

        setPowerCapacity(Integer.parseInt(csv[3]));
    }


    public PSU(String manufacturer, String model, int PowerCapacity, double price){
        super(manufacturer, model, price);

        setPowerCapacity(PowerCapacity);
    }

    public String getComponentType(){
        return COMPONENT_TYPE;
    }

    public int getPowerCapacity(){
        return powerCapacity.get();
    }

    public void setPowerCapacity(int powerCapacity){
        if(powerCapacity < 0 ){
            throw new IllegalArgumentException("Power capacity cannot be negative");
        }
        else {
            this.powerCapacity.set(powerCapacity);
        }
    }

    @Override
    public String getNoise() {
        return null;
    }

    @Override
    public void setNoise(String noise) {

    }

    @Override
    public String getRpmString() {
        return null;
    }

    @Override
    public void setRpmString(String newValue) {

    }

    @Override
    public String toString(){
        return getComponentType() + ": " + getName() + "\n" +
                "Power Capacity: " + getPowerCapacity() + " W\n" +
                "Price: " + String.format("%.2f",getPrice());
    }

    @Override
    public String getSpec() {
        return "Power Capacity: " + getPowerCapacity() + " W\n" +
                "Price: " + String.format("%.2f",getPrice());
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getPowerCapacity(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getPowerCapacity());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        int PowerCapacity = objectInputStream.readInt();

        this.powerCapacity = new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setPowerCapacity(PowerCapacity);
    }

    @Override
    public int getRpm() {
        return 0;
    }

    @Override
    public void setRpm(int rpm) {

    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public void setCapacity(int capacity) {

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
    public double getPowerConsumption() {
        return 0;
    }

    @Override
    public void setPowerConsumption(double powerConsumption) {

    }

    @Override
    public String getSocket() {
        return null;
    }

    @Override
    public void setSocket(String socket) {

    }

    @Override
    public int getCoreCount() {
        return 0;
    }

    @Override
    public void setCoreCount(int coreCount) {

    }

    @Override
    public double getCoreClock() {
        return 0;
    }

    @Override
    public void setCoreClock(double coreClock) {

    }

    @Override
    public double getBoostClock() {
        return 0;
    }

    @Override
    public void setBoostClock(double boostClock) {

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
    public int getBoostSpeed() {
        return 0;
    }

    @Override
    public void setBoostSpeed(int boostSpeed) {

    }

    @Override
    public boolean getTactile() {
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
    public double getSize() {
        return 0;
    }

    @Override
    public void setSize(double size) {

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
}
