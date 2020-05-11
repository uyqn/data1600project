package components.Storage;

import fileManager.Formatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SSD extends Storage implements Serializable {

    public static final transient String COMPONENT_TYPE ="SSD";

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
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getCapacity(),
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
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        double capacity = objectInputStream.readDouble();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setCapacity(capacity);
        super.setPrice(price);
    }
}
