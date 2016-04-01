package com.littlesword.ozbargain.dagger;

import com.littlesword.ozbargain.mocknetwork.MockAPIImp;
import com.littlesword.ozbargain.network.APIInterface;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kongw1 on 1/04/16.
 */
@Module
public class AppModule {

    @Provides
    public APIInterface provideAPIInterface(){
        return new MockAPIImp();
    }
}
