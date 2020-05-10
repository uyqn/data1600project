package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Mouse extends Component implements Serializable {
    private transient static final String COMPONENT_TYPE = "Mouse";

    private transient SimpleIntegerProperty numberButtons = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty dpi = new SimpleIntegerProperty();
    private transient SimpleBooleanProperty ergonomic = new SimpleBooleanProperty();
    private transient SimpleBooleanProperty wireless = new SimpleBooleanProperty();

    public Mouse(String [] csv) {
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setNumberButtons(Integer.parseInt(csv[3]));
        setDpi(Integer.parseInt(csv[4]));
        setErgonomic(Boolean.parseBoolean(csv[5]));
        setWireless(Boolean.parseBoolean(csv[6]));

    }

    public Mouse(String manufacturer,
                 String model,
                 int numberButtons,
                 int dpi,
                 boolean ergonomic,
                 boolean wireless,
                 double price) {
        super(manufacturer, model, price);

        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
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
        String ergonomic = isErgonomic() ? "Yes" : "No";
        String wireless = isWireless() ? "Yes" : "No";

        return getComponentType() + ": " + getName() + "\n" +
                "Buttons: " + getNumberButtons() + "\n" +
                "Polling rate: " + getDpi() + "DPI \n" +
                "Ergonomic: " + ergonomic + "\n" +
                "Wireless: " + wireless + "\n" +
                "Price: " + getPrice();
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
        boolean ergonomic = objectInputStream.readBoolean();
        boolean wireless = objectInputStream.readBoolean();

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
