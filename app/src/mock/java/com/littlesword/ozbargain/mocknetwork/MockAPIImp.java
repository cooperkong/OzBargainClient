package com.littlesword.ozbargain.mocknetwork;

import com.littlesword.ozbargain.network.APIInterface;

import org.jsoup.nodes.Document;

/**
 * Created by kongw1 on 13/03/16.
 */
public class MockAPIImp implements APIInterface{

    @Override
    public Document getMainDocument(String url) {
        return null;
    }
}
