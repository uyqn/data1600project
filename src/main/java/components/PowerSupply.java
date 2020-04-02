package components;

import javafx.beans.property.SimpleIntegerProperty;

public class PowerSupply extends Component {

    private SimpleIntegerProperty PowerCapacity;

    public PowerSupply(String name, String manufacturer, String model, double price, int PowerCapacity){
        super(name, manufacturer, model, price);

        setPowerCapacity(PowerCapacity);
    }

    public int getPowerCapacity(){
        return PowerCapacity.get();
    }

    private void setPowerCapacity(int PowerCapacity){
        this.PowerCapacity.set(PowerCapacity);
    }
}
