package com.littlesword.ozbargain.mocknetwork;

import com.littlesword.ozbargain.network.APIInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by kongw1 on 13/03/16.
 */
public class MockAPIImp implements APIInterface{

    @Override
    public Document getHomePage(String url) {
        return Jsoup.parse(url);
    }



    public Observable<Document> getHomePageAsync(final String url) {
        return Observable.defer(new Func0<Observable<Document>>() {
            @Override
            public Observable<Document> call() {
                return Observable.just(getHomePage(url))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        });
    }
}
