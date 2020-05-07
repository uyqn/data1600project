package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PowerSupply extends Component implements Serializable {

    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Power Supply");
    private transient SimpleIntegerProperty PowerCapacity;

    public String getComponentType(){
        return COMPONENT_TYPE.get();
    }

    public PowerSupply(String manufacturer, String model, double price, int PowerCapacity){
        super(manufacturer, model, price);

        setPowerCapacity(PowerCapacity);
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

        setPowerCapacity(PowerCapacity);
    }
}
