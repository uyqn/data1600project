package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PowerSupply extends Component {

    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Power Supply");
    private SimpleIntegerProperty PowerCapacity;

    public String getComponentType(){
        return COMPONENT_TYPE.get();
    }

    public PowerSupply(String manufacturer, String model, double price, int PowerCapacity){
        super(manufacturer, model, price);

        setPowerCapacity(PowerCapacity);
    }


    public int getPowerCapacity(){
        return PowerCapacity.get();
    }

    private void setPowerCapacity(int PowerCapacity){
        if(PowerCapacity < 0 ){
            throw new IllegalArgumentException("Power capacity cannot be negative");
        }
        else {
            this.PowerCapacity.set(PowerCapacity);
        }
    }

    public String ToString(){
        return "PowerSupply: " + getName() + "Power Capacity: " +PowerCapacity+" W";
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getPowerCapacity(),
                getPrice()
        );
    }
}
