package components.Storage;
import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class HDD extends Storage implements Serializable {

    public static final transient String COMPONENT_TYPE ="HDD";

    private transient SimpleIntegerProperty RPM = new SimpleIntegerProperty();

    public HDD(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[3]), Double.parseDouble(csv[5]));
        setRPM(Integer.parseInt(csv[4]));
    }

    public HDD(String manufacturer,
               String model,
               double capacity,
               int RPM,
               double price){

        super(manufacturer, model, capacity, price);

        setRPM(RPM);

    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public int getRPM(){return RPM.getValue();}

    public void setRPM(int RPM){

        if(RPM<0){
            throw new IllegalArgumentException("RPM must be higher than 0!");

        }
        this.RPM.set(RPM);
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getRPM(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeDouble(getCapacity());
        objectOutputStream.writeInt(getRPM());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        double capacity = objectInputStream.readDouble();
        int rpm = objectInputStream.readInt();

        this.RPM = new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);

        super.setCapacity(capacity);
        setRPM(rpm);
    }
}