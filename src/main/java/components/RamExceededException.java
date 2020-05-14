package components;

public class RamExceededException extends IllegalArgumentException {
    public RamExceededException(String msg){
        super(msg);
    }
}
