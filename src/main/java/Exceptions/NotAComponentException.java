package Exceptions;

public class NotAComponentException extends IllegalArgumentException {
    public NotAComponentException(String msg){
        super(msg);
    }
}
