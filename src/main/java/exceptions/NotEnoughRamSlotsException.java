package exceptions;

public class NotEnoughRamSlotsException extends IllegalArgumentException {
    public NotEnoughRamSlotsException(String msg){
        super(msg);
    }
}
