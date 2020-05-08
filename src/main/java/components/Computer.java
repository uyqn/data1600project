package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import listManager.ComponentList;

import java.util.ArrayList;
import java.util.Arrays;

public class Computer extends ComponentList<Component> {

    public SimpleStringProperty name;
    public SimpleDoubleProperty price;
    ArrayList<Component> listOfComponents;

    //Step 1: Pick CPU
    public transient SimpleObjectProperty<CPU> cpu = new SimpleObjectProperty<>();

    //Step 2: Pick GPU
    public transient SimpleObjectProperty<GraphicCard> gpu = new SimpleObjectProperty<>();

    //Step 3: Pick Motherboard
    public transient SimpleObjectProperty<Motherboard> mb = new SimpleObjectProperty<>();

    //Step 4: Select one or multiple of RAMs



    public Computer(String name){
        this.name = new SimpleStringProperty(name);
    }

    public Computer(String[] csv) {
        setName(csv[1]);
    }

    public void setCpu(CPU cpu) {
        if(this.mb.getValue() != null){
            if(!this.mb.getValue().compatible(cpu)){
                throw new IllegalArgumentException("Motherboard: " + this.mb.getValue().getName() +
                        "\n is not compatible with \n" +
                        "CPU: " + cpu.getName());
            }
        }
        this.cpu.set(cpu);
    }

    public void setGpu(GraphicCard gpu){
        if(this.mb.getValue() != null){
            if(!this.mb.getValue().compatible(gpu)){
                throw new IllegalArgumentException("Motherboard: " + this.mb.getValue().getName() +
                        "\n is not compatible with \n" +
                        "GPU: " + gpu.getName());
            }
        }
        this.gpu.set(gpu);
    }

    public void setMotherboard(Motherboard motherboard){
        if(this.cpu.getValue() != null){
            if(!this.cpu.getValue().compatible(motherboard)){
                throw new IllegalArgumentException("Motherboard: " + this.mb.getValue().getName() +
                        "\n is not compatible with \n" +
                        "CPU: " + cpu.getName());
            }
        }

        if(this.gpu.getValue() != null){
            if(!this.gpu.getValue().compatible(motherboard)){
                throw new IllegalArgumentException("Motherboard: " + this.mb.getValue().getName() +
                        "\n is not compatible with \n" +
                        "GPU: " + gpu.getName());
            }
        }

        this.mb.setValue(motherboard);
    }

    public Double getPrice() {
        return this.price.getValue();
    }

    public ArrayList<Component> getContent() {
        return listOfComponents;
    }

    public void setContent(Component... components) {
        listOfComponents.addAll(Arrays.asList(components));
    }

    public String getName() {
        return this.name.getValue();
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public String toCSV() {
        StringBuilder csv = new StringBuilder(getName() + ",");
        for(Component component : listOfComponents){
            csv.append(component.toCSV()).append("\n");
        }
        csv.append(",").append(getPrice());

        return csv.toString();
    }
}