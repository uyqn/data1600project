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
    public transient final static String COMPONENT_TYPE = "Motherboard";

    //These attributes must be serialized and specified in the constructor:
    private transient SimpleIntegerProperty ProcessorSpaces = new SimpleIntegerProperty();
    private transient SimpleIntegerProperty maxRamSize = new SimpleIntegerProperty();
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
    private transient SimpleObjectProperty<GPU> gpu = new SimpleObjectProperty<>();
    private transient SimpleObjectProperty<Memory[]> memories = new SimpleObjectProperty<>();

    public Motherboard(String[] csv) {
        super(csv[1], csv[2], Double.parseDouble(csv[9]));

        setSocket(csv[3]);
        setBussType(csv[4]);
        setRamSlots(Integer.parseInt(csv[5]));
        setMemoryTech(csv[6]);
        setMaxRamSize(Integer.parseInt(csv[7]));
        setFormFactor(csv[8]);
    }

    public Motherboard(String manufacturer,
                       String model,
                       String socket,
                       String bussType,
                       int ramSlots,
                       String memoryTech,
                       int maxRamSize,
                       String formFactor,
                       double price){
        super(manufacturer, model, price);

        setSocket(socket);
        setBussType(bussType);
        setRamSlots(ramSlots);
        setMemoryTech(memoryTech);
        setMaxRamSize(maxRamSize);
        setFormFactor(formFactor);
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

    public GPU getGpu(){
        return this.gpu.getValue();
    }

    public void setGpu(GPU gpu){
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
        int[] validRamSlots = {1, 2, 4, 6, 8, 12, 16};
        boolean invalid = true;
        for(int slots : validRamSlots){
            if(ramSlots == slots){
                invalid = false;
                break;
            }
        }

        if(invalid){
            throw new IllegalArgumentException("The ramslot entered is not a valid number");
        }

        this.ramSlots.set(ramSlots);
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

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(int speed) {

    }

    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public void setSize(double size) {

    }

    @Override
    public int getRefreshRate() {
        return 0;
    }

    @Override
    public void setRefreshRate(int refreshRate) {

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

    @Override
    public int getMemory() {
        return 0;
    }

    @Override
    public void setMemory(int memory) {

    }

    @Override
    public String getMemoryType() {
        return null;
    }

    @Override
    public void setMemoryType(String memoryType) {

    }

    @Override
    public int getBoostSpeed() {
        return 0;
    }

    @Override
    public void setBoostSpeed(int boostSpeed) {

    }

    @Override
    public boolean getTactile() {
        return false;
    }

    @Override
    public void setTactile(boolean tactile) {

    }

    @Override
    public int getRam() {
        return 0;
    }

    @Override
    public void setRam(int ram) {

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

    @Override
    public int getCoreRpm() {
        return 0;
    }

    @Override
    public void setCoreRpm(int coreRPM) {

    }

    @Override
    public int getMaxRpm() {
        return 0;
    }

    @Override
    public void setMaxRpm(int maxRPM) {

    }

    @Override
    public double getCoreNoise() {
        return 0;
    }

    @Override
    public void setCoreNoise(double coreNoise) {

    }

    @Override
    public double getMaxNoise() {
        return 0;
    }

    @Override
    public void setMaxNoise(double noise) {

    }

    @Override
    public double getPowerConsumption() {
        return 0;
    }

    @Override
    public void setPowerConsumption(double powerConsumption) {

    }


    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public int getProcessorSpaces() {
        return ProcessorSpaces.getValue();
    }

    public void setProcessorSpaces(int processorSpaces) {

        if(processorSpaces<1 || processorSpaces>6){
            throw new IllegalArgumentException("No motherboard can have less than 1 or more than 6 sockets");
        }
        else {
            this.ProcessorSpaces.set(processorSpaces);
        }
    }

    public int getMaxRamSize() {
        return maxRamSize.getValue();
    }

    public void setMaxRamSize(int maxRamSize) {

        if(maxRamSize % 4 != 0 || maxRamSize<4){
            throw new IllegalArgumentException("Must be a valid number of RAM-Size (16,32,64 etc.)");
        }
        this.maxRamSize.set(maxRamSize);
    }

    @Override
    public int getNumberButtons() {
        return 0;
    }

    @Override
    public void setNumberButtons(int numberButtons) {

    }

    @Override
    public int getDpi() {
        return 0;
    }

    @Override
    public void setDpi(int dpi) {

    }

    @Override
    public boolean isErgonomic() {
        return false;
    }

    @Override
    public void setErgonomic(boolean ergonomic) {

    }

    @Override
    public boolean isWireless() {
        return false;
    }

    @Override
    public void setWireless(boolean wireless) {

    }

    @Override
    public int getPowerCapacity() {
        return 0;
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {

    }

    @Override
    public String getNoise() {
        return null;
    }

    @Override
    public void setNoise(String noise) {

    }

    @Override
    public String getRpmString() {
        return null;
    }

    @Override
    public void setRpmString(String newValue) {

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

    @Override
    public int getCoreCount() {
        return 0;
    }

    @Override
    public void setCoreCount(int coreCount) {

    }

    @Override
    public double getCoreClock() {
        return 0;
    }

    @Override
    public void setCoreClock(double coreClock) {

    }

    @Override
    public double getBoostClock() {
        return 0;
    }

    @Override
    public void setBoostClock(double boostClock) {

    }

    public String toString(){
        return String.format("%s: %s\n" +
                        "Socket: %s\n" +
                        "Buss type: %s\n" +
                        "Ram slots: %s\n" +
                        "Memory Technology: %s\n" +
                        "Max memory size: %s GB\n" +
                        "Form factor: %s\n" +
                        "Price: %s NOK",
                getComponentType(), getName(), getSocket(), getBussType(), getRamSlots(), getMemoryTech(),
                getMaxRamSize(), getFormFactor(), String.format("%.2f",getPrice()));
    }

    @Override
    public String getSpec() {
        return String.format(
                        "Socket: %s\n" +
                        "Buss type: %s\n" +
                        "Ram slots: %s\n" +
                        "Memory Technology: %s\n" +
                        "Max memory size: %s GB\n" +
                        "Form factor: %s\n" +
                        "Price: %s NOK",
                getSocket(), getBussType(), getRamSlots(), getMemoryTech(),
                getMaxRamSize(), getFormFactor(), String.format("%.2f",getPrice()));
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(getComponentType(),
                getManufacturer(),
                getModel(),
                getSocket(),
                getBussType(),
                getRamSlots(),
                getMemoryTech(),
                getMaxRamSize(),
                getFormFactor(),
                getPrice());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());
        objectOutputStream.writeUTF(getSocket());
        objectOutputStream.writeUTF(getBussType());
        objectOutputStream.writeInt(getRamSlots());
        objectOutputStream.writeUTF(getMemoryTech());
        objectOutputStream.writeInt(getMaxRamSize());
        objectOutputStream.writeUTF(getFormFactor());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String manufacturer = objectInputStream.readUTF();
        String model = objectInputStream.readUTF();
        double price = objectInputStream.readDouble();
        String socket = objectInputStream.readUTF();
        String bussType = objectInputStream.readUTF();
        int ramSlots = objectInputStream.readInt();
        String memoryTech = objectInputStream.readUTF();
        int maxRamSize = objectInputStream.readInt();
        String formFactor = objectInputStream.readUTF();

        this.socket = new SimpleStringProperty();
        this.bussType = new SimpleStringProperty();
        this.ramSlots = new SimpleIntegerProperty();
        this.memoryTech = new SimpleStringProperty();
        this.maxRamSize = new SimpleIntegerProperty();
        this.formFactor = new SimpleStringProperty();

        super.setManufacturer(manufacturer);
        super.setModel(model);
        super.setPrice(price);

        setSocket(socket);
        setBussType(bussType);
        setRamSlots(ramSlots);
        setMemoryTech(memoryTech);
        setMaxRamSize(maxRamSize);
        setFormFactor(formFactor);
    }

    @Override
    public int getRpm() {
        return 0;
    }

    @Override
    public void setRpm(int rpm) {

    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public void setCapacity(int capacity) {

    }

    @Override
    public boolean compatible(Compatible component) {
        if(component.getClass() == CPU.class){
            return ((CPU) component).getSocket().equals(getSocket());
        }
        else if(component.getClass() == GPU.class) {
            return ((GPU) component).getBussVersion() <= getBussVersion() &&
                    ((GPU) component).getBussSlots() == getBussSlots();
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
