package components;

import fileManager.Formatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cabin extends Component implements Compatible {
    private transient static final String COMPONENT_TYPE = "Cabin";
    private transient SimpleStringProperty formFactor = new SimpleStringProperty();

    private transient SimpleObjectProperty<Motherboard> motherboard = new SimpleObjectProperty<>();

    public Cabin(String manufacturer, String model, String formFactor, double price) {
        super(manufacturer, model, price);
        setFormFactor(formFactor);
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

    public double getTotalPrice(){
        double totalPrice = getPrice();

        try{
            totalPrice += getMotherboard().getTotalPrice();
        } catch (NullPointerException e){
            totalPrice += 0;
        }

        return totalPrice;
    }

    public Motherboard getMotherboard(){
        return this.motherboard.getValue();
    }

    public void setMotherboard(Motherboard motherboard) {
        if(!compatible(motherboard)){
            throw new IllegalArgumentException(motherboard.getName() + " is not compatible with this cabin");
        }
        this.motherboard.set(motherboard);
    }

    public String getAllMemories(){
        StringBuilder showAllMemories = new StringBuilder();
        for(Memory memory : getMotherboard().getMemoryList()){
            if(memory != null) {
                showAllMemories.append(memory.getName()).append("\n");
            }
        }
        return showAllMemories.toString();
    }

    public Memory[] getMemoryList(){
        return getMotherboard().getMemoryList();
    }

    public Memory getMemory(int index){
        if(index > getMotherboard().getMemoryList().length - 1){
            throw new IllegalArgumentException(getMotherboard().getName() + "only has" + getMotherboard().getMemoryList().length + " " +
                    "slots");
        }

        return getMotherboard().getMemory(index);
    }

    public void setMemories(Memory... memories){
        getMotherboard().setMemories(memories);
    }

    public GraphicCard getGpu(){
        return getMotherboard().getGpu();
    }

    public void setGpu(GraphicCard gpu){
        getMotherboard().setGpu(gpu);
    }

    public CPU getCpu(){
        return getMotherboard().getCpu();
    }

    public void setCpu(CPU cpu){
        getMotherboard().setCpu(cpu);
    }

    @Override
    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    @Override
    public String toCSV() {
        return Formatter.toCSV(
                getComponentType(),
                getManufacturer(),
                getModel(),
                getFormFactor(),
                getPrice()
        );
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n" +
                        "Form factor: %s\n" +
                        "Price: %s NOK",
                COMPONENT_TYPE, getName(), formFactor, String.format("%.2f",getPrice()));
    }

    @Override
    public boolean compatible(Compatible motherboard) {
        if(motherboard.getClass() != Motherboard.class){
            throw new IllegalArgumentException("This component is not a connected Motherboard");
        }
        return ((Motherboard) motherboard).getFormFactor().equals(getFormFactor());
    }
}
