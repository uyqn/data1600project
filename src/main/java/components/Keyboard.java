package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Keyboard extends Component implements Serializable {

    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Keyboard");
    private transient SimpleBooleanProperty Tactile;

    public Keyboard(String [] csv) {
        super(csv[1], csv[2], Double.parseDouble(csv[4]));

        setTactile(Boolean.parseBoolean(csv[3]));
    }

    public boolean isTactile() {
        return Tactile.get();
    }

    public void setTactile(boolean tactile) {
        this.Tactile.set(tactile);
    }

    public static String getComponentType(){
        return COMPONENT_TYPE.get();
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

        Boolean Tactile = objectInputStream.readBoolean();

        this.Tactile = new SimpleBooleanProperty();

        setTactile(Tactile);
    }

}
