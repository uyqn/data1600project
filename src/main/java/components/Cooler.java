package components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cooler extends Component {
    private static final transient SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Cooler");

    private transient SimpleBooleanProperty liquid = new SimpleBooleanProperty();
    private transient SimpleDoubleProperty noise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty rpm = new SimpleDoubleProperty();

    public Cooler(String manufacturer,
                  String model,
                  boolean liquid,
                  Dimension dimension,
                  double noise,
                  double rpm,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setLiquid(liquid);
        setNoise(noise);
        setRpm(rpm);
    }

    public Cooler(String manufacturer,
                  String model,
                  boolean liquid,
                  String dimension,
                  double noise,
                  double rpm,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setLiquid(liquid);
        setNoise(noise);
        setRpm(rpm);
    }

    public Cooler(String manufacturer,
                  String model,
                  boolean liquid,
                  double width,
                  double depth,
                  double height,
                  double noise,
                  double rpm,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);

        setLiquid(liquid);
        setNoise(noise);
        setRpm(rpm);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public boolean isLiquid() {
        return liquid.getValue();
    }

    public void setLiquid(boolean liquid) {
        this.liquid.set(liquid);
    }

    public double getNoise() {
        return noise.getValue();
    }

    public void setNoise(double noise) {
        if(noise < 0){
            throw new IllegalArgumentException("Noise cannot be negative");
        }
        this.noise.set(noise);
    }

    public double getRpm() {
        return rpm.getValue();
    }

    public void setRpm(double rpm) {
        if(rpm < 0){
            throw new IllegalArgumentException("RPM cannot be negative");
        }
        this.rpm.set(rpm);
    }
}
