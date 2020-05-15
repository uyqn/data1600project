package exceptions;

public class InvalidCsvException extends IllegalArgumentException {
    public InvalidCsvException(String msg){
        super(msg);
    }
}
