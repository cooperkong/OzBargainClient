package com.littlesword.ozbargain.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.network.APIImp;
import com.littlesword.ozbargain.util.DocExtractor;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by kongw1 on 18/11/15.
 */
public class BargainReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        APIImp api = new APIImp();
        api.getMainDocumentAsync("https://ozbargain.com.au").subscribe(
                doc -> processDoc((Document) doc),
                error -> handlerError(error)
        );
    }

    private void handlerError(Throwable error) {

    }

    private void processDoc(Document doc) {
        ArrayList<Bargain> list = DocExtractor.getBargainItems(doc);
        Log.d("wenchao", "fetched item : " + list.get(0).descriptoin);
    }
}
