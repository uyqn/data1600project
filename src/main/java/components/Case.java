package components;

import javafx.beans.property.SimpleStringProperty;

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
}
