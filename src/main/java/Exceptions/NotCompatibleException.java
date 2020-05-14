package Exceptions;

public class NotCompatibleException extends IllegalArgumentException {
    public NotCompatibleException(String msg){
        super(msg);
    }
}
