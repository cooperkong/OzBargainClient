package com.littlesword.ozbargain.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

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
            doc = Jsoup.connect(url)
                    .get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Document getMainDocumentString(String url) {
        Document doc = null;
        doc = Jsoup.parse(url);
        return doc;
    }

    public Observable<Object> getMainDocumentAsyncString(String url) {
        return Observable.defer(() -> Observable.just(getMainDocumentString(url))).compose(applySchedulers());
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
