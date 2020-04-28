package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Memory extends Component{

    private static final transient SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Memory");

    private transient SimpleIntegerProperty RAM=new SimpleIntegerProperty();
    private transient SimpleStringProperty speed=new SimpleStringProperty();


    public Memory(String manufacturer, String model, double price, int RAM, String speed){

        super(manufacturer, model, price);

        setRAM(RAM);
        setSpeed(speed);



    }

    public String getCOMPONENT_TYPE(){return COMPONENT_TYPE.getValue();}

    public int getRAM(){ return RAM.getValue();}

    public void setRAM(int RAM){

        boolean invalid=true;

        for (int i=2; i<=64; i=i*2){
            if(RAM==i){
                invalid=false;
                break;
            }
        }

        if(invalid){
            throw new IllegalArgumentException("RAM must be one of these: \n 2, 4, 8, 16, 32, 64");
        }
        this.RAM.set(RAM);
    }

    public String getSpeed(){ return speed.getValue();}

    public void setSpeed(String speed){
        if(speed.matches("/")){
            throw new IllegalArgumentException("Speed must be valid. \n(for example: DDR4-2400\n" +
                    "DDR4-2666\n" +
                    "DDR4-3000\n" +
                    "DDR4-3200)"
                    );
        }
        this.speed.set(speed);

    }

    @Override
    public String toCSV(){
        return Formatter.toCSV(getCOMPONENT_TYPE(),getManufacturer(),getModel(),getPrice(),getRAM(),getSpeed());
    }

    @Override
    public String toString(){
        return String.format("%s: %s\n"+
                "RAM: %s GB\n"+
                "Speed: %s\n"+
                "Price: %s", getCOMPONENT_TYPE(), getName(), getRAM(),getSpeed(),getPrice());
    }


}
