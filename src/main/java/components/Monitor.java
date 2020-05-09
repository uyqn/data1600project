package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Monitor extends Component implements Serializable {
    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Monitor");

    private transient SimpleIntegerProperty refreshRate = new SimpleIntegerProperty();

    public Monitor(String[] csv) {
        super(csv[0], csv[1], Double.parseDouble(csv[3]));

        setRefreshRate(Integer.parseInt(csv[2]));
    }

    public Monitor(String manufacturer,
                   String model,
                   int refreshRate,
                   double price){

        super(manufacturer, model, price);

        setPrice(price);

    }

    public String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public int getRefreshRate(){
        return refreshRate.getValue();
    }

    private void setRefreshRate(int RefreshRate){

        if (RefreshRate<0) {
            throw new IllegalArgumentException("Refresh rate cannot be negative");
        }
        else{
            this.refreshRate.set(RefreshRate);
        }
    }

    @Override
    public String toString(){
        return getComponentType() + ": " + getName() + "\n" +
                "Refresh rate " +getRefreshRate()+" Hz";
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getManufacturer(),
                getModel(),
                getRefreshRate(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getRefreshRate());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        int refreshRate = objectInputStream.readInt();

        this.refreshRate = new SimpleIntegerProperty();

        setRefreshRate(refreshRate);
    }
}
