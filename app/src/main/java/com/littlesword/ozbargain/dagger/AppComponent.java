package com.littlesword.ozbargain.dagger;

import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.network.APIInterface;

import dagger.Component;

/**
 * Created by kongw1 on 6/04/16.
 */
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);

    APIInterface apiInterface();

}
