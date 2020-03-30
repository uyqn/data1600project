package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CPU extends Component{
    private static final transient SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("CPU");

    private transient SimpleStringProperty socket = new SimpleStringProperty();
    private transient SimpleIntegerProperty coreCount = new SimpleIntegerProperty();
    private transient SimpleDoubleProperty coreClock = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty boostClock = new SimpleDoubleProperty();
    private transient SimpleIntegerProperty powerConsumption = new SimpleIntegerProperty();

    private transient SimpleStringProperty clockSpeed = new SimpleStringProperty();

    public CPU(String manufacturer,
               String model,
               String socket,
               int coreCount,
               String clockSpeed,
               int powerConsumption,
               double price) {
        super(manufacturer, model, price);

        setSocket(socket);
        setCoreCount(coreCount);
        setClockSpeed(clockSpeed);
        setPowerConsumption(powerConsumption);
    }

    public CPU(String manufacturer,
               String model,
               String socket,
               int coreCount,
               double coreClock,
               double boostClock,
               int powerConsumption,
               double price) {
        super(manufacturer, model, price);

        setSocket(socket);
        setCoreCount(coreCount);
        setCoreClock(coreClock);
        setBoostClock(boostClock);
        setPowerConsumption(powerConsumption);

        this.clockSpeed.set(this.coreClock+"/"+this.boostClock+" GHz");
    }

    public String getCOMPONENT_TYPE() {
        return COMPONENT_TYPE.getValue();
    }

    public String getSocket() {
        return socket.getValue();
    }

    public void setSocket(String socket) {
        if(!socket.matches("[a-z]?[A-Z]{1,3}(\\s)?([1-9][+]|[1-9][0-9]{0,3}([-][1-9])?)")){
            throw new IllegalArgumentException("Format of socket type is invalid");
        }
        this.socket.set(socket);
    }

    public int getCoreCount() {
        return coreCount.getValue();
    }

    public void setCoreCount(int coreCount) {
        boolean invalid = true;

        for(int i = 1 ; i < 4 ; i++){
            if(coreCount == i){
                invalid = false;
                break;
            }
        }

        for(int i = 4 ; i < 25 ; i += 2){
            if(coreCount == i){
                invalid = false;
                break;
            }
        }

        if(invalid && coreCount !=32 && coreCount !=64){
            throw new IllegalArgumentException("Number of cores must be either one of these:\n" +
                    "- A number between 1 and 3\n" +
                    "- An even number between 4 and 24\n" +
                    "- 32 or 64");
        }
        this.coreCount.set(coreCount);
    }

    public double getCoreClock() {
        return coreClock.getValue();
    }

    public void setCoreClock(double coreClock) {
        if(coreClock < 1.1 || coreClock > 5.0){
            throw new IllegalArgumentException("Core clock frequency should be between or equal to 1.1 and "
                    + getBoostClock() + " GHz");
        }
        if(coreClock > getBoostClock()){
            setBoostClock(coreClock);
        }

        this.coreClock.set(coreClock);
    }

    public double getBoostClock() {
        return boostClock.getValue();
    }

    public void setBoostClock(double boostClock) {
        if(boostClock < 1.1 || boostClock > 5.0){
            throw new IllegalArgumentException("Overclocked speed should be between or equal to " + getCoreClock() +
                    " and 5.0 GHz");
        }
        if(boostClock < getCoreClock()){
            setCoreClock(boostClock);
        }

        this.boostClock.set(boostClock);
    }

    public int getPowerConsumption() {
        return powerConsumption.getValue();
    }

    public void setPowerConsumption(int powerConsumption) {
        if(powerConsumption < 10 || powerConsumption > 300){
            throw new IllegalArgumentException("Thermal design power must be between 10 and 300");
        }
        this.powerConsumption.set(powerConsumption);
    }

    public String getClockSpeed() {
        return clockSpeed.getValue();
    }

    public void setClockSpeed(double core, double boost){
        if(core > boost){
            throw new IllegalArgumentException("Core clock speed should be greater than the boost clock speed");
        }

        setCoreClock(core);
        setBoostClock(boost);
        this.clockSpeed.set(getCoreClock() + "/" + getBoostClock() + " GHz");
    }

    public void setClockSpeed(String clockSpeed) {
        if(!clockSpeed.matches(
                "([-+])?[1-9](\\.[0-9]{1,2})?((\\s)?[Gg][Hh][Zz])?|" +
                        "([-+])?[1-9](\\.[0-9]{1,2})?((\\s)?[Gg][Hh][Zz])?" +
                        "((\\s)?[/\\-~](\\s)?)" +
                        "([-+])?[1-9](\\.[0-9]{1,2})?((\\s)?[Gg][Hh][Zz])?")){
            throw new IllegalArgumentException("Clock speed format is #.##/#.## GHz");
        }

        double coreClock, boostClock;
        String formatted = clockSpeed.replaceAll("[ ]|[Gg][Hh][Zz]", "");

        if(clockSpeed.matches("([-+])?[1-9](\\.[0-9]{1,2})?((\\s)?[Gg][Hh][Zz])?" +
                "((\\s)?[/\\-~](\\s)?)" +
                "([-+])?[1-9](\\.[0-9]{1,2})?((\\s)?[Gg][Hh][Zz])?")) {

            String[] split = formatted.trim().split("[/\\-~]");

            coreClock = Double.parseDouble(split[0]);
            boostClock = Double.parseDouble(split[1]);
        } else {
            coreClock = boostClock = Double.parseDouble(formatted);
        }

        setCoreClock(coreClock);
        setBoostClock(boostClock);

        if(coreClock != boostClock) {
            this.clockSpeed.set(getCoreClock() + "/" + getBoostClock() + " GHz");
        } else {
            this.clockSpeed.set(getCoreClock() + " GHz");
        }

    }


    public String toCSV() {
        return Formatter.toCSV(
                getCOMPONENT_TYPE(),
                getManufacturer(),
                getModel(),
                getSocket(),
                getCoreCount(),
                getClockSpeed(),
                getPowerConsumption(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                "Socket: %s\n" +
                "Number of cores: %s\n" +
                "Clock speed: %s\n" +
                "Power usage: %s\n" +
                "Price: %s NOK",
                getCOMPONENT_TYPE(), getName(), getSocket(), getCoreCount(), getClockSpeed(), getPowerConsumption(), getPrice());
    }
}
