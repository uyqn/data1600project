package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Memory extends Component{

    private static final String COMPONENT_TYPE = "Memory";

    private transient SimpleIntegerProperty RAM=new SimpleIntegerProperty();
    private transient SimpleStringProperty speedTech=new SimpleStringProperty();
    private transient SimpleIntegerProperty speed=new SimpleIntegerProperty();


    public Memory(String manufacturer, String model, double price, int RAM, String speedTech, int speed){

        super(manufacturer, model, price);

        setRAM(RAM);
        setSpeedTech(speedTech);
        setSpeed(speed);



    }

    public String getCOMPONENT_TYPE(){return COMPONENT_TYPE;}

    public int getRAM(){ return RAM.getValue();}

    public void setRAM(int RAM){

        boolean invalid=true;

        for (int i=2; i<=256; i=i*2){
            if(RAM==i){
                invalid=false;
                break;
            }
        }

        if(invalid){
            throw new IllegalArgumentException("RAM must be one of these: \n 2GB, 4GB, 8GB, 16GB, 32GB, 64GB, 128GB");
        }
        this.RAM.set(RAM);
    }

    public String getSpeedTech(){ return speedTech.getValue();}

    public void setSpeedTech(String speedTech){
        if(speedTech.matches("/")){
            throw new IllegalArgumentException("Speed technology must be valid. \n(for example: DDR4-2400\n" +
                    "DDR4-2666\n" +
                    "DDR4-3000\n" +
                    "DDR4-3200)"
                    );
        }
        this.speedTech.set(speedTech);

    }

    public int getSpeed(){ return speed.getValue();}

    public void setSpeed(int speed){
        if(speed<1000||speed>5000){
            throw new IllegalArgumentException("Speed must be between 1000 MHz and 5000MHz");
        }
        this.speed.set(speed);
    }


    @Override
    public String toCSV(){
        return Formatter.toCSV(getCOMPONENT_TYPE(),getManufacturer(),getModel(),getPrice(),getRAM(),getSpeedTech(), getSpeed());
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "RAM: %s GB\n"+
                "Speed: %sMHz %s\n"+
                "Price: %s", getCOMPONENT_TYPE(), getName(), getRAM(),getSpeed(), getSpeedTech(), getPrice());
    }


}