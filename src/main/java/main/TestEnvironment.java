package main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestEnvironment {
    public static void main(String[] args){
        String test = "2.77 GHz";

        Pattern pattern = Pattern.compile("[-+]?\\.\\d+|[-+]?\\d+(\\.?\\d+)");
        Matcher matcher = pattern.matcher(test);

        ArrayList<String> list = new ArrayList<>();

        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
