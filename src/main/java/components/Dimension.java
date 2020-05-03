package components;

import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Dimension implements Serializable {
    private transient SimpleDoubleProperty width = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty depth = new SimpleDoubleProperty();
    private transient SimpleDoubleProperty height = new SimpleDoubleProperty();

    public Dimension(){}

    public Dimension(String dimension){
        setDimension(dimension);
    }

    public Dimension(double width, double depth){
        setWidth(width);
        setDepth(depth);
    }

    public Dimension(double width, double depth, double height){
        setWidth(width);
        setDepth(depth);
        setHeight(height);
    }

    public double getWidth() {
        return width.getValue();
    }

    public void setWidth(double width) {
        if(width < 0){
            throw new IllegalArgumentException("Width cannot be negative");
        }
        this.width.set(width);
    }

    public double getDepth() {
        return depth.getValue();
    }

    public void setDepth(double depth) {
        if(depth < 0){
            throw new IllegalArgumentException("Depth cannot be negative");
        }
        this.depth.set(depth);
    }

    public double getHeight() {
        return height.getValue();
    }

    public void setHeight(double height) {
        if(height < 0){
            throw new IllegalArgumentException("Height cannot be negative");
        }
        this.height.set(height);
    }

    public void setDimension(double width, double height){
        setWidth(width);
        setHeight(height);
    }

    public void setDimension(double width, double height, double depth){
        setWidth(width);
        setHeight(height);
        setDepth(depth);
    }

    public void setDimension(String dimension){
        String rDouble = "([-+])?[0-9]+(\\.[0-9]*)?";
        String rX = "[Xx\\s,*;/]+";

        if(!dimension.matches(rDouble+rX+rDouble + "|" + rDouble + rX + rDouble + rX + rDouble)){
            throw new IllegalArgumentException("Invalid format for specifying dimension correct format is #x# or " +
                    "#x#x#");
        }


        String[] split = dimension.replaceAll(rX, ";").split(";");

        setWidth(Double.parseDouble(split[0]));
        setDepth(Double.parseDouble(split[1]));

        if(split.length == 3){
            setHeight(Double.parseDouble(split[2]));
        }
    }

    public double getArea(){
        return getWidth()*getDepth();
    }

    public double getVolume(){
        return getWidth()*getDepth()*getHeight();
    }

    @Override
    public String toString() {
        return (getHeight() == 0) ?
                getWidth() + " x " + getDepth() :
                getWidth() + " x " + getDepth() + " x " + getHeight();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        //Super component
        objectOutputStream.writeDouble(getWidth());
        objectOutputStream.writeDouble(getDepth());
        objectOutputStream.writeDouble(getHeight());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        double width = objectInputStream.readDouble();
        double depth = objectInputStream.readDouble();
        double height = objectInputStream.readDouble();

        this.width = new SimpleDoubleProperty();
        this.depth = new SimpleDoubleProperty();
        this.height = new SimpleDoubleProperty();

        setWidth(width);
        setDepth(depth);
        setHeight(height);
    }
}
