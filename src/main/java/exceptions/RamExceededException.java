package exceptions;

public class RamExceededException extends IllegalArgumentException {
    public RamExceededException(String msg){
        super(msg);
    }
}
