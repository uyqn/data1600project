package components;

import components.Storage.Storage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import listManager.ListableList;
import listManager.ItemList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Computer extends ListableList<Component> implements Listable, ItemList<Component>, Serializable {
    private transient SimpleStringProperty name = new SimpleStringProperty();
    private transient ObservableList<Component> components = FXCollections.observableArrayList();

    private transient int availableRamSlots;

    public transient SimpleObjectProperty<CPU> cpu = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<GPU> gpu = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Motherboard> motherboard = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<ArrayList<Memory>> memories = new SimpleObjectProperty<>(new ArrayList<>());
    public transient SimpleObjectProperty<Storage> storage = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Cooler> cooler = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<PSU> psu = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Cabin> cabin = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Mouse> mouse = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Monitor> monitor = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Keyboard> keyboard = new SimpleObjectProperty<>();

    public Computer(){
        setName("Unnamed");
    }

    public Computer(String name){
        this.name = new SimpleStringProperty(name);
    }

    public Computer(String[] csv) {
        setName(csv[1]);
    }

    public CPU getCpu(){
        return this.cpu.getValue();
    }

    public String getCpuName(){
        return getCpu().getName();
    }

    public void setCpu(CPU cpu) {
        if(this.motherboard.getValue() != null){
            if(!this.motherboard.getValue().compatible(cpu)){
                throw new IllegalArgumentException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "CPU: " + cpu.getName());
            }
        }

        components.remove(getCpu());
        this.cpu.set(cpu);
        components.add(getCpu());
    }

    public GPU getGpu(){
        return this.gpu.getValue();
    }

    public String getGpuName(){
        return getGpu().getName();
    }

    public void setGpu(GPU gpu){
        if(this.motherboard.getValue() != null){
            if(!this.motherboard.getValue().compatible(gpu)){
                throw new IllegalArgumentException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "GPU: " + gpu.getName());
            }
        }

        components.remove(getGpu());
        this.gpu.set(gpu);
        components.add(getGpu());
    }

    public Motherboard getMotherboard(){
        return this.motherboard.getValue();
    }

    public String getMotherboardName(){
        return getMotherboard().getName();
    }

    public void setMotherboard(Motherboard motherboard){
        if(getCpu() != null){
            if(!getCpu().compatible(motherboard)){
                throw new IllegalArgumentException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with \n" +
                        "CPU: " + getCpu().getName());
            }
        }

        if(getGpu() != null){
            if(!getGpu().compatible(motherboard)){
                throw new IllegalArgumentException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with \n" +
                        "GPU: " + getGpu().getName());
            }
        }

        if(getCabin() != null){
            if(!getCabin().compatible(motherboard)){
                throw new IllegalArgumentException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with \n" +
                        "Cabin: " + getCabin().getName());
            }
        }

        if(getMemories().size() > 0){
            if(memories.getValue().size() > motherboard.getRamSlots()){
                throw new IllegalArgumentException("Motherboard: " + motherboard.getName() +
                        "\ndoes not have enough slots for memories to house all the memories added\n" +
                        motherboard.getName() + " has " + motherboard.getRamSlots() + " available slots \n" +
                        "This computer currently has " + memories.getValue().size() + " memories");
            }
            else {
                for (Memory memory : getMemories()) {
                    if (!motherboard.compatible(memory)) {
                        throw new IllegalArgumentException("Motherboard: " + motherboard.getName() +
                                "\nis not compatible with \n" +
                                "Memory: " + memory.getName());
                    }
                }
            }
        }

        components.remove(getMotherboard());
        this.motherboard.setValue(motherboard);
        components.add(getMotherboard());

        this.availableRamSlots = getMotherboard().getRamSlots() - getMemories().size();
    }

    public Memory getMemory(int index){
        return this.memories.getValue().get(index);
    }

    public String getMemoryName(int index){
        return getMemory(index).getName();
    }

    public void addMemory(Memory memory){
        if(this.motherboard.getValue() != null){
            if(!this.motherboard.getValue().compatible(memory)){
                throw new IllegalArgumentException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "RAM: " + memory.getName());
            }
            if(availableRamSlots <= 0) {
                throw new IllegalArgumentException("No available memory slots left on\n" +
                        "this motherboard: " + this.motherboard.getValue().getName());
            }
            else {
                this.memories.getValue().add(memory);
                components.add(memory);
                availableRamSlots--;
            }
        } else {
            this.memories.getValue().add(memory);
            components.add(memory);
        }
    }

    public void setMemories(ArrayList<Memory> memories){
        components.removeAll(memories);
        this.memories.set(memories);
        components.addAll(memories);
    }

    public ArrayList<Memory> getMemories(){
        return this.memories.getValue();
    }

    public String getMemoriesName(){
        StringBuilder memories = new StringBuilder();
        for(int i = 0 ; i < getMemories().size() ; i++){
            if(i == getMemories().size()-1){
                memories.append(getMemories().get(i).getName());
            } else {
                memories.append(getMemories().get(i).getName()).append("\n");
            }
        }
        return memories.toString();
    }

    public Storage getStorage(){
        return this.storage.getValue();
    }

    public String getStorageName(){
        return getStorage().getName();
    }

    public void setStorage(Storage storage){
        components.remove(getStorage());
        this.storage.set(storage);
        components.add(getStorage());
    }

    public Cooler getCooler(){
        return this.cooler.getValue();
    }

    public String getCoolerName(){
        return getCooler().getName();
    }

    public void setCooler(Cooler cooler){
        components.remove(getCooler());
        this.cooler.set(cooler);
        components.add(getCooler());
    }

    public PSU getPsu(){
        return this.psu.getValue();
    }

    public String getPsuName(){
        return getPsu().getName();
    }

    public void setPsu(PSU psu){
        components.remove(getPsu());
        this.psu.set(psu);
        components.add(getPsu());
    }

    public Cabin getCabin(){
        return this.cabin.getValue();
    }

    public String getCabinName(){
        return getCabin().getName();
    }

    public void setCabin(Cabin cabin){
        if(getMotherboard() != null){
            if(!getMotherboard().compatible(cabin)){
                throw new IllegalArgumentException("Cabin: " + cabin.getName() +
                        "\n is not compatible with \n" +
                        "Motherboard: " + getMotherboard().getName());
            }
        }
        components.remove(getCabin());
        this.cabin.set(cabin);
        components.add(getCabin());
    }

    public Mouse getMouse(){
        return this.mouse.getValue();
    }

    public String getMouseName(){
        return getMouse().getName();
    }

    public void setMouse(Mouse mouse){
        components.remove(getMouse());
        this.mouse.set(mouse);
        components.add(mouse);
    }

    public Monitor getMonitor(){
        return this.monitor.getValue();
    }

    public String getMonitorName(){
        return getMonitor().getName();
    }

    public void setMonitor(Monitor monitor){
        components.remove(getMonitor());
        this.monitor.set(monitor);
        components.add(getMonitor());
    }

    public Keyboard getKeyboard(){
        return this.keyboard.getValue();
    }

    public String getKeyboardName(){
        return getKeyboard().getName();
    }

    public ObservableList<Component> getComponents(){
        return components;
    }

    public void setKeyboard(Keyboard keyboard){
        components.remove(getKeyboard());
        this.keyboard.set(keyboard);
        components.add(getKeyboard());
    }

    public double getPrice() {
        double price = 0;

        for(Component component : components){
            price += component.getPrice();
        }

        return price;
    }

    public String getName() {
        return this.name.getValue();
    }

    public void setName(String name) {
        if(name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        this.name.set(name);
    }

    @Override
    public String toCSV(){
        StringBuilder csv = new StringBuilder();

        for(Component component : components){
            csv.append(component.toCSV());
        }

        return csv.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "Computer: %s\n" +
                "CPU: %s\n" +
                "GPU: %s\n" +
                "Motherboard: %s\n" +
                "Memories: %s\n" +
                "Storage:  %s\n" +
                "Cooler: %s \n" +
                "PSU: %s \n" +
                "Cabin: %s \n" +
                "Mouse: %s \n" +
                "Monitor: %s \n" +
                "Keyboard: %s \n" +
                "Price: %s",
                getName(),
                getCpuName(),
                getGpuName(),
                getMotherboardName(),
                getMemoriesName(),
                getStorageName(),
                getCoolerName(),
                getPsuName(),
                getCabinName(),
                getMouseName(),
                getMonitorName(),
                getKeyboardName(),
                String.format("%.2f",getPrice())
        );
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeUTF(getName());
        outputStream.writeObject(getCpu());
        outputStream.writeObject(getMotherboard());
        outputStream.writeObject(getMemories());
        outputStream.writeObject(getStorage());
        outputStream.writeObject(getCooler());
        outputStream.writeObject(getPsu());
        outputStream.writeObject(getCabin());
        outputStream.writeObject(getMouse());
        outputStream.writeObject(getMonitor());
        outputStream.writeObject(getKeyboard());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        String name = inputStream.readUTF();
        CPU cpu = (CPU) inputStream.readObject();
        Motherboard motherboard = (Motherboard) inputStream.readObject();
        ArrayList<Memory> memories = (ArrayList<Memory>) inputStream.readObject();
        Storage storage = (Storage) inputStream.readObject();
        Cooler cooler = (Cooler) inputStream.readObject();
        PSU psu = (PSU) inputStream.readObject();
        Cabin cabin = (Cabin) inputStream.readObject();
        Mouse mouse = (Mouse) inputStream.readObject();
        Monitor monitor = (Monitor) inputStream.readObject();
        Keyboard keyboard = (Keyboard) inputStream.readObject();

        this.name = new SimpleStringProperty();
        this.gpu = new SimpleObjectProperty<>();
        this.motherboard = new SimpleObjectProperty<>();
        this.memories = new SimpleObjectProperty<>();
        this.storage = new SimpleObjectProperty<>();
        this.cooler = new SimpleObjectProperty<>();
        this.psu = new SimpleObjectProperty<>();
        this.cabin = new SimpleObjectProperty<>();
        this.mouse = new SimpleObjectProperty<>();
        this.monitor = new SimpleObjectProperty<>();
        this.keyboard = new SimpleObjectProperty<>();

        setName(name);
        setCpu(cpu);
        setMotherboard(motherboard);
        setMemories(memories);
        setStorage(storage);
        setCooler(cooler);
        setPsu(psu);
        setCabin(cabin);
        setMouse(mouse);
        setMonitor(monitor);
        setKeyboard(keyboard);
    }
}