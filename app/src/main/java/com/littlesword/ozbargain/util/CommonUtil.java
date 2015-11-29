package com.littlesword.ozbargain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kongw1 on 29/11/15.
 */
public class CommonUtil {
    private static Pattern pattern = Pattern.compile("\\$[0-9]+(.[0-9][0-9]?)?");//for money in the string

    public static String applyColorToString(String input){
        Matcher matcher = pattern.matcher(input);

        // Find all matches
        while (matcher.find()) {
            // Get the matching string
            String match = matcher.group();
            input = input.replace(match, "<font color=\"#aa3311\">" + match + "</font>");
        }
        return input;
    }
}
