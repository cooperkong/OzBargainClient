package com.littlesword.ozbargain.categorylist;

import com.littlesword.ozbargain.network.APIInterface;
import com.littlesword.ozbargain.util.CatUrls;

import org.jsoup.nodes.Document;

import rx.Subscriber;

/**
 * Created by kongw1 on 17/03/16.
 */
public class BargainListPresenter implements CategoryListContract.Actions {
    private CategoryListContract.View view;
    private APIInterface api;

    public BargainListPresenter(CategoryListContract.View view, APIInterface api){
        this.view = view;
        this.api = api;
    }

    @Override
    public void openBargain() {

    }

    @Override
    public void loadBargainCategory() {

    }

    @Override
    public void loadBargainList(String category) {
        api.getHomePageAsync(CatUrls.BASE_URL +  category)
            .subscribe(new Subscriber<Object>() {
               @Override
               public void onCompleted() {
               }

               @Override
               public void onError(Throwable e) {
                   view.handlerError(e);
               }

               @Override
               public void onNext(Object o) {
                   view.showCategoryList((Document) o);
               }
           });
    }
}
