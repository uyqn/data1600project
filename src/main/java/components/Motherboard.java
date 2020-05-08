package components;

import controllers.guiManager.Extract;
import fileManager.Formatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Motherboard extends Component implements Serializable, Compatible {
    private transient final static String COMPONENT_TYPE = "Motherboard";

    //These attributes must be serialized and specified in the constructor:
    private transient SimpleIntegerProperty ProcessorSpaces = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty MaxRamSize = new SimpleIntegerProperty();
    private transient SimpleStringProperty boostType = new SimpleStringProperty();
    private transient SimpleStringProperty socket = new SimpleStringProperty();
    private transient SimpleStringProperty bussType = new SimpleStringProperty();
    private transient SimpleStringProperty formFactor = new SimpleStringProperty();
    private transient SimpleStringProperty memoryTech = new SimpleStringProperty();
    private transient SimpleIntegerProperty ramSlots = new SimpleIntegerProperty();

    //These attributes should not be serialized or specified in the constructor:
    private transient Integer bussSlots;
    private transient double bussVersion;
    private transient int techNumber;
    private transient int availableRamSlots;

    //These attributes are meant to help out with building the Computer class:
    private transient SimpleObjectProperty<CPU> cpu = new SimpleObjectProperty<>();
    private transient SimpleObjectProperty<GraphicCard> gpu = new SimpleObjectProperty<>();
    private transient SimpleObjectProperty<Memory[]> memories = new SimpleObjectProperty<>();

    public Motherboard(String[] csv // manufacturer, String model, int ProcessorSpaces, int MaxRamSize,
                       /*double price*/) {
        super(csv[1], csv[2], Double.parseDouble(csv[5]));

        setProcessorSpaces(Integer.parseInt(csv[3]));
        setMaxRamSize(Integer.parseInt(csv[4]));
    }

    public Motherboard(String manufacturer,
                       String model,
                       String socket,
                       String bussType,
                       int maxRamSize,
                       String memoryTech,
                       int ramSlots,
                       String formFactor,
                       double price){
        super(manufacturer, model, price);

        setMaxRamSize(maxRamSize);
        setSocket(socket);
        setBussType(bussType);
        setMemoryTech(memoryTech);
        setFormFactor(formFactor);
        setRamSlots(ramSlots);
    }

    public double getTotalPrice(){
        double totalPrice = getPrice();

        try{
            totalPrice += getCpu().getPrice();
        } catch (NullPointerException e){
            totalPrice += 0;
        }

        try{
            totalPrice += getCpu().getPrice();
        } catch (NullPointerException e){
            totalPrice += 0;
        }

        for(Memory memory : getMemoryList()){
            if(memory != null){
                totalPrice += memory.getPrice();
            }
        }

        return totalPrice;
    }

    public String getAllMemories(){
        StringBuilder showAllMemories = new StringBuilder();
        for(Memory memory : this.memories.getValue()){
            if(memory != null) {
                showAllMemories.append(memory.getName()).append("\n");
            }
        }
        return showAllMemories.toString();
    }

    public Memory[] getMemoryList(){
        return this.memories.getValue();
    }

    public Memory getMemory(int index){
        if(index > this.memories.getValue().length - 1){
            throw new IllegalArgumentException("This motherboard only has " + this.memories.getValue().length + " " +
                    "slots");
        }

        return this.memories.getValue()[index];
    }

    public void setMemories(Memory... memories){
        if(memories.length > availableRamSlots){
            throw new IllegalArgumentException("The total number of memories exceeds the number of available ramslots");
        }

        for(int i = this.memories.getValue().length - availableRamSlots ; i < memories.length ; i++){
            if(compatible(memories[i])) {
                this.memories.getValue()[i] = memories[i];
            }
            else {
                throw new IllegalArgumentException(memories[i] + " is not compatible with this Motherboard");
            }
        }

        availableRamSlots -= memories.length;
    }

    public GraphicCard getGpu(){
        return this.gpu.getValue();
    }

    public void setGpu(GraphicCard gpu){
        if(!compatible(gpu)){
            throw new IllegalArgumentException(gpu.getName() + " is not compatible with this Motherboard");
        }
        this.gpu.set(gpu);
    }

    public CPU getCpu(){
        return this.cpu.getValue();
    }

    public void setCpu(CPU cpu){
        if(!compatible(cpu)){
            throw new IllegalArgumentException(cpu.getName() + " is not compatible with this Motherboard");
        }
        this.cpu.set(cpu);
    }

    public int getRamSlots(){
        return ramSlots.getValue();
    }

    public void setRamSlots(int ramSlots) {
        if(ramSlots < 1 || ramSlots > 4){
            throw new IllegalArgumentException("The number of ram slots must be 1,2 or 4");
        }
        if(ramSlots == 3){
            throw new IllegalArgumentException("The number of ram slots must be 1,2 or 4");
        }

        this.ramSlots.set(ramSlots);
        this.memories.set(new Memory[ramSlots]);
    }

    public String getMemoryTech(){ return memoryTech.getValue();}

    public void setMemoryTech(String memoryTech){
        if(memoryTech.matches("[/\\s-]")){
            throw new IllegalArgumentException("Speed technology must be valid. \n(for example: DDR\n" +
                    "DDR2\n" +
                    "DDR3\n" +
                    "DDR4)"
            );
        }

        if(Extract.ints(memoryTech).size() == 0){
            setTechNumber(1);
        }
        else if(Extract.ints(memoryTech).get(0) == 1){
            setTechNumber(1);
        }
        else {
            setTechNumber(Extract.ints(memoryTech).get(0));
        }

        this.memoryTech.set("DDR"+ getTechNumber());
    }

    public int getTechNumber() {
        return techNumber;
    }

    private void setTechNumber(Integer integer) {
        if(integer < 1 || integer > 4){
            throw new IllegalArgumentException("Memory technology number must either be empty or between 1 and 4");
        }
        this.techNumber = integer;
    }

    public String getBussType(){
        return bussType.getValue();
    }

    public void setBussType(String bussType) {
        if(!bussType.matches(
                "[Pp][Cc][Ii](\\s|-)?([Ee]|" +
                        "[Ee][Xx][Pp][Rr][Ee][Ss][Ss])" +
                        "(\\s)?\\d(\\.\\d)(\\s)?[Xx ](\\s?)\\d{1,2}")){
            throw new IllegalArgumentException("Busstype not recognized");
        }

        setBussVersion(Extract.doubles(bussType).get(0));
        setBussSlots(Extract.ints(bussType).get(1));

        this.bussType.set("PCIe " + getBussVersion() + "x" + getBussSlots());
    }

    public int getBussSlots() {
        return bussSlots;
    }

    public double getBussVersion() {
        return bussVersion;
    }

    private void setBussSlots(Integer integer) {
        boolean valid = false;

        for(int i = 1 ; i < 64 ; i *= 2){
            if (integer == i) {
                valid = true;
                break;
            }
        }

        if(!valid){
            throw new IllegalArgumentException("Buss slots must be between 1 and 32 and a multiple of 2");
        }

        this.bussSlots = integer;
    }

    private void setBussVersion(Double aDouble) {
        if(aDouble < 1.0 || aDouble > 6.0){
            throw new IllegalArgumentException("Buss version must be between 1.0 and 6.0");
        }
        this.bussVersion = aDouble;
    }

    public String getFormFactor() {
        return formFactor.getValue();
    }

    public void setFormFactor(String formFactor) {
        if(!formFactor.matches("([A-Za-z]{4}\\s)?(" +
                "([Ee][Xx][Tt][Ee][Nn][Dd][Ee][Dd]|[A-Za-z]{1,5})(\\s|-)?)?[A-Za-z]{2,4}")){
            throw new IllegalArgumentException("The format for form factor is not recognizable.");
        }

        this.formFactor.set(formFactor);
    }


    public String getComponentType() {
        return COMPONENT_TYPE;
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
        return boostType.getValue();
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
        if(!socket.matches("[a-z]?[A-Z]{1,3}(\\s)?([1-9][+]|[1-9][0-9]{0,3}([-][1-9])?)")){
            throw new IllegalArgumentException("Format of socket type is invalid");
        }
        this.socket.set(socket);
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
                getSocket(),
                getMemoryTech(),
                getRamSlots(),
                getFormFactor(),
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
    public boolean compatible(Compatible component) {
        if(component.getClass() == CPU.class){
            return ((CPU) component).getSocket().equals(getSocket());
        }
        else if(component.getClass() == GraphicCard.class) {
            return ((GraphicCard) component).getBussVersion() <= getBussVersion() &&
                    ((GraphicCard) component).getBussSlots() == getBussSlots();
        }
        else if(component.getClass() == Cabin.class) {
            return ((Cabin) component).getFormFactor().equals(getFormFactor());
        }
        else if(component.getClass() == Memory.class) {
            return (((Memory) component).getTechNumber() == getTechNumber());
        }
        else{
            throw new IllegalArgumentException(
                    "Motherboards can only connect with the following components:\n" +
                    "- CPU \n " +
                    "- GraphicCards \n" +
                    "- MemoryCards \n" +
                    "- Cabins"
            );
        }
    }
}
