package com.littlesword.ozbargain.util;

import android.annotation.SuppressLint;

import com.littlesword.ozbargain.model.Bargain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kongw1 on 19/11/15.
 */
public class TimeUtil {


    /**
     * if out time is later than in time.
     * @param in
     * @param out
     * @return
     */
    public static boolean isNew(String in, String out){
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date inDate = outputFormat.parse(in.replace("on","").replace("-",""));
            Date outDate = outputFormat.parse(out.replace("on","").replace("-",""));
            return inDate.before(outDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
