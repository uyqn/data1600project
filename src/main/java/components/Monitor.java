package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;

public class Monitor extends Component{

    private SimpleIntegerProperty RefreshRate;

    public Monitor(String manufacturer, String model, int RefreshRate, double price) {
        super(manufacturer, model, price);

        setRefreshRate(RefreshRate);
    }

    public int getRefreshRate(){
        return RefreshRate.get();
    }

    private void setRefreshRate(int RefreshRate){
        this.RefreshRate.set(RefreshRate);
    }

    @Override
    public String toString(){
        return "Monitor: " + getName() + "RefreshRate " +RefreshRate+" Hz";
    }

    @Override
    String toCSV() {
        return Formatter.toCSV(
                getManufacturer(),
                getModel(),
                getRefreshRate(),
                getPrice()
        );
    }
}
