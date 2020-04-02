package components;

import javafx.beans.property.SimpleIntegerProperty;

public class Monitor extends Component{

    private SimpleIntegerProperty RefreshRate;

    public Monitor(String manufacturer, String model, double price, int RefreshRate) {
        super(manufacturer, model, price);

        setRefreshRate(RefreshRate);
    }

    public int getRefreshRate(){
        return RefreshRate.get();
    }

    private void setRefreshRate(int RefreshRate){
        this.RefreshRate.set(RefreshRate);
    }

    public String ToString(){
        return "Monitor: " + getName() + "RefreshRate " +RefreshRate+" Hz";
    }
}
