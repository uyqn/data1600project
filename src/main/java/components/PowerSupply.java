package components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PowerSupply extends Component {

    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Power Supply");
    private SimpleIntegerProperty PowerCapacity;

    public PowerSupply(String manufacturer, String model, double price, int PowerCapacity){
        super(manufacturer, model, price);

        setPowerCapacity(PowerCapacity);
    }

    public int getPowerCapacity(){
        return PowerCapacity.get();
    }

    private void setPowerCapacity(int PowerCapacity){
        this.PowerCapacity.set(PowerCapacity);
    }

    public String ToString(){
        return "PowerSupply: " + getName() + "Power Capacity: " +PowerCapacity+" W";
    }

    @Override
    public String toCSV() {
        return null;
    }
}
