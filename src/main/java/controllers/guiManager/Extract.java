package controllers.guiManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
    public static ArrayList<Double> numbers(String str){
        Pattern pattern = Pattern.compile("[-+]?\\.\\d+|[-+]?\\d+(\\.?\\d+)");
        Matcher matcher = pattern.matcher(str);

        ArrayList<Double> extractedNumbers = new ArrayList<>();

        while(matcher.find()){
            extractedNumbers.add(Double.parseDouble(matcher.group()));
        }

        return extractedNumbers;
    }
}
