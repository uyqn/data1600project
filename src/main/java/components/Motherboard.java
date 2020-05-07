package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Motherboard extends Component implements Serializable, Connectable {
    private transient final static SimpleStringProperty COMPONENT_TYPE = new SimpleStringProperty("Motherboard");

    private transient SimpleIntegerProperty ProcessorSpaces = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty MaxRamSize = new SimpleIntegerProperty();
    private transient SimpleStringProperty boostType = new SimpleStringProperty();
    private transient SimpleStringProperty socket = new SimpleStringProperty();

    private String[] sockets = {"AM1","AM2", "AM3","AM4", "BGA413", "BGA559","BGA1023","C32","FM1","FM2","G34"
                                ,"LGA771","LGA775","LGA1150","LGA1151","LGA1356","LGA1366","LGA2011","LGA2011-3","LGA2066",
                                "PGA988","sTR4"};

    public Motherboard(String[] csv // manufacturer, String model, int ProcessorSpaces, int MaxRamSize,
                       /*double price*/) {
        super(csv[1], csv[2], Double.parseDouble(csv[5]));

        setProcessorSpaces(Integer.parseInt(csv[3]));
        setMaxRamSize(Integer.parseInt(csv[4]));
    }

    public String getComponentType() {
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

    public String getBoostType() {
        return boostType.get();
    }

    public void setBoostType(String boostType) {
        if(boostType.equals("2")|| boostType.equals("3") || boostType.equals("4")) {
            this.boostType.set("PCIe "+boostType);
        }else{
            throw new IllegalArgumentException("Valid boosttypes include values 2-4");
        }
    }

    public String getSocket() {
        return socket.getValue();
    }


    public void setSocket(String socket) {

        for(int i = 0; i<sockets.length; i++){
            if(sockets[i].toLowerCase().equals(socket.toLowerCase())){
                this.socket.set(socket);
                break;
            }
        }

        if (this.socket == null){
            throw new IllegalArgumentException("This socket type is not valid");
        }
    }

    public String toString(){
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

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());

        objectOutputStream.writeInt(getProcessorSpaces());
        objectOutputStream.writeInt(getMaxRamSize());
        objectOutputStream.writeUTF(getBoostType());
        objectOutputStream.writeUTF(getSocket());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();

        int ProcessorSpaces = objectInputStream.readInt();
        int MaxRamSize = objectInputStream.readInt();
        String boostType = objectInputStream.readUTF();
        String socket = objectInputStream.readUTF();

        this.ProcessorSpaces = new SimpleIntegerProperty();
        this.MaxRamSize = new SimpleIntegerProperty();
        this.boostType = new SimpleStringProperty();
        this.socket = new SimpleStringProperty();

        setProcessorSpaces(ProcessorSpaces);
        setMaxRamSize(MaxRamSize);
        setBoostType(boostType);
        setSocket(socket);
    }

    @Override
    public boolean connect(Connectable item) {
        if(item.getClass() == CPU.class){
            return ((CPU) item).getSocket().equals(getSocket());
        }
        else{
            throw new IllegalArgumentException("Motherboards can only connect with the following components:\n" +
                    "- CPU \n " +
                    "- GraphicCards \n" +
                    "- MemoryCards \n" +
                    "- Cabins");
        }
    }
}
