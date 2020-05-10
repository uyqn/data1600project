package components.Storage;

import components.Component;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Storage extends Component {


    private transient SimpleDoubleProperty capacity=new SimpleDoubleProperty();


    public Storage(String manufacturer, String model, double capacity, double price){

        super(manufacturer, model, price);
        setCapacity(capacity);
    }

    public double getCapacity(){return capacity.getValue();}

    public void setCapacity(double capacity){
        if (capacity<0){
            throw new IllegalArgumentException("Capaity must be positive");
        }
        this.capacity.set(capacity);


    }


    //Serialisering
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

        this.capacity=new SimpleDoubleProperty();

        setCapacity(capacity);


    }


}






