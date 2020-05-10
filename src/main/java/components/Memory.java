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

    private static final String COMPONENT_TYPE = "Memory";

    private transient SimpleIntegerProperty RAM=new SimpleIntegerProperty();
    private transient SimpleStringProperty memoryTech =new SimpleStringProperty();
    private transient SimpleIntegerProperty speed=new SimpleIntegerProperty();

    private transient int techNumber; //DDR#

    public Memory(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[3]));

        setRAM(Integer.parseInt(csv[4]));
        setMemoryTech(csv[5]);
        setSpeed(Integer.parseInt(csv[6]));
    }


    public Memory(String manufacturer, String model, double price, int RAM, String memoryTech, int speed){

        super(manufacturer, model, price);

        setRAM(RAM);
        setMemoryTech(memoryTech);
        setSpeed(speed);
    }

    @Override
    public String getComponentType(){return COMPONENT_TYPE;}

    public int getRAM(){ return RAM.getValue();}

    public void setRAM(int RAM){

        boolean invalid=true;

        for (int i=2; i<=256; i=i*2){
            if(RAM==i){
                invalid=false;
                break;
            }
        }

        if(invalid){
            throw new IllegalArgumentException("RAM must be one of these: \n 2GB, 4GB, 8GB, 16GB, 32GB, 64GB, 128GB");
        }
        this.RAM.set(RAM);
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
    public String toCSV(){
        return Formatter.toCSV(getComponentType(),getManufacturer(),getModel(),getPrice(),getRAM(), getMemoryTech(), getSpeed());
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "RAM: %s GB\n"+
                "Speed: %sMHz %s\n"+
                "Price: %s", getComponentType(), getName(), getRAM(),getSpeed(), getMemoryTech(), getPrice());
    }

    //Serialisering:

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getRAM());
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

        this.RAM = new SimpleIntegerProperty();
        this.memoryTech = new SimpleStringProperty();
        this.speed=new SimpleIntegerProperty();

        setRAM(RAM);
        setMemoryTech(speedTech);
        setSpeed(speed);
    }

    @Override
    public boolean compatible(Compatible motherboard) {
        if(motherboard.getClass() != Motherboard.class) {
            throw new IllegalArgumentException("Memories can only connect to motherboards");
        }

        return ((Motherboard) motherboard).getTechNumber() == getTechNumber();
    }
}