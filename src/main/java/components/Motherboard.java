package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Motherboard extends Component {
    private transient final static SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Motherboard");

    private SimpleIntegerProperty ProcessorSpaces;
    private SimpleIntegerProperty MaxRamSize;

    public Motherboard(String manufacturer, String model, int ProcessorSpaces, int MaxRamSize,
                       double price) {
        super(manufacturer, model, price);

        setProcessorSpaces(ProcessorSpaces);
        setMaxRamSize(MaxRamSize);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public int getProcessorSpaces() {
        return ProcessorSpaces.getValue();
    }

    private void setProcessorSpaces(int processorSpaces) {

        if(processorSpaces<1 || processorSpaces>6){
            throw new IllegalArgumentException("No motherboard can have less than 1 or more than 6 sockets");
        }
        else {
            this.ProcessorSpaces.set(processorSpaces);
        }
    }

    public int getMaxRamSize() {
        return MaxRamSize.getValue();
    }

    private void setMaxRamSize(int maxRamSize) {

        if(maxRamSize % 4 != 0 || maxRamSize<4){
            throw new IllegalArgumentException("Must be a valid number of RAM-Size (16,32,64 etc.)");
        }
        this.MaxRamSize.set(maxRamSize);
    }


    public String ToString(){
        return getComponentType() + ": " + getName() + "\n" +
                "Processor Spaces: " + getProcessorSpaces() + "\n" +
                " Max Ram: " + getMaxRamSize() + " GB";
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(getComponentType(),
                getManufacturer(),
                getModel(),
                getProcessorSpaces(),
                getMaxRamSize(),
                getPrice());
    }
}
