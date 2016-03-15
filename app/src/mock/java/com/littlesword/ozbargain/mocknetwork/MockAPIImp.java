package com.littlesword.ozbargain.mocknetwork;

import com.littlesword.ozbargain.network.APIInterface;
import com.littlesword.ozbargain.util.CommonUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by kongw1 on 13/03/16.
 */
public class MockAPIImp implements APIInterface{

    private Document getHomePage(String content) {
        String file = "res/raw/sad"; // res/raw/test.txt also work.
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        try {
             return Jsoup.parse(CommonUtil.readTextFile(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * In mock flavor, parameter is used as actual content
     * @param content
     * @return
     */
    @Override
    public Observable<Document> getHomePageAsync(final String content) {
        return Observable.defer(new Func0<Observable<Document>>() {
            @Override
            public Observable<Document> call() {
                return Observable.just(getHomePage(content))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        });
    }
}
