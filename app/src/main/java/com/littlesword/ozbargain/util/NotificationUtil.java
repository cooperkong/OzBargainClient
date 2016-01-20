package com.littlesword.ozbargain.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;

/**
 * Created by kongw1 on 19/11/15.
 */
public class NotificationUtil {

    public static final String LATEST_BARGAIN_TIMESTAMP = "latest_timestamp";
    public static final String SHARED_PREF = "ob_sharedpref";

    public static Notification build(Context context, String title, String content, Bargain bargain){
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(MainActivity.NOTIFICATION_EXTRA, bargain);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_notification)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent);
        return mBuilder.build();
    }
}
