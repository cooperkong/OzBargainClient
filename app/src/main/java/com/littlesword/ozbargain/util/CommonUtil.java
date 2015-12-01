package com.littlesword.ozbargain.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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


    public static String readTextFile(InputStream inputStream) { // getting XML

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
