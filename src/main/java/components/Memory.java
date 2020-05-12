package components;

import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Memory extends Component implements Serializable, Compatible {

    public transient static final String COMPONENT_TYPE = "Memory";

    private transient SimpleIntegerProperty ram = new SimpleIntegerProperty();
    private transient SimpleStringProperty memoryTech =new SimpleStringProperty();
    private transient SimpleIntegerProperty speed = new SimpleIntegerProperty();

    private transient int techNumber; //DDR#

    public Memory(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[3]));

        setRam(Integer.parseInt(csv[4]));
        setMemoryTech(csv[5]);
        setSpeed(Integer.parseInt(csv[6]));
    }


    public Memory(String manufacturer, String model, double price, int ram, String memoryTech, int speed){

        super(manufacturer, model, price);

        setRam(ram);
        setMemoryTech(memoryTech);
        setSpeed(speed);
    }

    @Override
    public String getComponentType(){return COMPONENT_TYPE;}

    public int getRam(){ return ram.getValue();}

    public void setRam(int ram){

        boolean invalid=true;

        for (int i=2; i<=256; i=i*2){
            if(ram ==i){
                invalid=false;
                break;
            }
        }

        if(invalid){
            throw new IllegalArgumentException("RAM must be one of these: \n 2GB, 4GB, 8GB, 16GB, 32GB, 64GB, 128GB");
        }
        this.ram.set(ram);
    }

    public String getMemoryTech(){ return memoryTech.getValue();}

    public void setMemoryTech(String memoryTech){
        if(memoryTech.matches("[/\\s-]")){
            throw new IllegalArgumentException("Speed technology must be valid. \n(for example: DDR\n" +
                    "DDR2\n" +
                    "DDR3\n" +
                    "DDR4)"
                    );
        }

        if(Extract.ints(memoryTech).size() == 0){
            setTechNumber(1);
        }
        else if(Extract.ints(memoryTech).get(0) == 1){
            setTechNumber(1);
        }
        else {
            setTechNumber(Extract.ints(memoryTech).get(0));
        }

        this.memoryTech.set("DDR"+ getTechNumber());
    }

    public int getTechNumber() {
        return techNumber;
    }

    private void setTechNumber(Integer integer) {
        if(integer < 1 || integer > 4){
            throw new IllegalArgumentException("Memory technology number must either be empty or between 1 and 4");
        }
        this.techNumber = integer;
    }

    public int getSpeed(){ return speed.getValue();}

    public void setSpeed(int speed){
        if(speed<1000||speed>5000){
            throw new IllegalArgumentException("Speed must be between 1000 MHz and 5000MHz");
        }
        this.speed.set(speed);
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
        return Formatter.toCSV(getComponentType(),getManufacturer(),getModel(),getPrice(), getRam(), getMemoryTech(), getSpeed());
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "RAM: %s GB\n"+
                "Speed: %sMHz %s\n"+
                "Price: %s", getComponentType(), getName(), getRam(),getSpeed(), getMemoryTech(), getPrice());
    }

    //Serialisering:

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getRam());
        objectOutputStream.writeUTF(getMemoryTech());
        objectOutputStream.writeInt(getSpeed());

    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        String manufacturer= objectInputStream.readUTF();
        String model=objectInputStream.readUTF();
        double price=objectInputStream.readDouble();
        int RAM=objectInputStream.readInt();
        String speedTech= objectInputStream.readUTF();
        int speed=objectInputStream.readInt();

        this.ram = new SimpleIntegerProperty();
        this.memoryTech = new SimpleStringProperty();
        this.speed=new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setRam(RAM);
        setMemoryTech(speedTech);
        setSpeed(speed);
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
    public boolean compatible(Compatible motherboard) {
        if(motherboard.getClass() != Motherboard.class) {
            throw new IllegalArgumentException("Memories can only connect to motherboards");
        }

        return ((Motherboard) motherboard).getTechNumber() == getTechNumber();
    }
}