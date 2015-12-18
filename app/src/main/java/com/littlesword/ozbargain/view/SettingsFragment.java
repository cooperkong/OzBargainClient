package com.littlesword.ozbargain.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.scheduler.BargainFetcher;
import com.littlesword.ozbargain.scheduler.BargainReceiver;

/**
 * Created by kongw1 on 2/12/15.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("pref_notification")){
            boolean val = sharedPreferences.getBoolean(key, true);
            if(!val) {
                Intent intent = new Intent(getActivity(), BargainReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }else{
                BargainFetcher.scheduleTask(getActivity());
            }
        }
    }
}
