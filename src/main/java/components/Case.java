package components;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Case extends Component {
    public static final String COMPONENT_TYPE = "Case";
    private transient SimpleStringProperty formFactor = new SimpleStringProperty();
    private transient String[] formFactorList = {"ATX", "EATX", "flex ATX", "HPTX", "Micro ATX", "Mini ITX",
            "Thin Mini ITX", "Mini DTX", "SSI CEB", "SSI EEB", "XL ATX"};

    public Case(String manufacturer, String model, String maxFormFactor, double price) {
        super(manufacturer, model, price);

        setFormFactor(maxFormFactor);
    }

    public String getFormFactor() {
        return formFactor.getValue();
    }

    public void setFormFactor(String formFactor) {
        boolean validFormFactor = false;
        int formFactorIndex = 0;

        for(int i = 0 ; i < formFactorList.length ; i++){
            if(formFactor.toLowerCase().equals(formFactorList[i].toLowerCase())){
                validFormFactor = true;
                formFactorIndex = i;
                break;
            }
        }

        if(!validFormFactor){
            throw new IllegalArgumentException("Specified form factor is not available");
        }
        this.formFactor.set(formFactorList[formFactorIndex]);
    }

    @Override
    public String toCSV() {
        return null;
    }


    //Serialisering

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeUTF(getManufacturer());
        objectOutputStream.writeUTF(getModel());
        objectOutputStream.writeDouble(getPrice());


        objectOutputStream.writeUTF(getFormFactor());

    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException{
        String manufacturer= objectInputStream.readUTF();
        String model=objectInputStream.readUTF();
        double price=objectInputStream.readDouble();

        String formFactor= objectInputStream.readUTF();


        this.formFactor = new SimpleStringProperty();


        setFormFactor(formFactor);


    }


}
