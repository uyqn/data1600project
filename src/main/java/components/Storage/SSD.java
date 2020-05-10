package components.Storage;

import javafx.beans.property.SimpleStringProperty;

public class SSD extends Storage{

    private static final transient String COMPONENT_TYPE ="SSD";

    private transient SimpleStringProperty busType=new SimpleStringProperty();

    public SSD(String manufacturer,
               String model,
               double capacity,
               double price){

        super(manufacturer, model, capacity, price);

    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    @Override
    public String toCSV() {
        return null;
    }
}
