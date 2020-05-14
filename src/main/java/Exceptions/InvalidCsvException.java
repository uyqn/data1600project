package Exceptions;

public class InvalidCsvException extends IllegalArgumentException {
    public InvalidCsvException(String msg){
        super(msg);
    }
}
