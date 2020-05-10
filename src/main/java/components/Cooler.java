package components;

import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Cooler extends Component implements Serializable {
    public static final transient String COMPONENT_TYPE = "Cooler";

    private transient SimpleIntegerProperty coreRPM = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty maxRPM = new SimpleIntegerProperty();
    private transient SimpleDoubleProperty coreNoise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty maxNoise = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty powerConsumption = new SimpleDoubleProperty();

    public Cooler(String[] csv){
        super(csv[1], csv[2], Double.parseDouble(csv[7]));

        setDimension(csv[3]);
        setRPM(csv[4]);
        setNoise(csv[5]);
        setPowerConsumption(Double.parseDouble(csv[6]));
    }

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

    @Override
    public String getComponentType() {
        return COMPONENT_TYPE;
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

    public int getCoreRPM() {
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

    public int getMaxRPM() {
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
                    Extract.ints(rpm).get(1))
            );
            setMaxRPM(Math.max(Extract.ints(rpm).get(0),
                    Extract.ints(rpm).get(1))
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
                COMPONENT_TYPE,
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
                COMPONENT_TYPE, getName(), getDimension(), getRPM(), getNoise(), getPowerConsumption(),
                String.format("%.2f",getPrice())
                );
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
        objectOutputStream.writeUTF(getDimension());

        objectOutputStream.writeInt(getCoreRPM());
        objectOutputStream.writeInt(getMaxRPM());
        objectOutputStream.writeDouble(getCoreNoise());
        objectOutputStream.writeDouble(getMaxNoise());
        objectOutputStream.writeDouble(getPowerConsumption());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        String dimension = objectInputStream.readUTF();

        int coreRPM = objectInputStream.readInt();
        int maxRPM = objectInputStream.readInt();
        double coreNoise = objectInputStream.readDouble();
        double maxNoise = objectInputStream.readDouble();
        double powerConsumption = objectInputStream.readDouble();

        this.coreRPM = new SimpleIntegerProperty();
        this.maxRPM = new SimpleIntegerProperty();
        this.coreNoise = new SimpleDoubleProperty();
        this.maxNoise = new SimpleDoubleProperty();
        this.powerConsumption = new SimpleDoubleProperty();

        setCoreRPM(coreRPM);
        setMaxRPM(maxRPM);
        setCoreNoise(coreNoise);
        setMaxNoise(maxNoise);
        setPowerConsumption(powerConsumption);
    }
}
