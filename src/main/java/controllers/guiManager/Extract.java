package controllers.guiManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
    public static ArrayList<String> strings(String str){
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(str);

        ArrayList<String> extractedStrings = new ArrayList<>();

        while(matcher.find()){
            extractedStrings.add(matcher.group());
        }

        return extractedStrings;
    }

    public static ArrayList<Double> doubles(String str){
        Pattern pattern = Pattern.compile("[-+]?\\.\\d+|[-+]?\\d+(\\.?\\d+)?");
        Matcher matcher = pattern.matcher(str);

        ArrayList<Double> extractedNumbers = new ArrayList<>();

        while(matcher.find()){
            extractedNumbers.add(Double.parseDouble(matcher.group()));
        }

        return extractedNumbers;
    }

    public static ArrayList<Integer> ints(String str){
        Pattern pattern = Pattern.compile("[-+]?\\d+((\\.)\\d{1,2})?");
        Matcher matcher = pattern.matcher(str);

        ArrayList<Integer> extractedNumbers = new ArrayList<>();

        while(matcher.find()){
            extractedNumbers.add((int) Double.parseDouble(matcher.group()));
        }

        return extractedNumbers;
    }
}
