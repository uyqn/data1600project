package components;

import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Cooler extends Component implements Serializable {
    public static final transient String COMPONENT_TYPE = "Cooler";

    private transient SimpleIntegerProperty coreRpm = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty maxRpm = new SimpleIntegerProperty();
    private transient SimpleDoubleProperty coreNoise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty maxNoise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty powerConsumption = new SimpleDoubleProperty();

    public Cooler(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setDimension(csv[3]);
        setRPM(csv[4]);
        setNoise(csv[5]);
        setPowerConsumption(Double.parseDouble(csv[6]));
    }

    public Cooler(String manufacturer,
                   String model,
                   String dimension,
                   String rpm,
                   String noise,
                   double powerConsumption,
                   double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);
        setRPM(rpm);
        setNoise(noise);
        setPowerConsumption(powerConsumption);
    }

    public Cooler(String manufacturer,
                  String model,
                  double width,
                  double depth,
                  double height,
                  int coreRpm,
                  int maxRpm,
                  double coreNoise,
                  double maxNoise,
                  double powerConsumption,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);
        setCoreRpm(coreRpm);
        setMaxRpm(maxRpm);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
        setPowerConsumption(powerConsumption);
    }

    @Override
    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    @Override
    public void setDimension(String dimension){
        if(Extract.doubles(dimension).size() < 3){
            throw new IllegalArgumentException("All three fields of dimension must be specified");
        }
        super.setWidth(Extract.doubles(dimension).get(0));
        super.setDepth(Extract.doubles(dimension).get(1));
        super.setHeight(Extract.doubles(dimension).get(2));
    }

    public double getCoreNoise() {
        return coreNoise.getValue();
    }

    public void setCoreNoise(double coreNoise) {
        if(coreNoise < 0){
            throw new IllegalArgumentException("Noise cannot be negative");
        }
        if(coreNoise > getMaxNoise()){
            setMaxNoise(coreNoise);
        }
        this.coreNoise.set(coreNoise);
    }

    public double getMaxNoise() {
        return maxNoise.getValue();
    }

    public void setMaxNoise(double maxNoise) {
        if(maxNoise < 0){
            throw new IllegalArgumentException("Max noise cannot be less than base noise");
        }
        if(maxNoise < getCoreNoise()){
            setCoreNoise(maxNoise);
        }
        this.maxNoise.set(maxNoise);
    }

    public String getNoise() {
        return (getCoreNoise() != getMaxNoise()) ?
                getCoreNoise() + " - " + getMaxNoise() :
                String.valueOf(getCoreNoise());
    }

    public void setNoise(String noise) {
        if(noise.matches(" - ")){
            throw new IllegalArgumentException("Base noise and max noise are empty\n" +
                    "One of the fields must be filled");
        }

        if(Extract.doubles(noise).size() == 1){
            setCoreNoise(Extract.doubles(noise).get(0));
            setMaxNoise(Extract.doubles(noise).get(0));
        }
        else {
            setCoreNoise(
                    Math.min(Extract.doubles(noise).get(0),
                            Extract.doubles(noise).get(1))
            );
            setMaxNoise(
                    Math.max(Extract.doubles(noise).get(0),
                            Extract.doubles(noise).get(1))
            );
        }
    }

    public int getCoreRpm() {
        return coreRpm.getValue();
    }

    public void setCoreRpm(int coreRpm) {
        if(coreRpm < 0){
            throw new IllegalArgumentException("RPM cannot be negative");
        }
        if(coreRpm > getMaxRpm()){
            setMaxRpm(coreRpm);
        }
        this.coreRpm.set(coreRpm);
    }

    public int getMaxRpm() {
        return maxRpm.getValue();
    }

    public void setMaxRpm(int maxRpm) {
        if(maxRpm < 0){
            throw new IllegalArgumentException("Max RPM cannot be negative");
        }
        if(maxRpm < getCoreRpm()){
            setCoreRpm(maxRpm);
        }

        this.maxRpm.set(maxRpm);
    }

    public String getRpmString(){
        return (getCoreRpm() != getMaxRpm()) ?
                getCoreRpm() + " - " + getMaxRpm() :
                String.valueOf(getCoreRpm());
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

    public void setRPM(String rpm){
        if(rpm.matches(" - ")){
            throw new IllegalArgumentException("Base rpm and max rpm are empty\n" +
                    "One of the fields must be filled");
        }

        if(Extract.ints(rpm).size() == 1){
            setCoreRpm(Extract.ints(rpm).get(0));
            setMaxRpm(Extract.ints(rpm).get(0));
        }
        else {
            setCoreRpm(Math.min(Extract.ints(rpm).get(0),
                    Extract.ints(rpm).get(1))
            );
            setMaxRpm(Math.max(Extract.ints(rpm).get(0),
                    Extract.ints(rpm).get(1))
            );
        }

    }

    public double getPowerConsumption(){
        return powerConsumption.getValue();
    }

    public void setPowerConsumption(double powerConsumption){
        if(powerConsumption < 0){
            throw new IllegalArgumentException("Power consumption cannot be negative");
        }
        this.powerConsumption.set(powerConsumption);
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

    @Override
    public int getPowerCapacity() {
        return 0;
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {

    }

    @Override
    public String toCSV(){
        return Formatter.toCSV(
                COMPONENT_TYPE,
                getManufacturer(),
                getModel(),
                getDimension(),
                getRpmString(),
                getNoise(),
                getPowerConsumption(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                        "Dimension: %s cm\n" +
                        "RPM: %s RPM\n" +
                        "Noise: %s dBA\n" +
                        "Power consumption: %s W\n" +
                        "Price: %s NOK",
                COMPONENT_TYPE, getName(), getDimension(), getRpmString(), getNoise(), getPowerConsumption(),
                String.format("%.2f",getPrice())
                );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
        objectOutputStream.writeUTF(getDimension());

        objectOutputStream.writeInt(getCoreRpm());
        objectOutputStream.writeInt(getMaxRpm());
        objectOutputStream.writeDouble(getCoreNoise());
        objectOutputStream.writeDouble(getMaxNoise());
        objectOutputStream.writeDouble(getPowerConsumption());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        String dimension = objectInputStream.readUTF();

        int coreRPM = objectInputStream.readInt();
        int maxRPM = objectInputStream.readInt();
        double coreNoise = objectInputStream.readDouble();
        double maxNoise = objectInputStream.readDouble();
        double powerConsumption = objectInputStream.readDouble();

        this.coreRpm = new SimpleIntegerProperty();
        this.maxRpm = new SimpleIntegerProperty();
        this.coreNoise = new SimpleDoubleProperty();
        this.maxNoise = new SimpleDoubleProperty();
        this.powerConsumption = new SimpleDoubleProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        super.setDimension(dimension);
        setCoreRpm(coreRPM);
        setMaxRpm(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
        setPowerConsumption(powerConsumption);
    }

    @Override
    public int getRpm() {
        return 0;
    }
}
