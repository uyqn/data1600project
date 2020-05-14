package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Monitor extends Component implements Serializable {
    public transient static final String COMPONENT_TYPE = "Monitor";

    private transient SimpleIntegerProperty refreshRate = new SimpleIntegerProperty();
    private transient SimpleDoubleProperty size = new SimpleDoubleProperty();

    public Monitor(String[] csv) {
        super(csv[0], csv[1], Double.parseDouble(csv[4]));

        setSize(Double.parseDouble(csv[2]));
        setRefreshRate(Integer.parseInt(csv[3]));
    }

    public Monitor(String manufacturer,
                   String model,
                   double size,
                   int refreshRate,
                   double price){

        super(manufacturer, model, price);

        setSize(size);
        setRefreshRate(refreshRate);
        setPrice(price);
    }

    @Override
    public void setSize(double size) {
        if(size < 15 || size > 65){
            throw new IllegalArgumentException("Screen size must be between 15.0\" and 65.0\"");
        }
        this.size.set(size);
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public int getRefreshRate(){
        return refreshRate.getValue();
    }

    public void setRefreshRate(int RefreshRate){

        if (RefreshRate<0) {
            throw new IllegalArgumentException("Refresh rate cannot be negative");
        }
        else{
            this.refreshRate.set(RefreshRate);
        }
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
                "Display size: " + getSize() + "\" \n" +
                "Refresh rate " + getRefreshRate() + " Hz\n" +
                "Price: " + String.format("%.2f",getPrice());
    }

    @Override
    public String getSpec() {
        return  "Display size: " + getSize() + "\" \n" +
                "Refresh rate " + getRefreshRate() + " Hz\n" +
                "Price: " + String.format("%.2f",getPrice());
    }

    @Override
    public double getSize() {
        return this.size.getValue();
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getSize(),
                getRefreshRate(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
        objectOutputStream.writeDouble(getSize());
        objectOutputStream.writeInt(getRefreshRate());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        double size = objectInputStream.readDouble();
        int refreshRate = objectInputStream.readInt();

        this.refreshRate = new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setSize(size);
        setRefreshRate(refreshRate);
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
}
