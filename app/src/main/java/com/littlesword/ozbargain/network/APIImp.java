package com.littlesword.ozbargain.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

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
    public Document getMainDocument(String url) {
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

    public Document getMainDocumentString(String url) {
        Document doc = null;
        doc = Jsoup.parse(url);
        return doc;
    }

    public Observable<Object> getMainDocumentAsyncString(final String url) {
        return Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(getMainDocumentString(url)).compose(applySchedulers());
            }
        });
    }

    public Observable<Document> getMainDocumentAsync(final String url) {
        return applySchedulers(Observable.defer(new Func0<Observable<Document>>() {
            @Override
            public Observable<Document> call() {
                return Observable.just(getMainDocument(url))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        }));
//        return Observable.defer(new Func0<Observable<Object>>() {
//            @Override
//            public Observable<Object> call() {
//                return .compose(applySchedulers());
//            }
//        });
    }

    /**
     * Make observable running async.
     * @param <T>
     * @return
     */
    <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    <T> Observable<T> applySchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
