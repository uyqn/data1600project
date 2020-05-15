package exceptions;

public class EmptyCsvException extends InvalidCsvException {
    public EmptyCsvException(String msg) {
        super(msg);
    }
}
