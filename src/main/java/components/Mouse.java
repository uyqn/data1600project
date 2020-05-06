package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Mouse extends Component{
    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Mouse");

    private SimpleIntegerProperty numberButtons = new SimpleIntegerProperty();
    private SimpleIntegerProperty dpi = new SimpleIntegerProperty();
    private SimpleBooleanProperty ergonomic = new SimpleBooleanProperty();
    private SimpleBooleanProperty wireless = new SimpleBooleanProperty();

    public Mouse(String manufacturer, String model, int numberButtons, int dpi, boolean ergonomic, boolean wireless,
                 double price) {
        super(manufacturer, model, price);

        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public int getNumberButtons() {
        return numberButtons.getValue();
    }

    private void setNumberButtons(int numberButtons) {

        if(numberButtons < 0 ){
            throw new IllegalArgumentException("Number of buttons on a mouse cannot be negative");
        }
        else {
            this.numberButtons.set(numberButtons);
        }
    }

    public int getDpi() {
        return dpi.getValue();
    }


    private void setDpi(int dpi) {
        if(dpi < 0 ){
            throw new IllegalArgumentException("DPI cannot be negative");
        }
        else {
            this.dpi.set(dpi);
        }
    }

    public boolean isErgonomic() {
        return ergonomic.getValue();
    }

    private void setErgonomic(boolean ergonomic) {
        this.ergonomic.set(ergonomic);
    }

    public boolean isWireless() {
        return wireless.getValue();
    }

    private void setWireless(boolean wireless) {
        this.wireless.set(wireless);
    }

    @Override
    public String toString(){
        return getComponentType() + ": " + getName();
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV( getComponentType(),
                getManufacturer(),
                getModel(),
                getNumberButtons(),
                getDpi(),
                isErgonomic(),
                isWireless(),
                getPrice()
        );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getNumberButtons());
        objectOutputStream.writeInt(getDpi());
        objectOutputStream.writeBoolean(isErgonomic());
        objectOutputStream.writeBoolean(isWireless());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        int numberButtons = objectInputStream.readInt();
        int dpi = objectInputStream.readInt();
        Boolean ergonomic = objectInputStream.readBoolean();
        Boolean wireless = objectInputStream.readBoolean();

        this.numberButtons = new SimpleIntegerProperty();
        this.dpi = new SimpleIntegerProperty();
        this.ergonomic = new SimpleBooleanProperty();
        this.wireless = new SimpleBooleanProperty();

        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
    }
}
