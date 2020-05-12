package components;


//Graphics Card
import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GPU extends Component implements Serializable, Compatible {
    public static final transient String COMPONENT_TYPE = "GPU";

    private transient SimpleStringProperty bussType = new SimpleStringProperty();
    private transient SimpleIntegerProperty memory = new SimpleIntegerProperty();
    private transient SimpleStringProperty memoryType=new SimpleStringProperty();
    private transient SimpleIntegerProperty boostSpeed= new SimpleIntegerProperty();

    private transient double bussVersion;
    private transient int bussSlots;

    public GPU(String[] csv //String manufacturer, String model, String bussType,
               // int memory,  String memoryType, String clockSpeed, double price)
    ){
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setBussType(csv[3]);
        setMemory(Integer.parseInt(csv[4]));
        setMemoryType(csv[5]);
        setBoostSpeed(Integer.parseInt(csv[6]));
    }


    public GPU(String manufacturer, String model, String bussType, int memory, String memoryType,
               int boostSpeed, double price){
        super(manufacturer,model,price);

        setBussType(bussType);
        setMemory(memory);
        setMemoryType(memoryType);
        setBoostSpeed(boostSpeed);
    }

    public String getCOMPONENT_TYPE(){return COMPONENT_TYPE;}


    public double getCoreClock() {
        return 0;
    }

    public void setCoreClock(double coreClock) {
    }

    @Override
    public double getBoostClock() {
        return 0;
    }

    @Override
    public void setBoostClock(double boostClock) {

    }

    public String getBussType(){
        return bussType.getValue();
    }

    public void setBussType(String bussType) {
        if(!bussType.matches(
                "[Pp][Cc][Ii](\\s|-)?([Ee]|" +
                        "[Ee][Xx][Pp][Rr][Ee][Ss][Ss])" +
                        "(\\s)?\\d(\\.\\d)(\\s)?[Xx ](\\s?)\\d{1,2}")){
            throw new IllegalArgumentException("Busstype not recognized");
        }

        setBussVersion(Extract.doubles(bussType).get(0));
        setBussSlots(Extract.ints(bussType).get(1));

        this.bussType.set("PCIe " + getBussVersion() + "x" + getBussSlots());
    }

    public int getBussSlots() {
        return bussSlots;
    }

    public double getBussVersion() {
        return bussVersion;
    }

    private void setBussSlots(Integer integer) {
        boolean valid = false;

        for(int i = 1 ; i < 64 ; i *= 2){
            if (integer == i) {
                valid = true;
                break;
            }
        }

        if(!valid){
            throw new IllegalArgumentException("Buss slots must be between 1 and 32 and a multiple of 2");
        }

        this.bussSlots = integer;
    }

    private void setBussVersion(Double aDouble) {
        if(aDouble < 1.0 || aDouble > 6.0){
            throw new IllegalArgumentException("Buss version must be between 1.0 and 6.0");
        }
        this.bussVersion = aDouble;
    }

    public int getBoostSpeed() {
        return boostSpeed.getValue();
    }

    public void setBoostSpeed(int boostSpeed) {
        if(boostSpeed < 1){
            throw new IllegalArgumentException("Overclocked speed should be positive");
        }

        this.boostSpeed.set(boostSpeed);
    }

    public int getMemory() {
        return memory.getValue();
    }

    public void setMemory(int memory) {
        if(memory<0 || memory>64){
            throw new IllegalArgumentException("Memory must be a whole number beteen 1 and 64");
        }
        this.memory.set(memory);
    }

    public String getMemoryType(){ return memoryType.getValue();}

    public void setMemoryType(String memoryType){
        if(memoryType.isEmpty() || memoryType.isBlank()){
            throw new IllegalArgumentException("Memory type cannot be empty");
        }
        this.memoryType.set(memoryType);
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
    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    @Override
    public String toCSV(){
        return Formatter.toCSV(
                getCOMPONENT_TYPE(),
                getManufacturer(),
                getModel(),
                getBussType(),
                getMemory(),
                getMemoryType(),
                getBoostSpeed(),
                getPrice()
        );
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n" +
                "Buss type: %s \n" +
                "Memory: %s GB\n" +
                "Memory type: %s\n" +
                "Boost clock: %s MHz\n" +
                "Price: %s NOK\n",
                getCOMPONENT_TYPE(), getName(), getBussType(), getMemory(), getMemoryType(), getBoostSpeed(),
                String.format("%.2f",getPrice()));
    }

    //Serialisering
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException{
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getMemory());
        objectOutputStream.writeUTF(getMemoryType());
        objectOutputStream.writeInt(getBoostSpeed());
    }

    private void readObject(ObjectInputStream objecInputStream) throws IOException, ClassNotFoundException{
        String manufacturer = objecInputStream.readUTF();
        String model = objecInputStream.readUTF();
        double price = objecInputStream.readDouble();

        int memory= objecInputStream.readInt();
        String memoryType= objecInputStream.readUTF();
        double boostSpeed = objecInputStream.readInt();

        this.memory=new SimpleIntegerProperty();
        this.memoryType=new SimpleStringProperty();
        this.boostSpeed= new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);

        setMemory(memory);
        setMemoryType(memoryType);
        setBoostClock(boostSpeed);

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
    public boolean compatible(Compatible motherboard) {
        if(motherboard.getClass() != Motherboard.class){
            throw new IllegalArgumentException("This component can only connect to a Motherboard");
        }

        return ((Motherboard) motherboard).getBussType().equals(getBussType());
    }
}


