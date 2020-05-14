package components;

import components.Storage.HDD;
import components.Storage.SSD;
import components.Storage.Storage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import listManager.ItemList;
import listManager.ListableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Computer extends ListableList<Component> implements Listable, ItemList<Component>, Serializable {
    private transient SimpleStringProperty name = new SimpleStringProperty();
    private transient ObservableList<Component> components = FXCollections.observableArrayList();

    private transient int availableRamSlots;

    public transient SimpleObjectProperty<CPU> cpu = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<GPU> gpu = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<Motherboard> motherboard = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<ArrayList<Memory>> memories = new SimpleObjectProperty<>(new ArrayList<>());
    public transient SimpleObjectProperty<SSD> ssd = new SimpleObjectProperty<>();
    public transient SimpleObjectProperty<HDD> hdd = new SimpleObjectProperty<>();
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

    private void sortList(){
        components.clear();
        components.addAll(getCpu(), getGpu(), getMotherboard());
        components.addAll(getMemories());
        components.addAll(getHdd(), getCooler(), getPsu(), getCabin(), getMouse(), getMonitor(), getKeyboard());
        components.removeIf(Objects::isNull);
    }

    public TreeView<String> setTreeView(TreeView<String> treeView){
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> memories = new TreeItem<>("Memories");

        for(Component component : this.components){
            if(component.getClass() == Memory.class && getMemories().size() > 1){
                TreeItem<String> memory = new TreeItem<>(component.getName());
                TreeItem<String> memorySpec = new TreeItem<>(component.getSpec());
                memory.getChildren().add(memorySpec);
                memories.getChildren().add(memory);
                if(!root.getChildren().contains(memories)){
                    root.getChildren().add(memories);
                }
            }
            else {
                TreeItem<String> comp = new TreeItem<>(component.getComponentType() + ": " + component.getName());
                TreeItem<String> compSpec = new TreeItem<>(component.getSpec());
                comp.getChildren().add(compSpec);
                root.getChildren().add(comp);
            }
        }

        treeView.setRoot(root);
        root.setExpanded(true);
        treeView.setShowRoot(false);

        return treeView;
    }

    public void add(Component component){
        categorize(component);
    }

    private void categorize(Component component){
        switch (component.getComponentType()) {
            case Cabin.COMPONENT_TYPE:
                setCabin((Cabin) component);
            case Cooler.COMPONENT_TYPE:
                setCooler((Cooler) component);
            case CPU.COMPONENT_TYPE:
                setCpu((CPU) component);
            case GPU.COMPONENT_TYPE:
                setGpu((GPU) component);
            case Keyboard.COMPONENT_TYPE:
                setKeyboard((Keyboard) component);
            case Memory.COMPONENT_TYPE:
                addMemory((Memory) component);
            case Monitor.COMPONENT_TYPE:
                setMonitor((Monitor) component);
            case Motherboard.COMPONENT_TYPE:
                setMotherboard((Motherboard) component);
            case Mouse.COMPONENT_TYPE:
                setMouse((Mouse) component);
            case PSU.COMPONENT_TYPE:
                setPsu((PSU) component);
            case SSD.COMPONENT_TYPE:
                setSsd((SSD) component);
            case HDD.COMPONENT_TYPE:
                setHdd((HDD) component);
            default:
                throw new IllegalArgumentException("Unable to recognize component");
        }
    }

    public CPU getCpu(){
        return this.cpu.getValue();
    }

    public String getCpuName(){
        return getCpu().getName();
    }

    public void setCpu(CPU cpu) {
        if(getMotherboard() != null){
            if(!getMotherboard().compatible(cpu)){
                throw new IllegalArgumentException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "CPU: " + cpu.getName());
            }
        }

        this.cpu.set(cpu);
        sortList();
    }

    public GPU getGpu(){
        return this.gpu.getValue();
    }

    public String getGpuName(){
        return getGpu().getName();
    }

    public void setGpu(GPU gpu){
        if(getMotherboard() != null){
            if(!getMotherboard().compatible(gpu)){
                throw new NotCompatibleException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "GPU: " + gpu.getName());
            }
        }

        this.gpu.set(gpu);
        sortList();
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
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with" + "CPU: " + getCpu().getName() + "\n" +
                        "Because mismatch sockets: \n" +
                        getCpu().getName() + " socket: " + getCpu().getSocket() + "\n" +
                        motherboard.getName() + "socket: " +motherboard.getSocket());
            }
        }

        if(getGpu() != null){
            if(!getGpu().compatible(motherboard)){
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with " + "GPU: " + getGpu().getName());
            }
        }

        if(getCabin() != null){
            if(!getCabin().compatible(motherboard)){
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with" +
                        "Cabin: " + getCabin().getName());
            }
        }

        if(getMemories().size() > 0){
            if(getMemories().size() > motherboard.getRamSlots()){
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\ndoes not have enough slots for memories to house all the memories added\n" +
                        motherboard.getName() + " has " + motherboard.getRamSlots() + " available slots \n" +
                        "This computer currently has " + getMemories().size() + " memories");
            }
            else {
                for (Memory memory : getMemories()) {
                    if (!motherboard.compatible(memory)) {
                        throw new IllegalArgumentException("Motherboard: " + motherboard.getName() +
                                "\nis not compatible with" +
                                "Memory: " + memory.getName());
                    }
                }
            }
        }

        this.motherboard.setValue(motherboard);
        sortList();

        this.availableRamSlots = getMotherboard().getRamSlots() - getMemories().size();
    }

    public Memory getMemory(int index){
        return this.memories.getValue().get(index);
    }

    public String getMemoryName(int index){
        return getMemory(index).getName();
    }

    public void addMemory(Memory memory){
        if(getMotherboard() != null){
            if(!getMotherboard().compatible(memory)){
                throw new NotCompatibleException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "RAM: " + memory.getName());
            }
            if(availableRamSlots <= 0) {
                throw new NotCompatibleException("No available memory slots left on\n" +
                        "this motherboard: " + getMotherboard().getName());
            }
            else {
                getMemories().add(memory);
                sortList();
                availableRamSlots--;
            }
        } else {
            getMemories().add(memory);
            sortList();
        }
    }

    public void setMemories(ArrayList<Memory> memories){
        this.memories.set(memories);
        sortList();
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

    public Storage getHdd(){
        return this.hdd.getValue();
    }

    public String getHddName(){
        return getHdd().getName();
    }

    public void setHdd(HDD hdd){
        this.hdd.set(hdd);
        sortList();
    }

    public SSD getSsd() {
        return ssd.getValue();
    }

    public String getSsdName(){
        return getSsd().getName();
    }

    public void setSsd(SSD ssd){
        this.ssd.set(ssd);
        sortList();
    }

    public Cooler getCooler(){
        return this.cooler.getValue();
    }

    public String getCoolerName(){
        return getCooler().getName();
    }

    public void setCooler(Cooler cooler){
        this.cooler.set(cooler);
        sortList();
    }

    public PSU getPsu(){
        return this.psu.getValue();
    }

    public String getPsuName(){
        return getPsu().getName();
    }

    public void setPsu(PSU psu){
        this.psu.set(psu);
        sortList();
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
                throw new NotCompatibleException("Cabin: " + cabin.getName() +
                        "\n is not compatible with \n" +
                        "Motherboard: " + getMotherboard().getName());
            }
        }
        this.cabin.set(cabin);
        sortList();
    }

    public Mouse getMouse(){
        return this.mouse.getValue();
    }

    public String getMouseName(){
        return getMouse().getName();
    }

    public void setMouse(Mouse mouse){
        this.mouse.set(mouse);
        sortList();
    }

    public Monitor getMonitor(){
        return this.monitor.getValue();
    }

    public String getMonitorName(){
        return getMonitor().getName();
    }

    public void setMonitor(Monitor monitor){
        this.monitor.set(monitor);
        sortList();
    }

    public Keyboard getKeyboard(){
        return this.keyboard.getValue();
    }

    public String getKeyboardName(){
        return getKeyboard().getName();
    }

    public ObservableList<Component> getComponents(){
        sortList();
        return components;
    }

    public void setKeyboard(Keyboard keyboard){
        this.keyboard.set(keyboard);
        sortList();
    }

    public double getPrice() {
        double price = 0;
        sortList();
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
                "HDD:  %s\n" +
                "SSD: %s\n" +
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
                getHddName(),
                getSsdName(),
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
        outputStream.writeObject(getSsd());
        outputStream.writeObject(getHdd());
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
        SSD ssd = (SSD) inputStream.readObject();
        HDD hdd = (HDD) inputStream.readObject();
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
        this.ssd = new SimpleObjectProperty<>();
        this.hdd = new SimpleObjectProperty<>();
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
        setSsd(ssd);
        setHdd(hdd);
        setCooler(cooler);
        setPsu(psu);
        setCabin(cabin);
        setMouse(mouse);
        setMonitor(monitor);
        setKeyboard(keyboard);
    }
}