package com.littlesword.ozbargain.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kongw1 on 19/11/15.
 */
public final class TimeUtil {

    private final static String expectedDateFormat = "dd/MM/yyyy HH:mm";

    private TimeUtil(){}
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
}
