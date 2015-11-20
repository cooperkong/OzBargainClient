package com.littlesword.ozbargain.scheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.network.APIImp;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.util.NotificationUtil;
import com.littlesword.ozbargain.util.TimeUtil;

import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by kongw1 on 18/11/15.
 */
public class BargainReceiver extends BroadcastReceiver {
    private static final String NOTIFICATION_DISSMISSED = "notification_dismissed";
    private static final String DISSMISSED_TIMESTAMP = "dismissed_timestamp";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction() != null){
            if(intent.getAction().compareToIgnoreCase(NOTIFICATION_DISSMISSED) == 0){
                //user dismiss the notification
                //TODO need to update the last bargain timestamp here!
                SharedPreferences mPref = context.getSharedPreferences(NotificationUtil.SHARED_PREF,Context.MODE_PRIVATE);
                String lastBargainTimestamp = mPref.getString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP, "11/11/2011 11:11");
                String dismissedTime = intent.getExtras().getString(DISSMISSED_TIMESTAMP);
                Log.d("wenchao", "lastBargainTimestamp " + lastBargainTimestamp);
                Log.d("wenchao", "dismissedTime " + dismissedTime);
                if(dismissedTime != null){
                    if(TimeUtil.isNew(lastBargainTimestamp, dismissedTime)){
                        //save the new time stamp.
                        mPref.edit().putString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP,dismissedTime).apply();
                    }
                }
            }
        }
        APIImp api = new APIImp();
//        api.getMainDocumentAsync("https://ozbargain.com.au").subscribe(
//                doc -> processDoc((Document) doc, context),
//                error -> handlerError(error)
//        );
        api.getMainDocumentAsyncString(readTextFile(R.raw.sad2, context)).subscribe(
                doc -> processDoc((Document) doc, context),
                error -> handlerError(error)
        );
    }

    public String readTextFile(int resId, Context context) {
        InputStream inputStream = context.getResources().openRawResource(resId); // getting XML

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

    private void handlerError(Throwable error) {

    }

    private void processDoc(Document doc, Context context) {
        ArrayList<Bargain> list = DocExtractor.getBargainItems(doc);
        SharedPreferences mPref = context.getSharedPreferences(NotificationUtil.SHARED_PREF, Context.MODE_PRIVATE);
        String lastBargainTimestamp = mPref.getString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP, "11/11/2011 11:11");
        if(TimeUtil.isNew(lastBargainTimestamp, list.get(0).submittedOn)) {

            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            Notification notification = NotificationUtil.build(context,"New Deal!",list.get(0).descriptoin);
            Intent deleteIntent = new Intent(context, BargainReceiver.class);
            deleteIntent.setAction(NOTIFICATION_DISSMISSED);
            deleteIntent.putExtra(DISSMISSED_TIMESTAMP, list.get(0).submittedOn);
            notification.deleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, 0);
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, notification);
        }
    }
}
