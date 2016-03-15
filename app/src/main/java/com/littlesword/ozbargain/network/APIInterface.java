package com.littlesword.ozbargain.network;

import org.jsoup.nodes.Document;

import rx.Observable;


/**
 * Created by kongw1 on 13/11/15.
 */
public interface APIInterface {

    Observable<Document> getHomePageAsync(String url);

}
