package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleStringProperty;

public class Cabin extends Component implements Connectable {
    private transient static final String COMPONENT_TYPE = "Cabin";
    private transient SimpleStringProperty formFactor = new SimpleStringProperty();

    public Cabin(String manufacturer, String model, String formFactor, double price) {
        super(manufacturer, model, price);
        setFormFactor(formFactor);
    }

    public String getFormFactor() {
        return formFactor.getValue();
    }

    public void setFormFactor(String formFactor) {
        if(!formFactor.matches("([A-Za-z]{4}\\s)?(" +
                "([Ee][Xx][Tt][Ee][Nn][Dd][Ee][Dd]|[A-Za-z]{1,5})(\\s|-)?)?[A-Za-z]{2,4}")){
            throw new IllegalArgumentException("The format for form factor is not recognizable.");
        }
        this.formFactor.set(formFactor);
    }

    @Override
    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getFormFactor(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                        "Form factor: %s\n" +
                        "Price: %s NOK",
                COMPONENT_TYPE, getName(), formFactor, String.format("%.2f",getPrice()));
    }

    @Override
    public boolean connect(Connectable item) {
        return false;
    }
}
