package components;

import fileManager.FileOpenerCSV;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CPU extends Component{
    private static final SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("CPU");

    private SimpleStringProperty socket = new SimpleStringProperty();
    private SimpleIntegerProperty coreCount = new SimpleIntegerProperty();
    private SimpleDoubleProperty coreClock = new SimpleDoubleProperty();
    private SimpleDoubleProperty boostClock = new SimpleDoubleProperty();
    private SimpleIntegerProperty power = new SimpleIntegerProperty();

    private SimpleStringProperty clockSpeed = new SimpleStringProperty();

    public CPU(String manufacturer,
               String model,
               double price,
               String socket,
               int coreCount,
               String clockSpeed,
               int power) {
        super(manufacturer, model, price);

        setSocket(socket);
        setCoreCount(coreCount);
        setClockSpeed(clockSpeed);
        setPower(power);
    }

    public CPU(String manufacturer,
               String model,
               double price,
               String socket,
               int coreCount,
               double coreClock,
               double boostClock,
               int power) {
        super(manufacturer, model, price);

        setSocket(socket);
        setCoreCount(coreCount);
        setCoreClock(coreClock);
        setBoostClock(boostClock);
        setPower(power);

        this.clockSpeed.set(this.coreClock+"/"+this.boostClock+" GHz");
    }

    public String getCOMPONENT_TYPE() {
        return COMPONENT_TYPE.getValue();
    }

    public String getSocket() {
        return socket.getValue();
    }

    public void setSocket(String socket) {
        if(!socket.matches("[a-z]?[A-Z]{1,3}([1-9][+]|[1-9][0-9]{1,3}([-][1-9])?)")){
            throw new IllegalArgumentException("Format of socket type is invalid");
        }
        this.socket.set(socket);
    }

    public int getCoreCount() {
        return coreCount.getValue();
    }

    public void setCoreCount(int coreCount) {
        boolean invalid = true;
        for(int i = 0 ; i < 17 ; i += 2){
            if(coreCount == 1 || coreCount == i){
                invalid = false;
                break;
            }
        }

        if(invalid){
            throw new IllegalArgumentException("Number of cores must be 1, 2, 4, 8, 16, 32 or 64");
        }
        this.coreCount.set(coreCount);
    }

    public double getCoreClock() {
        return coreClock.getValue();
    }

    public void setCoreClock(double coreClock) {
        if(coreClock < 1){
            throw new IllegalArgumentException("Core clock frequency must be 1 or greater");
        }
        this.coreClock.set(coreClock);
    }

    public double getBoostClock() {
        return boostClock.getValue();
    }

    public void setBoostClock(double boostClock) {
        if(boostClock < coreClock.getValue()){
            throw new IllegalArgumentException("Overclocked speed must be greater than core clock frequency");
        }
        this.boostClock.set(boostClock);
    }

    public int getPower() {
        return power.getValue();
    }

    public void setPower(int power) {
        if(power <= 0){
            throw new IllegalArgumentException("Power requirement must be greater than 0");
        }
        this.power.set(power);
    }

    public String getClockSpeed() {
        return clockSpeed.getValue();
    }

    public void setClockSpeed(String clockSpeed) {
        if(!clockSpeed.matches("[1-9]\\.[0-9]{1,2}/[1-9]\\.[0-9]{1,2}((\\s)?GHz)?")){
            throw new IllegalArgumentException("Clock speed format is #.##/#.## GHz");
        }else {
            String formatted = clockSpeed.replaceAll("\\s|[A-Za-z]", "");
            String[] split = formatted.split("/");

            setCoreClock(Double.parseDouble(split[0]));
            setBoostClock(Double.parseDouble(split[1]));
            this.clockSpeed.set(getCoreClock() + "/" + getBoostClock() + " GHz");
        }
    }

    public String toCSV() {
        String formatted = String.format("%s;%s;%s;%s;%s;%s;%s;%s",
                getCOMPONENT_TYPE(),
                getManufacturer(),
                getModel(),
                getSocket(),
                getCoreCount(),
                getCoreClock(),
                getBoostClock(),
                getPower()
                );

        return formatted.replaceAll(";", FileOpenerCSV.DELIMITER);
    }

    @Override
    public String toString() {
        return getCOMPONENT_TYPE() + ": " + super.getName() + "\n" +
                "Socket: " + getSocket() + "\n" +
                "Number of cores: " + getCoreCount() + "\n" +
                "Clock speed: " + getClockSpeed() + "\n" +
                "Power usage: " + getPower() + "W";
    }
}
