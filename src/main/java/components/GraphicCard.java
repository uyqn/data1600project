package components;
//Graphics Card
import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GraphicCard extends Component {
    private static final transient SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Graphic Card");

    private transient SimpleIntegerProperty memory = new SimpleIntegerProperty();
    private transient SimpleStringProperty memoryType=new SimpleStringProperty();
    private transient SimpleDoubleProperty baseClock = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty boostClock = new SimpleDoubleProperty();


    public GraphicCard(String manufacturer, String model, double price, int memory, String memoryType, double baseClock, double boostClock){

        super(manufacturer,model,price);

        setMemory(memory);
        setMemoryType(memoryType);
        setBaseClock(baseClock);
        setBoostClock(boostClock);


    }

    public GraphicCard(String manufacturer, String model, double price, int memory, String memoryType, String clockSpeed){


        super(manufacturer,model,price);

        setMemory(memory);
        setMemoryType(memoryType);
        setClockSpeed(clockSpeed);
    }

    public String getCOMPONENT_TYPE(){return COMPONENT_TYPE.getValue();}


    public double getBaseClock() {
        return baseClock.getValue();
    }

    public void setBaseClock(double baseClock) {
        if(baseClock < 1.1 || baseClock > 5.0){
            throw new IllegalArgumentException("Base clock frequency should be between or equal to 1.1 and "
                    + getBoostClock() + " GHz");
        }
        if(baseClock > getBoostClock()){
            setBoostClock(baseClock);
        }

        this.baseClock.set(baseClock);
    }

    public double getBoostClock() {
        return boostClock.getValue();
    }

    public void setBoostClock(double boostClock) {
        if(boostClock < 1.1 || boostClock > 5.0){
            throw new IllegalArgumentException("Overclocked speed should be between or equal to " + getBaseClock() +
                    " and 5.0 GHz");
        }
        if(boostClock < getBaseClock()){
            setBaseClock(boostClock);
        }

        this.boostClock.set(boostClock);
    }

    public int getMemory() {
        return memory.getValue();
    }

    public void setMemory(int memory) {
        if(memory<0 || memory>64){
            throw new IllegalArgumentException("Memory must be a whole number beteen 1 and 64");
        }
        this.memory.set(memory);
    }

    public String getMemoryType(){ return memoryType.getValue();}

    public void setMemoryType(String memoryType){

        /*if(!memoryType.matches("[A-Z]")){
            throw new IllegalArgumentException("Memory type format is invalid");
        }*/
        this.memoryType.set(memoryType);
    }

    public String getClockSpeed() {
        return (getBaseClock() != getBoostClock()) ?
                getBaseClock() + "/" + getBoostClock():
                String.valueOf(getBaseClock());
    }

    public void setClockSpeed(double core, double boost){
        if(core > boost){
            throw new IllegalArgumentException("Core clock speed should be greater than the boost clock speed");
        }

        setBaseClock(core);
        setBoostClock(boost);
    }

    public void setClockSpeed(String clockSpeed) {
        if(clockSpeed.matches("/")){
            throw new IllegalArgumentException("Core clock speed and boost clock speed are empty\n" +
                    "One of the fields must be filled");
        }

        if(Extract.doubles(clockSpeed).size() == 1){
            setBaseClock(Extract.doubles(clockSpeed).get(0));
            setBoostClock(Extract.doubles(clockSpeed).get(0));
        }
        else {
            setBaseClock(
                    Math.min(Extract.doubles(clockSpeed).get(0),
                            Extract.doubles(clockSpeed).get(Extract.doubles(clockSpeed).size() - 1))
            );
            setBoostClock(
                    Math.max(Extract.doubles(clockSpeed).get(0),
                            Extract.doubles(clockSpeed).get(Extract.doubles(clockSpeed).size() - 1))
            );
        }
    }

    @Override
    public String toCSV(){
        return Formatter.toCSV(
                getCOMPONENT_TYPE(),
                getManufacturer(),
                getModel(),
                getMemory(),
                getMemoryType(),
                getClockSpeed()
        );
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "Memory: %s GB %s\n"+
                "Clock Speed: %s GHz\n"
                +"Price: %s NOK\n",
                getCOMPONENT_TYPE(), getName(), getMemory(), getMemoryType(), getClockSpeed(), getPrice());
    }
}


