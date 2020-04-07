package components;

import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cooler extends Component {
    private static final transient SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Cooler");

    private transient SimpleIntegerProperty coreRPM = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty maxRPM = new SimpleIntegerProperty();
    private transient SimpleDoubleProperty coreNoise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty maxNoise = new SimpleDoubleProperty();
    private transient SimpleStringProperty rpm = new SimpleStringProperty();
    private transient SimpleDoubleProperty powerConsumption = new SimpleDoubleProperty();

    public Cooler(String manufacturer,
                   String model,
                   String dimension,
                   String rpm,
                   String noise,
                   double powerConsumption,
                   double price) {
        super(manufacturer, model, price);
        setDimension(dimension);
        setRPM(rpm);
        setNoise(noise);
        setPowerConsumption(powerConsumption);
    }

    public Cooler(String manufacturer,
                  String model,
                  double width,
                  double depth,
                  double height,
                  int coreRPM,
                  int maxRPM,
                  double coreNoise,
                  double maxNoise,
                  double powerConsumption,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);
        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
        setPowerConsumption(powerConsumption);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    @Override
    public void setDimension(String dimension){
        if(Extract.doubles(dimension).size() < 3){
            throw new IllegalArgumentException("All three fields of dimension must be specified");
        }
        super.setWidth(Extract.doubles(dimension).get(0));
        super.setDepth(Extract.doubles(dimension).get(1));
        super.setHeight(Extract.doubles(dimension).get(2));
    }

    public double getCoreNoise() {
        return coreNoise.getValue();
    }

    public void setCoreNoise(double coreNoise) {
        if(coreNoise < 0){
            throw new IllegalArgumentException("Noise cannot be negative");
        }
        if(coreNoise > getMaxNoise()){
            setMaxNoise(coreNoise);
        }
        this.coreNoise.set(coreNoise);
    }

    public double getMaxNoise() {
        return maxNoise.getValue();
    }

    public void setMaxNoise(double maxNoise) {
        if(maxNoise < 0){
            throw new IllegalArgumentException("Max noise cannot be less than base noise");
        }
        if(maxNoise < getCoreNoise()){
            setCoreNoise(maxNoise);
        }
        this.maxNoise.set(maxNoise);
    }

    public String getNoise() {
        return (getCoreNoise() != getMaxNoise()) ?
                getCoreNoise() + " - " + getMaxNoise() :
                String.valueOf(getCoreNoise());
    }

    public void setNoise(String noise) {
        if(noise.matches(" - ")){
            throw new IllegalArgumentException("Base noise and max noise are empty\n" +
                    "One of the fields must be filled");
        }

        if(Extract.doubles(noise).size() == 1){
            setCoreNoise(Extract.doubles(noise).get(0));
            setMaxNoise(Extract.doubles(noise).get(0));
        }
        else {
            setCoreNoise(
                    Math.min(Extract.doubles(noise).get(0),
                            Extract.doubles(noise).get(1))
            );
            setMaxNoise(
                    Math.max(Extract.doubles(noise).get(0),
                            Extract.doubles(noise).get(1))
            );
        }
    }

    public double getCoreRPM() {
        return coreRPM.getValue();
    }

    public void setCoreRPM(int coreRPM) {
        if(coreRPM < 0){
            throw new IllegalArgumentException("RPM cannot be negative");
        }
        if(coreRPM > getMaxRPM()){
            setMaxRPM(coreRPM);
        }
        this.coreRPM.set(coreRPM);
    }

    public double getMaxRPM() {
        return maxRPM.getValue();
    }

    public void setMaxRPM(int maxRPM) {
        if(maxRPM < 0){
            throw new IllegalArgumentException("Max RPM cannot be negative");
        }
        if(maxRPM < getCoreRPM()){
            setCoreRPM(maxRPM);
        }

        this.maxRPM.set(maxRPM);
    }

    public String getRPM(){
        return (getCoreRPM() != getMaxRPM()) ?
                getCoreRPM() + " - " + getMaxRPM() :
                String.valueOf(getCoreRPM());
    }

    public void setRPM(String rpm){
        if(rpm.matches(" - ")){
            throw new IllegalArgumentException("Base rpm and max rpm are empty\n" +
                    "One of the fields must be filled");
        }

        if(Extract.ints(rpm).size() == 1){
            setCoreRPM(Extract.ints(rpm).get(0));
            setMaxRPM(Extract.ints(rpm).get(0));
        }
        else {
            setCoreRPM(Math.min(Extract.ints(rpm).get(0),
                    Extract.ints(rpm).get(Extract.ints(rpm).size() - 1))
            );
            setMaxRPM(Math.max(Extract.ints(rpm).get(0),
                    Extract.ints(rpm).get(Extract.ints(rpm).size() - 1))
            );
        }

    }

    public double getPowerConsumption(){
        return powerConsumption.getValue();
    }

    public void setPowerConsumption(double powerConsumption){
        if(powerConsumption < 0){
            throw new IllegalArgumentException("Power consumption cannot be negative");
        }
        this.powerConsumption.set(powerConsumption);
    }

    @Override
    public String toCSV(){
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getDimension(),
                getRPM(),
                getNoise(),
                getPowerConsumption(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                        "Dimension: %s cm\n" +
                        "RPM: %s RPM\n" +
                        "Noise: %s dBA\n" +
                        "Power consumption: %s W\n" +
                        "Price: %s NOK",
                getComponentType(), getName(), getDimension(), getRPM(), getNoise(), getPowerConsumption(), getPrice()
                );
    }
}
