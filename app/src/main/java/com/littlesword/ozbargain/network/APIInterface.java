package com.littlesword.ozbargain.network;

import org.jsoup.nodes.Document;

/**
 * Created by kongw1 on 13/11/15.
 */
public interface APIInterface {

    Document getMainDocument(String url);

}
