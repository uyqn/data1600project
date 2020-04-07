package components;

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
    private transient SimpleStringProperty noise = new SimpleStringProperty();
    private transient SimpleDoubleProperty powerConsumption = new SimpleDoubleProperty();

    public Cooler(String manufacturer,
                   String model,
                   String dimension,
                   String rpm,
                   String noise,
                   double powerConsumption,
                   double price) {
        super(manufacturer, model, price);
        super.setDimension(dimension);
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
                  int powerConsumption,
                  double price) {
        super(manufacturer, model, price);
        super.setDimension(width, depth, height);
        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
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
        return noise.getValue();
    }

    public void setNoise(String noise) {
        if(!noise.matches(
                "[0](\\.[0]{1,2})?((\\s)?[Dd][Bb][Aa])?|" +
                "[0-9]{1,2}(\\.[0-9]{1,2})?((\\s)?[Dd][Bb][Aa])?|"+
                "[0-9]{1,2}(\\.[0-9]{1,2})?((\\s)?[Dd][Bb][Aa])?[/\\-~][0-9]{1,2}(\\.[0-9]{1,2})?((\\s)?[Dd][Bb][Aa])?"
        )){
            throw new IllegalArgumentException("Invalid input for noise level");
        }

        double coreNoise;
        double maxNoise;

        if(noise.matches("[0-9]{1,2}(\\.[0-9]{1,2})?((\\s)?[Dd][Bb][Aa])?[/\\-~][0-9]{1,2}(\\.[0-9]{1,2})?((\\s)?[Dd][Bb][Aa])?")){
            String formatted = noise.replaceAll("\\s|[Dd][Bb][Aa]", "");
            String[] split = formatted.split("[/\\-]");

            coreNoise = Double.parseDouble(split[0]);
            maxNoise = Double.parseDouble(split[1]);
        } else if (noise.matches("[0-9]{1,2}(\\.[0-9]{1,2})?((\\s)?[Dd][Bb][Aa])?|")){
            String formatted = noise.replaceAll("\\s|[Dd][Bb][Aa]", "");
            coreNoise = maxNoise = Double.parseDouble(formatted);
        } else{
            coreNoise = maxNoise = 0;
        }

        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);

        if(getCoreNoise() != getMaxNoise()) {
            this.noise.set(getCoreNoise() + "-" + getMaxNoise() + " dBA");
        } else {
            this.noise.set(getCoreNoise() + " dBA");
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
        return coreRPM.getValue() + "-" + maxRPM.getValue();
    }

    public void setRPM(String rpm){
        if(!rpm.matches("[1-9][0-9]{2,4}((\\s)?[Rr][Pp][Mm])?|[0]((\\s)?[Rr][Pp][Mm])?|[1-9][0-9]{2,4}((\\s)?[Rr][Pp][Mm])" +
                "?[/\\-~][1-9][0-9]{2,4}((\\s)?[Rr][Pp][Mm])?")){
            throw new IllegalArgumentException("RPM format is #.##-#.## dBA");
        }

        int coreRPM;
        int maxRPM;

        if(rpm.matches("[1-9][0-9]{2,4}((\\s)?[Rr][Pp][Mm])?[/\\-~][1-9][0-9]{2,4}((\\s)?[Rr][Pp][Mm])?")) {
            String formatted = rpm.replaceAll("\\s|[Rr][Pp][Mm]", "");
            String[] split = formatted.split("[/\\-]");

            coreRPM = Integer.parseInt(split[0]);
            maxRPM = Integer.parseInt(split[1]);
        } else if(rpm.matches("[1-9][0-9]{2,4}((\\s)?[Rr][Pp][Mm])?")) {
            String formatted = rpm.replaceAll("\\s|[Rr][Pp][Mm]", "");
            coreRPM = maxRPM = Integer.parseInt(formatted);
        } else{
            coreRPM = maxRPM = 0;
        }

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);

        if(getCoreRPM() != getMaxRPM()) {
            this.rpm.set(getCoreRPM() + "~" + getMaxRPM() + " RPM");
        } else{
            this.rpm.set(getCoreRPM() + " RPM");
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
                        "Dimension: %s\n" +
                        "RPM: %s RPM\n" +
                        "Noise: %s dBA\n" +
                        "Power consumption: %s W\n" +
                        "Price: %s NOK",
                getComponentType(), getName(), getDimension(), getRPM(), getNoise(), getPowerConsumption(), getPrice()
                );
    }
}
