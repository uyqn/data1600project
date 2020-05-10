package components.Storage;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SSD extends Storage{

    private static final transient String COMPONENT_TYPE ="SSD";

    public SSD(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[3]), Double.parseDouble(csv[4]));
    }

    public SSD(String manufacturer,
               String model,
               double capacity,
               double price){

        super(manufacturer, model, capacity, price);

    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "Capacity: %s GB\n"+
                "Price: %s", COMPONENT_TYPE, getName(), getCapacity(), getPrice());
    }

    @Override
    public String toCSV() {

        return Formatter.toCSV(
                COMPONENT_TYPE,
                getManufacturer(),
                getModel(),
                getCapacity(),
                getPrice()
        );
    }

    //Serialisering:

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
        objectOutputStream.writeDouble(getCapacity());


    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        double capacity = objectInputStream.readDouble();
        int rpm = objectInputStream.readInt();

    }
}
