package components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Computer {

    public SimpleStringProperty Name;

    public SimpleDoubleProperty Price;
    public ArrayList<Component> Content = new ArrayList<Component>();

    public Computer(Double price, ArrayList<Component> list){

    }

    public Double getPrice(){
        return Price.get();
    }

    public void setPrice(Double price){
        this.Price.set(price);
    }

    public ArrayList<Component> getContent() {
        return Content;
    }

    public void setContent(ArrayList<Component> content) {
        Content = content;
    }

    public String getName() {
        return Name.get();
    }


    public void setName(String name) {
        this.Name.set(name);
    }
}
