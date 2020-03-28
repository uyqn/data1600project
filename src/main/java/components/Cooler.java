package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cooler extends Component {
    private static final transient SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Cooler");

    private transient SimpleDoubleProperty coreRPM = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty maxRPM = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty coreNoise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty maxNoise = new SimpleDoubleProperty();

    private transient SimpleStringProperty rpm = new SimpleStringProperty();
    private transient SimpleStringProperty noise = new SimpleStringProperty();

    public Cooler(String manufacturer,
                  String model,
                  Dimension dimension,
                  double coreRPM,
                  double maxRPM,
                  double coreNoise,
                  double maxNoise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
    }

    public Cooler(String manufacturer,
                  String model,
                  Dimension dimension,
                  double coreRPM,
                  double maxRPM,
                  String noise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setNoise(noise);
    }

    public Cooler(String manufacturer,
                  String model,
                  Dimension dimension,
                  String rpm,
                  double coreNoise,
                  double maxNoise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setRPM(rpm);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
    }

    public Cooler(String manufacturer,
                  String model,
                  String dimension,
                  double coreRPM,
                  double maxRPM,
                  double coreNoise,
                  double maxNoise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
    }

    public Cooler(String manufacturer,
                  String model,
                  String dimension,
                  double coreRPM,
                  double maxRPM,
                  String noise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setNoise(noise);
    }

    public Cooler(String manufacturer,
                  String model,
                  String dimension,
                  String rpm,
                  double coreNoise,
                  double maxNoise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setRPM(rpm);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
    }

    public Cooler(String manufacturer,
                   String model,
                   String dimension,
                   String rpm,
                   String noise,
                   double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);

        setRPM(rpm);
        setNoise(noise);
    }

    public Cooler(String manufacturer,
                  String model,
                  double width,
                  double depth,
                  double height,
                  double coreRPM,
                  double maxRPM,
                  double coreNoise,
                  double maxNoise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
    }

    public Cooler(String manufacturer,
                  String model,
                  double width,
                  double depth,
                  double height,
                  double coreRPM,
                  double maxRPM,
                  String noise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setNoise(noise);
    }

    public Cooler(String manufacturer,
                  String model,
                  double width,
                  double depth,
                  double height,
                  String rpm,
                  double coreNoise,
                  double maxNoise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);

        setRPM(rpm);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
    }

    public Cooler(String manufacturer,
                  String model,
                  double width,
                  double depth,
                  double height,
                  String rpm,
                  String noise,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);

        setRPM(rpm);
        setNoise(noise);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public double getCoreNoise() {
        return coreNoise.getValue();
    }

    public void setCoreNoise(double coreNoise) {
        if(coreNoise < 0){
            throw new IllegalArgumentException("Noise cannot be negative");
        }
        this.coreNoise.set(coreNoise);
    }

    public double getMaxNoise() {
        return maxNoise.getValue();
    }

    public void setMaxNoise(double maxNoise) {
        if(maxNoise < coreNoise.getValue()){
            throw new IllegalArgumentException("Max noise cannot be less than base noise");
        }
        this.maxNoise.set(maxNoise);
    }

    public String getNoise() {
        return noise.getValue();
    }

    public void setNoise(String noise) {
        if(!noise.matches("[1-9]([0-9]+)?(\\.[0-9]*)?[/\\-][1-9]([0-9]+)?(\\.[0-9]*)?((\\s)?dBA)?")){
            throw new IllegalArgumentException("RPM format is #.##-#.## dBA");
        }else {
            String formatted = noise.replaceAll("\\s|dBA", "");
            String[] split = formatted.split("[/\\-]");

            setCoreNoise(Double.parseDouble(split[0]));
            setMaxNoise(Double.parseDouble(split[1]));
            this.noise.set(getCoreNoise() + "-" + getMaxNoise());
        }
    }

    public double getCoreRPM() {
        return coreRPM.getValue();
    }

    public void setCoreRPM(double coreRPM) {
        if(coreRPM < 0){
            throw new IllegalArgumentException("RPM cannot be negative");
        }
        this.coreRPM.set(coreRPM);
    }

    public double getMaxRPM() {
        return maxRPM.getValue();
    }

    public void setMaxRPM(double maxRPM) {
        if(maxRPM < coreRPM.getValue()){
            throw new IllegalArgumentException("Max RPM cannot be less than core RPM");
        }
        this.maxRPM.set(maxRPM);
    }

    public String getRPM(){
        return coreRPM.getValue() + "-" + maxRPM.getValue();
    }

    public void setRPM(String rpm){
        if(!rpm.matches("[1-9][0-9]+(\\.[0-9]*)?[/\\-][1-9][0-9]+(\\.[0-9]*)?((\\s)?RPM)?")){
            throw new IllegalArgumentException("RPM format is #.##-#.## dBA");
        }else {
            String formatted = rpm.replaceAll("\\s|dBA", "");
            String[] split = formatted.split("[/\\-]");

            setCoreRPM(Double.parseDouble(split[0]));
            setMaxRPM(Double.parseDouble(split[1]));
            this.rpm.set(getCoreRPM() + "-" + getMaxRPM() + " dBA");
        }
    }

    public String toCSV(){
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getDimension(),
                getRPM(),
                getNoise(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                        "Dimension: %s\n" +
                        "RPM: %s rpm\n" +
                        "Noise: %s dBA\n" +
                        "Price: %s NOK",
                getComponentType(), getName(), getDimension(), getRPM(), getNoise(), getPrice()
                );
    }
}
