package components.Storage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Harddrive extends Storage{

    private static final transient String COMPONENT_TYPE ="Hard drive";

    private transient SimpleStringProperty busType=new SimpleStringProperty();
    private transient SimpleIntegerProperty RPM=new SimpleIntegerProperty();

    public Harddrive(String manufacturer,
                     String model,
                     double price,
                     double capacity,
                     int RPM,
                     String form){

        super(manufacturer, model, price, capacity, form);

        setRPM(RPM);

    }

    public static String getComponentType() {
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