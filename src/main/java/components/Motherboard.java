package components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Motherboard extends Component {

    private SimpleIntegerProperty ProcessorSpaces;
    private SimpleIntegerProperty MaxRamSize;
    private SimpleStringProperty SoundType;

    public Motherboard(String manufacturer, String model, double price, int ProcessorSpaces, int MaxRamSize,
                       String SoundType) {
        super(manufacturer, model, price);

        setProcessorSpaces(ProcessorSpaces);
        setMaxRamSize(MaxRamSize);
        setSoundType(SoundType);
    }

    public int getProcessorSpaces() {
        return ProcessorSpaces.get();
    }

    public SimpleIntegerProperty processorSpacesProperty() {
        return ProcessorSpaces;
    }

    public void setProcessorSpaces(int processorSpaces) {
        this.ProcessorSpaces.set(processorSpaces);
    }

    public int getMaxRamSize() {
        return MaxRamSize.get();
    }

    public SimpleIntegerProperty maxRamSizeProperty() {
        return MaxRamSize;
    }

    public void setMaxRamSize(int maxRamSize) {
        this.MaxRamSize.set(maxRamSize);
    }

    public String getSoundType() {
        return SoundType.get();
    }

    public SimpleStringProperty soundTypeProperty() {
        return SoundType;
    }

    public void setSoundType(String soundType) {
        this.SoundType.set(soundType);
    }

    public String ToString(){
        return "Motherboard: " + getName() + "Processor Spaces: " + getProcessorSpaces()
                + " Max Ram: " + getMaxRamSize() + " GB" + " SoundType: " + getSoundType();
    }
}
