package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Monitor extends Component{
    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Monitor");

    private transient SimpleIntegerProperty refreshRate = new SimpleIntegerProperty();

    public Monitor(String manufacturer, String model, int refreshRate, double price) {
        super(manufacturer, model, price);

        setRefreshRate(refreshRate);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public int getRefreshRate(){
        return refreshRate.getValue();
    }

    private void setRefreshRate(int RefreshRate){
        this.refreshRate.set(RefreshRate);
    }

    @Override
    public String toString(){
        return getComponentType() + ": " + getName() + "\n" +
                "RefreshRate " +getRefreshRate()+" Hz";
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getManufacturer(),
                getModel(),
                getRefreshRate(),
                getPrice()
        );
    }
}
