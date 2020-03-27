package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Component {
    public static final transient String DELIMITER = ",";

    private transient SimpleStringProperty manufacturer = new SimpleStringProperty();
    private transient SimpleStringProperty model = new SimpleStringProperty();
    private transient SimpleDoubleProperty price = new SimpleDoubleProperty();

    private transient SimpleStringProperty name = new SimpleStringProperty();
    private transient SimpleObjectProperty<Dimension> dimension = new SimpleObjectProperty<>(new Dimension());

    public Component(String manufacturer, String model,
                     double price) {
        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);

        setName();
    }

    public String getName() {
        return name.getValue();
    }

    public void setName() {
        this.name.set(getManufacturer() + " " + getModel());
    }

    public String getManufacturer() {
        return manufacturer.getValue();
    }

    public void setManufacturer(String manufacturer) {
        if(!manufacturer.matches("[A-Z][a-z]+|[A-Z]+")){
            throw new IllegalArgumentException("Invalid name format for manufacturer");
        }
        this.manufacturer.set(manufacturer);
    }

    public String getModel() {
        return model.getValue();
    }

    public void setModel(String model) {
        if(!model.matches("[A-Za-z0-9 \\-]+")){
            throw new IllegalArgumentException("Invalid name format for model");
        }
        this.model.set(model);
    }

    public double getPrice() {
        return price.getValue();
    }

    public void setPrice(double price) {
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price.set(price);
    }

    public String getDimension() {
        return dimension.getValue().toString();
    }

    public double getWidth(){
        return dimension.getValue().getWidth();
    }

    public double getDepth(){
        return dimension.getValue().getWidth();
    }

    public double getHeight(){
        return dimension.getValue().getHeight();
    }

    public double getArea(){
        return dimension.getValue().getArea();
    }

    public double getVolume(){
        return dimension.getValue().getVolume();
    }

    public void setDimension(double width, double depth, double height){
        dimension.getValue().setWidth(width);
        dimension.getValue().setDepth(depth);
        dimension.getValue().setHeight(height);
    }

    public void setDimension(String dimension){
        this.dimension.getValue().setDimension(dimension);
    }

    public void setDimension(Dimension dimension) {
        this.dimension.set(dimension);
    }
}
