package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Keyboard extends Component {

    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Keyboard");
    private SimpleBooleanProperty Tactile;

    public Keyboard(String manufacturer, String model, double price, boolean Tactile) {
        super(manufacturer, model, price);

        setTactile(Tactile);
    }

    public boolean isTactile() {
        return Tactile.get();
    }

    public void setTactile(boolean tactile) {
        this.Tactile.set(tactile);
    }

    public String getComponentType(){
        return COMPONENT_TYPE.getValue();
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
}
