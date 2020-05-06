package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Monitor extends Component{
    private transient static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Monitor");

    private transient SimpleIntegerProperty refreshRate = new SimpleIntegerProperty();

    public Monitor(String[] csv) {
        super(csv[0], csv[1], Double.parseDouble(csv[3]));

        setRefreshRate(Integer.parseInt(csv[2]));
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public int getRefreshRate(){
        return refreshRate.getValue();
    }

    private void setRefreshRate(int RefreshRate){

        if (RefreshRate<0) {
            throw new IllegalArgumentException("Refresh rate cannot be negative");
        }
        else{
            this.refreshRate.set(RefreshRate);
        }
    }

    @Override
    public String toString(){
        return getComponentType() + ": " + getName() + "\n" +
                "Refresh rate " +getRefreshRate()+" Hz";
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
