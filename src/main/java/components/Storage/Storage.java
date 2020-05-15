package components.Storage;

import components.Component;
import components.Interchangeable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Storage extends Component implements Serializable, Interchangeable {
    private transient SimpleIntegerProperty capacity=new SimpleIntegerProperty();
    private transient SimpleStringProperty form=new SimpleStringProperty();


    public Storage(String manufacturer, String model, int capacity, double price){

        super(manufacturer, model, price);
        setCapacity(capacity);
    }

    @Override
    public int getCapacity(){return capacity.getValue();}

    @Override
    public void setCapacity(int capacity){
        if (capacity<0){
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity.set(capacity);


    }

    public String getCapacityString(){
        return (getCapacity() >= 1000) ?
                String.format("%.1f", (double) getCapacity()/1000) + " TB" :
                getCapacity() + " GB";
    }

    public String getForm(){return form.getValue();}

    public void setForm(String form){

        this.form.set(form);

    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeInt(getCapacity());
        objectOutputStream.writeDouble(getPrice());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        int capacity = objectInputStream.readInt();
        double price = objectInputStream.readDouble();

        this.capacity = new SimpleIntegerProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        setCapacity(capacity);
        super.setPrice(price);
    }
}






