package components.Storage;

import components.Component;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Storage extends Component implements Serializable {
    private transient SimpleDoubleProperty capacity=new SimpleDoubleProperty();
    private transient SimpleStringProperty form=new SimpleStringProperty();


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

    public String getForm(){return form.getValue();}

    public void setForm(String form){

        this.form.set(form);

    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getCapacity());
        objectOutputStream.writeDouble(getPrice());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double capacity = objectInputStream.readDouble();
        double price = objectInputStream.readDouble();

        this.capacity = new SimpleDoubleProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        setCapacity(capacity);
        super.setPrice(price);
    }

}






