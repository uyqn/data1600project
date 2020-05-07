package components;

import java.util.ArrayList;

public class FormFactor {
    private String name;
    private Dimension dimension;
    private static ArrayList<FormFactor> list;

    public FormFactor(String name, double dim1, double dim2){
        this.name = name;
        this.dimension = new Dimension(dim1, dim2);
    }

    public static ArrayList<FormFactor> getList(){
        return list;
    }

    public static void add(FormFactor formFactor){
        list.add(formFactor);
    }

    public static FormFactor getFormFactor(String name){
        for(FormFactor formFactor : list){
            if(formFactor.getName().toLowerCase().equals(name.toLowerCase())){
                return formFactor;
            }
        }
        return null;
    }

    public static void remove(String name){
        if(getFormFactor(name) != null){
            list.remove(getFormFactor(name));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDimension() {
        return dimension.toString();
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public String toString() {
        return name + "(" + getDimension() + ")";
    }
}
