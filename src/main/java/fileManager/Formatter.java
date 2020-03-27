package fileManager;

public class Formatter {
    public static final String DELIMITER = ",";

    public static String toCSV(Object... args){
        StringBuilder format = new StringBuilder();

        for (Object object : args) {
            format.append("%s;");
        }

        return String.format(format.substring(0,format.length() - 1).replaceAll(";", DELIMITER), args);
    }
}
