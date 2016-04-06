package com.littlesword.ozbargain;

import android.app.Application;

import com.littlesword.ozbargain.dagger.AppComponent;
import com.littlesword.ozbargain.dagger.DaggerAppComponent;

/**
 * Created by kongw1 on 1/04/16.
 */
public class MainApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        appComponent = DaggerAppComponent.create();
        super.onCreate();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
