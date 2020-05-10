package components.Storage;

import components.Component;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Storage extends Component {


    private transient SimpleDoubleProperty capacity=new SimpleDoubleProperty();
    private transient SimpleStringProperty form=new SimpleStringProperty();


    public Storage(String manufacturer, String model, double capacity, double price){

        super(manufacturer, model, price);
        setCapacity(capacity);
    }

    public double getCapacity(){return capacity.getValue();}

    public void setCapacity(double capacity){
        if (capacity<0){
            throw new IllegalArgumentException("Capaity must be positive");
        }
        this.capacity.set(capacity);


    }

    public String getForm(){return form.getValue();}

    public void setForm(String form){

        this.form.set(form);

    }



}






