package com.littlesword.ozbargain;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.littlesword.ozbargain.dagger.AppComponent;
import com.littlesword.ozbargain.dagger.DaggerAppComponent;

/**
 * Created by kongw1 on 1/04/16.
 */
public class MainApplication extends Application {

    private AppComponent appComponent;
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
    @Override
    public void onCreate() {
        appComponent = DaggerAppComponent.create();
        super.onCreate();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
