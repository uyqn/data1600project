package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PSU extends Component implements Serializable {

    public transient static final String COMPONENT_TYPE = "PSU";
    private transient SimpleIntegerProperty PowerCapacity;

    public PSU(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[3]));

        setPowerCapacity(Integer.parseInt(csv[4]));
    }


    public PSU(String manufacturer, String model, int PowerCapacity, double price){
        super(manufacturer, model, price);

        setPowerCapacity(PowerCapacity);
    }

    public String getComponentType(){
        return COMPONENT_TYPE;
    }

    public int getPowerCapacity(){
        return PowerCapacity.get();
    }

    private void setPowerCapacity(int PowerCapacity){
        if(PowerCapacity < 0 ){
            throw new IllegalArgumentException("Power capacity cannot be negative");
        }
        else {
            this.PowerCapacity.set(PowerCapacity);
        }
    }

    public String ToString(){
        return "PowerSupply: " + getName() + "Power Capacity: " +PowerCapacity+" W";
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getPowerCapacity(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getPowerCapacity());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        int PowerCapacity = objectInputStream.readInt();

        this.PowerCapacity = new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);
        setPowerCapacity(PowerCapacity);
    }
}
