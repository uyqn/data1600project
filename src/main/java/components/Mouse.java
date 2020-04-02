package components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Mouse extends Component{

    private SimpleIntegerProperty numberButtons;
    private SimpleIntegerProperty dpi;
    private SimpleBooleanProperty ergonomic;
    private SimpleBooleanProperty wireless;

    public Mouse(String manufacturer, String model, double price, int numberButtons, int dpi, boolean ergonomic, boolean wireless) {
        super(manufacturer, model, price);

        setNumberButtons(numberButtons);
        setDpi(dpi);
        setErgonomic(ergonomic);
        setWireless(wireless);
    }

    public int getNumberButtons() {
        return numberButtons.get();
    }

    public SimpleIntegerProperty numberButtonsProperty() {
        return numberButtons;
    }

    private void setNumberButtons(int numberButtons) {
        this.numberButtons.set(numberButtons);
    }

    public int getDpi() {
        return dpi.get();
    }

    public SimpleIntegerProperty dpiProperty() {
        return dpi;
    }

    private void setDpi(int dpi) {
        this.dpi.set(dpi);
    }

    public boolean isErgonomic() {
        return ergonomic.get();
    }

    public SimpleBooleanProperty ergonomicProperty() {
        return ergonomic;
    }

    private void setErgonomic(boolean ergonomic) {
        this.ergonomic.set(ergonomic);
    }

    public boolean isWireless() {
        return wireless.get();
    }

    public SimpleBooleanProperty wirelessProperty() {
        return wireless;
    }

    private void setWireless(boolean wireless) {
        this.wireless.set(wireless);
    }

    public String ToString(){
        return "Mouse: " + getModel();
    }



}
