package com.littlesword.ozbargain.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.GregorianCalendar;

/**
 * Created by kongw1 on 18/11/15.
 */
public class BargainFetcher {

    public static void scheduleTask(Context context){

        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        Long time = new GregorianCalendar().getTimeInMillis()+10000;

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(context, BargainReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time,
                10000, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT));
    }
}
