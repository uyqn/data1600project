package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mouse extends Component{
    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Mouse");

    private SimpleIntegerProperty numberButtons;
    private SimpleIntegerProperty dpi;
    private SimpleBooleanProperty ergonomic;
    private SimpleBooleanProperty wireless;

    public Mouse(String manufacturer, String model, int numberButtons, int dpi, boolean ergonomic, boolean wireless,
                 double price) {
        super(manufacturer, model, price);

        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
    }

    public String getComponentType() {
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
}
