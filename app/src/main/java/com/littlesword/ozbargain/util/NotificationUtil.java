package com.littlesword.ozbargain.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.categorylist.BargainListFragment;
import com.littlesword.ozbargain.model.Bargain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kongw1 on 19/11/15.
 */
public class NotificationUtil {

    public static final String LATEST_BARGAIN_TIMESTAMP = "latest_timestamp";
    public static final String SHARED_PREF = "ob_sharedpref";
    private final static AtomicInteger c = new AtomicInteger(0);

    public static Notification build(Context context, String title, String content, Bargain bargain){
        Intent resultIntent = new Intent(context, MainActivity.class);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        resultIntent.putExtra(BargainListFragment.NOTIFICATION_EXTRA, bargain);
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
                        .setSound(soundUri)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent);
        return mBuilder.build();
    }

    public static int generateId() {
            return c.incrementAndGet();
    }
}
