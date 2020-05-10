package components.Storage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HDD extends Storage{

    private static final transient String COMPONENT_TYPE ="HDD";

    private transient SimpleStringProperty busType = new SimpleStringProperty();
    private transient SimpleIntegerProperty RPM = new SimpleIntegerProperty();

    public HDD(String manufacturer,
               String model,
               double capacity,
               int RPM,
               double price){

        super(manufacturer, model, capacity, price);

        setRPM(RPM);

    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public int getRPM(){return RPM.getValue();}

    public void setRPM(int RPM){

        if(RPM<0){
            throw new IllegalArgumentException("RPM must be higher than 0!");

        }
        this.RPM.set(RPM);
    }


    @Override
    public String toCSV() {
        return null;
    }
}