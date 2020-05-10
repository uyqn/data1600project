package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Keyboard extends Component implements Serializable {

    private transient static final String COMPONENT_TYPE = "Keyboard";
    private transient SimpleBooleanProperty tactile = new SimpleBooleanProperty();

    public Keyboard(String [] csv) {
        super(csv[1], csv[2], Double.parseDouble(csv[4]));

        setTactile(Boolean.parseBoolean(csv[3]));
    }

    public Keyboard(String manufacturer,
                    String model,
                    boolean tactile,
                    double price){
        super(manufacturer, model, price);

        setTactile(tactile);

    }

    public String getComponentType(){
        return COMPONENT_TYPE;
    }

    public boolean isTactile() {
        return tactile.getValue();
    }

    public void setTactile(boolean tactile) {
        this.tactile.set(tactile);
    }


    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "Tactile: %s\n" +
                "Price: %s", getComponentType(),getName(),
                isTactile() ? "Yes" : "No", getPrice());
    }
    @Override
    public String toCSV() {
        return Formatter.toCSV(getComponentType(),
                getManufacturer(),
                getModel(),
                isTactile(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeBoolean(isTactile());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        boolean Tactile = objectInputStream.readBoolean();

        this.tactile = new SimpleBooleanProperty();

        setTactile(Tactile);
    }

}
