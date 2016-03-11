package com.littlesword.ozbargain.util;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.littlesword.ozbargain.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kongw1 on 29/11/15.
 */
public final class CommonUtil {
    private final static Pattern pattern = Pattern.compile("\\$[0-9]+(.[0-9][0-9]?)?");//for money in the string

    private CommonUtil(){}

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


    public static String readTextFile(InputStream inputStream) throws IOException { // getting XML

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toString();
    }

    public static Drawable getTintedIcon(Resources res) {
        Drawable shoppingCart = res.getDrawable(R.mipmap.ic_shopping_cart_black_12dp);
        Drawable wrapDrawable = DrawableCompat.wrap(shoppingCart);
        DrawableCompat.setTint(wrapDrawable, Color.parseColor("#aaaaaa"));
        return wrapDrawable;
    }
}
