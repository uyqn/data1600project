package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Keyboard extends Component {

    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Keyboard");
    private SimpleBooleanProperty Tactile;

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
}
