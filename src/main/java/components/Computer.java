package components;

import Exceptions.NotAComponentException;
import Exceptions.NotCompatibleException;
import Exceptions.NotEnoughRamSlotsException;
import Exceptions.RamExceededException;
import components.Storage.HDD;
import components.Storage.SSD;
import components.Storage.Storage;
import javafx.beans.property.SimpleObjectProperty;
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

    public void addComponent(Component component){
        switch (component.getComponentType()){
            case CPU.COMPONENT_TYPE:
                setCpu((CPU) component); break;
            case GPU.COMPONENT_TYPE:
                setGpu((GPU) component); break;
            case Motherboard.COMPONENT_TYPE:
                setMotherboard((Motherboard) component); break;
            case Memory.COMPONENT_TYPE:
                addMemory((Memory) component); break;
            case SSD.COMPONENT_TYPE:
                setSsd((SSD) component); break;
            case HDD.COMPONENT_TYPE:
                setHdd((HDD) component); break;
            case Cooler.COMPONENT_TYPE:
                setCooler((Cooler) component); break;
            case PSU.COMPONENT_TYPE:
                setPsu((PSU) component); break;
            case Cabin.COMPONENT_TYPE:
                setCabin((Cabin) component); break;
            case Mouse.COMPONENT_TYPE:
                setMouse((Mouse) component); break;
            case Monitor.COMPONENT_TYPE:
                setMonitor((Monitor) component); break;
            case Keyboard.COMPONENT_TYPE:
                setKeyboard((Keyboard) component); break;
            default:
                throw new NotAComponentException(component + " is not a recognizable component");
        }
    }

    private void sortList(){
        components.clear();
        components.addAll(getCpu(), getGpu(), getMotherboard());
        components.addAll(getMemories());
        components.addAll(getSsd(), getHdd(), getCooler(), getPsu(), getCabin(), getMouse(), getMonitor(),
                getKeyboard());
        components.removeIf(Objects::isNull);
    }

    public TreeView<String> setTreeView(TreeView<String> treeView){
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> memories = new TreeItem<>("Memories");
        int memoryMembers = memories.getChildren().size();

        for(Component component : this.components){
            if(component.getClass() == Memory.class && getMemories().size() > 1){
                TreeItem<String> memory = new TreeItem<>(component.getName());
                TreeItem<String> memorySpec = new TreeItem<>(component.getSpec());
                memory.getChildren().add(memorySpec);
                memories.getChildren().add(memory);
                memoryMembers++;
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

        if(memoryMembers > 1){
            memories.setValue("Memories " + "(" + memoryMembers + ")");
        }

        treeView.setRoot(root);
        root.setExpanded(true);
        treeView.setShowRoot(false);

        return treeView;
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
                throw new NotCompatibleException("Motherboard: " + this.motherboard.getValue().getName() +
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
                        "\n is not compatible with" + " CPU: " + getCpu().getName() + "\n" +
                        "Because mismatch sockets: \n" +
                        getCpu().getName() + " socket: " + getCpu().getSocket() + "\n" +
                        motherboard.getName() + " socket: " + motherboard.getSocket());
            }
        }

        if(getGpu() != null){
            if(!getGpu().compatible(motherboard)){
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with" + " GPU: " + getGpu().getName() + "\n" +
                        "Because mismatch sockets: \n" +
                        getGpu().getName() + " buss slots: " + getGpu().getBussSlots() + "\n" +
                        motherboard.getName() + " buss slots: " + motherboard.getBussSlots());
            }
        }

        if(getCabin() != null){
            if(!getCabin().compatible(motherboard)){
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with" + " Cabin: " + getCabin().getName() + "\n" +
                        "Because mismatch sockets: \n" +
                        getCabin().getName() + " form factor: " + getCabin().getFormFactor() + "\n" +
                        motherboard.getName() + " form factor: " + motherboard.getFormFactor());
            }
        }

        if(getMemories().size() > 0){
            if(getMemories().size() > motherboard.getRamSlots()){
                throw new NotEnoughRamSlotsException("Motherboard: " + motherboard.getName() +
                        "\ndoes not have enough slots for memories to house all the memories added\n" +
                        motherboard.getName() + " has " + motherboard.getRamSlots() + " available slots \n" +
                        "This computer currently has " + getMemories().size() + " memories");
            }
            else {
                for (Memory memory : getMemories()) {
                    if (!motherboard.compatible(memory)) {
                        throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                                "\n is not compatible with" + " Memory: " + memory.getName() + "\n" +
                                "Because mismatch sockets: \n" +
                                memory.getName() + " technology: " + memory.getMemoryTech() + "\n" +
                                motherboard.getName() + " form factor: " + motherboard.getMemoryTech());
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

    public int getTotalRam(){
        int totalRam = 0;
        for(Memory memory : getMemories()){
            totalRam += memory.getRam();
        }
        return totalRam;
    }

    public String getTotalRamString(){
        return getTotalRam() + " GB";
    }

    public void addMemory(Memory memory){
        if(getMotherboard() != null){
            if(!getMotherboard().compatible(memory)){
                throw new NotCompatibleException("Motherboard: " + this.motherboard.getValue().getName() +
                        "\n is not compatible with \n" +
                        "RAM: " + memory.getName());
            }
            if(availableRamSlots <= 0) {
                throw new NotEnoughRamSlotsException("No available memory slots left on\n" +
                        "this motherboard: " + getMotherboard().getName());
            }
            if(getMotherboard().getMaxRamSize() < getTotalRam() + memory.getRam()){
                throw new RamExceededException(getMotherboard().getName() + "cannot support the attempted total input" +
                        " of memory\n " + getMotherboard().getName() + " Max RAM size: " + getMotherboard().getMaxRamSize() + "\n" +
                        "Attempted total input of RAM: " + (getTotalRam() + memory.getRam()));
            }

            getMemories().add(memory);
            sortList();
            availableRamSlots--;
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

    public int getTotalStorage(){
        int totalStorage = 0;
        if(getSsd() != null){
            totalStorage += getSsd().getCapacity();
        }
        if(getHdd() != null){
            totalStorage += getHdd().getCapacity();
        }
        return totalStorage;
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
                throw new NotCompatibleException("Motherboard: " + motherboard.getName() +
                        "\n is not compatible with" + " Cabin: " + getCabin().getName() + "\n" +
                        "Because mismatch sockets: \n" +
                        getMotherboard().getName() + " form factor: " + getMotherboard().getFormFactor() + "\n" +
                        cabin.getName() + " form factor: " + cabin.getFormFactor());
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

    public String getTotalStorageString(){
        return (getTotalStorage() >= 1000) ?
                String.format("%.1f", (double) getTotalStorage()/1000) + " TB" :
                getTotalStorage() + " GB";
    }

    public String getPriceString(){
        return String.format("%.2f", getPrice()) + " NOK";
    }

    @Override
    public String toCSV(){
        StringBuilder csv = new StringBuilder();
        csv.append("Computer").append("\n");
        for(Component component : components){
            csv.append(component.toCSV()).append("\n");
        }

        return csv.toString();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        return String.format(
                "CPU: %s\n" +
                "GPU: %s\n" +
                "Motherboard: %s\n" +
                "Memories: %s\n" +
                "SSD:  %s\n" +
                "HDD: %s\n" +
                "Cooler: %s \n" +
                "PSU: %s \n" +
                "Cabin: %s \n" +
                "Mouse: %s \n" +
                "Monitor: %s \n" +
                "Keyboard: %s \n" +
                "Price: %s",
                getCpuName(),
                getGpuName(),
                getMotherboardName(),
                getMemoriesName(),
                getSsdName(),
                getHddName(),
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