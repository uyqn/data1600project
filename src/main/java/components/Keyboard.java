package components;

public class Keyboard extends Component {


    public Keyboard(String manufacturer, String model, double price) {
        super(manufacturer, model, price);
    }

    @Override
    String toCSV() {
        return null;
    }
}
