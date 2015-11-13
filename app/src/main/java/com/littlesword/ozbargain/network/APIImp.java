package com.littlesword.ozbargain.network;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kongw1 on 13/11/15.
 */
public final class APIImp implements APIInterface{


    public APIImp(){

    }

    @Override
    public Document getMainDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.ozbargain.com.au/").get();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Observable<Object> getMainDocumentAsync(String url) {
        return Observable.defer(() -> Observable.just(getMainDocument(url))).compose(applySchedulers());
    }

    /**
     * Make observable running async.
     * @param <T>
     * @return
     */
    <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
