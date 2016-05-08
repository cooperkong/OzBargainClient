package com.littlesword.ozbargain.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by kongw1 on 19/11/15.
 */
public final class AppUtil {

    private final static String expectedDateFormat = "dd/MM/yyyy HH:mm";

    private AppUtil(){}
    /**
     * if out time is later than in time.
     * @param in
     * @param out
     * @return
     */
    public static boolean isNew(String in, String out) throws ParseException {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(expectedDateFormat);
            Date inDate = outputFormat.parse(in.replace("on","").replace("-",""));
            Date outDate = outputFormat.parse(out.replace("on","").replace("-",""));
            return inDate.before(outDate);
    }

    public static boolean isWanted(String tag, Set<String> stringSet) {
        return stringSet.contains(tag);
    }
}
