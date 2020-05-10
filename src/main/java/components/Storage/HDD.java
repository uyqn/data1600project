package components.Storage;
import components.Component;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HDD extends Storage{

    private static final transient String COMPONENT_TYPE ="Harddrive";

    private transient SimpleIntegerProperty rpm = new SimpleIntegerProperty();

    public HDD(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[3]), Double.parseDouble(csv[5]));
        setRPM(Integer.parseInt(csv[4]));
    }

    public HDD(String manufacturer,
               String model,
               double capacity,
               int rpm,
               double price){

        super(manufacturer, model, capacity, price);

        setRPM(rpm);

    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public int getRPM(){return rpm.getValue();}

    public void setRPM(int RPM){

        if(RPM<0){
            throw new IllegalArgumentException("RPM must be higher than 0!");

        }
        this.rpm.set(RPM);
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "Capacity: %s TB\n"+
                "RPM: %s\n"+
                "Price: %s Nok", COMPONENT_TYPE, getName(), getCapacity(), getRPM(), getPrice());
    }


    @Override
    public String toCSV() {
        return Formatter.toCSV(
                COMPONENT_TYPE,
                getManufacturer(),
                getModel(),
                getCapacity(),
                getRPM(),
                getPrice()
        );
    }

    //Serialisering:

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException{
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeDouble(getCapacity());
        objectOutputStream.writeInt(getRPM());


    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        double capacity = objectInputStream.readDouble();
        int rpm = objectInputStream.readInt();


        this.rpm=new SimpleIntegerProperty();

        setRPM(rpm);
    }

}