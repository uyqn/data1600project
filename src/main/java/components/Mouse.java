package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Mouse extends Component implements Serializable {
    public transient static final String COMPONENT_TYPE = "Mouse";

    private transient SimpleIntegerProperty numberButtons = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty dpi = new SimpleIntegerProperty();
    private transient SimpleBooleanProperty ergonomic = new SimpleBooleanProperty();
    private transient SimpleBooleanProperty wireless = new SimpleBooleanProperty();

    public Mouse(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setNumberButtons(Integer.parseInt(csv[3]));
        setDpi(Integer.parseInt(csv[4]));
        setErgonomic(Boolean.parseBoolean(csv[5]));
        setWireless(Boolean.parseBoolean(csv[6]));
    }

    public Mouse(String manufacturer, String model, int numberButtons, int dpi, boolean ergonomic, boolean wireless,
                 double price) {
        super(manufacturer, model, price);

        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public int getNumberButtons() {
        return numberButtons.getValue();
    }

    public void setNumberButtons(int numberButtons) {

        if(numberButtons < 0 ){
            throw new IllegalArgumentException("Number of buttons on a mouse cannot be negative");
        }
        else {
            this.numberButtons.set(numberButtons);
        }
    }

    public int getDpi() {
        return dpi.getValue();
    }


    public void setDpi(int dpi) {
        if(dpi < 0 ){
            throw new IllegalArgumentException("DPI cannot be negative");
        }
        else {
            this.dpi.set(dpi);
        }
    }

    public boolean isErgonomic() {
        return ergonomic.getValue();
    }

    public void setErgonomic(boolean ergonomic) {
        this.ergonomic.set(ergonomic);
    }

    public boolean isWireless() {
        return wireless.getValue();
    }

    public void setWireless(boolean wireless) {
        this.wireless.set(wireless);
    }

    @Override
    public int getPowerCapacity() {
        return 0;
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {

    }

    @Override
    public String toString(){
        String ergonomic = isErgonomic() ? "Yes" : "No";
        String wireless = isWireless() ? "Yes" : "No";

        return getComponentType() + ": " + getName() + "\n" +
                "Buttons: " + getNumberButtons() + "\n" +
                "Polling rate: " + getDpi() + "DPI \n" +
                "Ergonomic: " + ergonomic + "\n" +
                "Wireless: " + wireless + "\n" +
                "Price: " + getPrice();
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV( getComponentType(),
                getManufacturer(),
                getModel(),
                getNumberButtons(),
                getDpi(),
                isErgonomic(),
                isWireless(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getNumberButtons());
        objectOutputStream.writeInt(getDpi());
        objectOutputStream.writeBoolean(isErgonomic());
        objectOutputStream.writeBoolean(isWireless());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        int numberButtons = objectInputStream.readInt();
        int dpi = objectInputStream.readInt();
        boolean ergonomic = objectInputStream.readBoolean();
        boolean wireless = objectInputStream.readBoolean();

        this.numberButtons = new SimpleIntegerProperty();
        this.dpi = new SimpleIntegerProperty();
        this.ergonomic = new SimpleBooleanProperty();
        this.wireless = new SimpleBooleanProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
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
}
