package com.littlesword.ozbargain.dagger;

import com.littlesword.ozbargain.categorylist.BargainListFragment;

import dagger.Component;

/**
 * Created by kongw1 on 1/04/16.
 */
@Component(modules =BargainListModule.class, dependencies = AppComponent.class
)
public interface BargainListComponent {
    void inject(BargainListFragment fragment);
}
