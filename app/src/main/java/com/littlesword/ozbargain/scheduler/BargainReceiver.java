package com.littlesword.ozbargain.scheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.network.APIImp;
import com.littlesword.ozbargain.util.CatUrls;
import com.littlesword.ozbargain.util.CommonUtil;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.util.NotificationUtil;
import com.littlesword.ozbargain.util.TimeUtil;

import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by kongw1 on 18/11/15.
 */
public class BargainReceiver extends BroadcastReceiver {
    private static final String NOTIFICATION_DISSMISSED = "notification_dismissed";
    private static final String DISSMISSED_TIMESTAMP = "dismissed_timestamp";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if(intent != null && intent.getAction() != null){
            if(intent.getAction().compareToIgnoreCase(NOTIFICATION_DISSMISSED) == 0){
                //user dismiss the notification
                //TODO need to update the last bargain timestamp here!
                SharedPreferences mPref = context.getSharedPreferences(NotificationUtil.SHARED_PREF,Context.MODE_PRIVATE);
                String lastBargainTimestamp = mPref.getString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP, "11/11/2011 11:11");
                String dismissedTime = intent.getExtras().getString(DISSMISSED_TIMESTAMP);
                if(dismissedTime != null){
                    try {
                        if(TimeUtil.isNew(lastBargainTimestamp, dismissedTime)){
                            //save the new time stamp.
                            mPref.edit().putString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP,dismissedTime).apply();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        APIImp api = new APIImp();
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected)
//            api.getMainDocumentAsync(CatUrls.BASE_URL).subscribe(
//                    doc -> processDoc((Document) doc, context),
//                    this::handlerError
//            );
        api.getMainDocumentAsyncString(CommonUtil.readTextFile(context.getResources().openRawResource(R.raw.sad2)))
                .subscribe(new Subscriber<Object>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   handlerError(e);
                               }

                               @Override
                               public void onNext(Object o) {
                                   try {
                                       processDoc((Document) o, context);
                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                               }
                           }
                );
    }


    private void handlerError(Throwable error) {

    }

    private void processDoc(Document doc, Context context) throws ParseException {
        ArrayList<Bargain> list = DocExtractor.getBargainItems(doc);
        SharedPreferences mPref = context.getSharedPreferences(NotificationUtil.SHARED_PREF, Context.MODE_PRIVATE);
        String lastBargainTimestamp = mPref.getString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP, "11/11/2011 11:11");
        if(TimeUtil.isNew(lastBargainTimestamp, list.get(0).submittedOn)) {

            int mNotificationId = NotificationUtil.generateId();
            // Gets an instance of the NotificationManager service
            Notification notification = NotificationUtil.build(context,context.getString(R.string.notification_new_deal),list.get(0).title, list.get(0));
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
