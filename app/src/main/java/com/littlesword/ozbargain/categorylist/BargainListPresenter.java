package com.littlesword.ozbargain.categorylist;

import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.mvp.view.BargainDetailFragment;
import com.littlesword.ozbargain.network.APIInterface;
import com.littlesword.ozbargain.util.CatUrls;
import com.littlesword.ozbargain.util.DocExtractor;

import org.jsoup.nodes.Document;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by kongw1 on 17/03/16.
 */
public class BargainListPresenter implements CategoryListContract.Actions {
    private CategoryListContract.View view;
    private APIInterface api;

    public BargainListPresenter(CategoryListContract.View view, APIInterface api){
        this.view = view;
        this.api = api;
    }

    @Override
    public void openBargain(final Bargain bargain) {

        view.showLoading();
        api.getHomePageAsync(CatUrls.BASE_URL + "/" + bargain.nodeId).subscribe(new Action1<Object>() {
               @Override
               public void call(Object document) {
                   processDocument((Document) document, bargain);
               }
           }
        );
    }

    @Override
    public void loadBargainCategory() {

    }

    @Override
    public void loadBargainList(String category) {
        api.getHomePageAsync(CatUrls.BASE_URL +  category)
            .subscribe(new Subscriber<Object>() {
               @Override
               public void onCompleted() {
               }

               @Override
               public void onError(Throwable e) {
                   view.handlerError(e);
               }

               @Override
               public void onNext(Object o) {
                   view.showCategoryList((Document) o);
                   view.notifyCategoryLoaded((Document) o);
               }
           });
    }


    private void processDocument(Document document, Bargain bargain) {
        view.dismissLoading();
        //get to the bargain node details page.
        bargain.comments = DocExtractor.getComments(document);
        bargain.coupon = DocExtractor.getCoupon(document);
        bargain.description = DocExtractor.getDescription(document);
        view.openBargainDetails(bargain);
    }
}
