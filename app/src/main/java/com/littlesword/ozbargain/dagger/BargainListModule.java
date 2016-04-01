package com.littlesword.ozbargain.dagger;

import com.littlesword.ozbargain.categorylist.BargainListContract;
import com.littlesword.ozbargain.categorylist.BargainListPresenter;
import com.littlesword.ozbargain.network.APIInterface;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kongw1 on 1/04/16.
 */
@Module
public class BargainListModule {

    @Provides
    BargainListContract.Actions provideCategoryPresenter( APIInterface apiInterface){
        return new BargainListPresenter(apiInterface);
    }

}
