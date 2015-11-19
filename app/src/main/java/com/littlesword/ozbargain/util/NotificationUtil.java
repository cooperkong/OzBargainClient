package com.littlesword.ozbargain.util;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.littlesword.ozbargain.R;

/**
 * Created by kongw1 on 19/11/15.
 */
public class NotificationUtil {

    public static final String LATEST_BARGAIN_TIMESTAMP = "latest_timestamp";
    public static final String SHARED_PREF = "ob_sharedpref";

    public static Notification build(Context context, String title, String content){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content);
        return mBuilder.build();
    }
}
