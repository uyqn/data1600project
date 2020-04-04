package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Motherboard extends Component {
    private transient final static SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("MB");

    private SimpleIntegerProperty ProcessorSpaces;
    private SimpleIntegerProperty MaxRamSize;
    private SimpleStringProperty SoundType;

    public Motherboard(String manufacturer, String model, int ProcessorSpaces, int MaxRamSize,
                       String SoundType, double price) {
        super(manufacturer, model, price);

        setProcessorSpaces(ProcessorSpaces);
        setMaxRamSize(MaxRamSize);
        setSoundType(SoundType);
    }

    public static String getComponentType() {
        return COMPONENT_TYPE.getValue();
    }

    public int getProcessorSpaces() {
        return ProcessorSpaces.get();
    }

    public void setProcessorSpaces(int processorSpaces) {
        this.ProcessorSpaces.set(processorSpaces);
    }

    public int getMaxRamSize() {
        return MaxRamSize.get();
    }

    public void setMaxRamSize(int maxRamSize) {
        this.MaxRamSize.set(maxRamSize);
    }

    public String getSoundType() {
        return SoundType.get();
    }

    public void setSoundType(String soundType) {
        this.SoundType.set(soundType);
    }

    public String ToString(){
        return "Motherboard: " + getName() + "Processor Spaces: " + getProcessorSpaces()
                + " Max Ram: " + getMaxRamSize() + " GB" + " SoundType: " + getSoundType();
    }

    @Override
    String toCSV() {
        return Formatter.toCSV(getComponentType(),
                getManufacturer(),
                getModel(),
                getProcessorSpaces(),
                getMaxRamSize(),
                getSoundType(),
                getPrice());
    }
}
