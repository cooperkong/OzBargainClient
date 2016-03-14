package com.littlesword.ozbargain.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by kongw1 on 13/11/15.
 */
public final class APIImp implements APIInterface{


    public APIImp(){

    }

    @Override
    public Document getHomePage(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .get();

        } catch (IOException e) {
            //TODO sometimes through SSL exception, need to handle properly
            e.printStackTrace();
        }
        return doc;
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
