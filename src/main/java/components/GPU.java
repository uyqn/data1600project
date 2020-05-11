package components;


//Graphics Card
import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GPU extends Component implements Serializable, Compatible {
    public static final transient String COMPONENT_TYPE = "GPU";

    private transient SimpleStringProperty bussType = new SimpleStringProperty();
    private transient SimpleIntegerProperty memory = new SimpleIntegerProperty();
    private transient SimpleStringProperty memoryType=new SimpleStringProperty();
    private transient SimpleDoubleProperty baseClock = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty boostClock = new SimpleDoubleProperty();

    private transient double bussVersion;
    private transient int bussSlots;

    public GPU(String[] csv //String manufacturer, String model, String bussType,
               // int memory,  String memoryType, String clockSpeed, double price)
    ){
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setBussType(csv[3]);
        setMemory(Integer.parseInt(csv[4]));
        setMemoryType(csv[5]);
        setClockSpeed(csv[6]);

    }


    public GPU(String manufacturer, String model, String bussType, int memory, String memoryType,
               double baseClock, double boostClock, double price){
        super(manufacturer,model,price);

        setBussType(bussType);
        setMemory(memory);
        setMemoryType(memoryType);
        setBaseClock(baseClock);
        setBoostClock(boostClock);
    }

    public GPU(String manufacturer, String model, String bussType, int memory, String memoryType, String clockSpeed, double price){


        super(manufacturer,model,price);

        setBussType(bussType);
        setMemory(memory);
        setMemoryType(memoryType);
        setClockSpeed(clockSpeed);
    }

    public String getCOMPONENT_TYPE(){return COMPONENT_TYPE;}


    public double getBaseClock() {
        return baseClock.getValue();
    }

    public void setBaseClock(double baseClock) {
        if(baseClock < 1.1 || baseClock > 5.0){
            throw new IllegalArgumentException("Base clock frequency should be between or equal to 1.1 and "
                    + getBoostClock() + " GHz");
        }
        if(baseClock > getBoostClock()){
            setBoostClock(baseClock);
        }

        this.baseClock.set(baseClock);
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

    public double getBoostClock() {
        return boostClock.getValue();
    }

    public void setBoostClock(double boostClock) {
        if(boostClock < 1.1 || boostClock > 5.0){
            throw new IllegalArgumentException("Overclocked speed should be between or equal to " + getBaseClock() +
                    " and 5.0 GHz");
        }
        if(boostClock < getBaseClock()){
            setBaseClock(boostClock);
        }

        this.boostClock.set(boostClock);
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

    public String getClockSpeed() {
        return (getBaseClock() != getBoostClock()) ?
                getBaseClock() + "/" + getBoostClock():
                String.valueOf(getBaseClock());
    }

    public void setClockSpeed(double core, double boost){
        if(core > boost){
            throw new IllegalArgumentException("Core clock speed should be greater than the boost clock speed");
        }

        setBaseClock(core);
        setBoostClock(boost);
    }

    public void setClockSpeed(String clockSpeed) {
        if(clockSpeed.matches("/")){
            throw new IllegalArgumentException("Core clock speed and boost clock speed are empty\n" +
                    "One of the fields must be filled");
        }

        if(Extract.doubles(clockSpeed).size() == 1){
            setBaseClock(Extract.doubles(clockSpeed).get(0));
            setBoostClock(Extract.doubles(clockSpeed).get(0));
        }
        else {
            setBaseClock(
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
                getClockSpeed(),
                getPrice()
        );
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n" +
                "Busstype: %s \n" +
                "Memory: %s GB %s\n" +
                "Clock Speed: %s GHz\n" +
                "Price: %s NOK\n",
                getCOMPONENT_TYPE(), getName(), getBussType(), getMemory(), getMemoryType(), getClockSpeed(),
                getPrice());
    }

    //Serialisering
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException{
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer()); //String
        objectOutputStream.writeUTF(getModel()); //int
        objectOutputStream.writeDouble(getPrice()); //Double

        objectOutputStream.writeInt(getMemory());
        objectOutputStream.writeUTF(getMemoryType());
        objectOutputStream.writeDouble(getBaseClock());
        objectOutputStream.writeDouble(getBoostClock());
    }

    private void readObject(ObjectInputStream objecInputStream) throws IOException, ClassNotFoundException{
        String manufacturer = objecInputStream.readUTF();
        String model = objecInputStream.readUTF();
        double price = objecInputStream.readDouble();

        int memory= objecInputStream.readInt();
        String memoryType= objecInputStream.readUTF();
        double baseClock= objecInputStream.readDouble();
        double boostClock = objecInputStream.readDouble();

        this.memory=new SimpleIntegerProperty();
        this.memoryType=new SimpleStringProperty();
        this.baseClock=new SimpleDoubleProperty();
        this.boostClock= new SimpleDoubleProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setMemory(memory);
        setMemoryType(memoryType);
        setBaseClock(baseClock);
        setBoostClock(boostClock);

    }

    @Override
    public boolean compatible(Compatible motherboard) {
        if(motherboard.getClass() != Motherboard.class){
            throw new IllegalArgumentException("This component can only connect to a Motherboard");
        }

        return ((Motherboard) motherboard).getBussType().equals(getBussType());
    }
}


