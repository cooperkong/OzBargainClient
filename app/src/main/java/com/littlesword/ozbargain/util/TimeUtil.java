package com.littlesword.ozbargain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kongw1 on 19/11/15.
 */
public class TimeUtil {


    public static boolean isNew(String in, String out){
        try {
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date inDate = outputFormat.parse(in);
            Date outDate = outputFormat.parse(out);
            return inDate.before(outDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
