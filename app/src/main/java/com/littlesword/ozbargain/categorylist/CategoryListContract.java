package com.littlesword.ozbargain.categorylist;

import com.littlesword.ozbargain.model.Bargain;

import org.jsoup.nodes.Document;

/**
 * Created by kongw1 on 12/03/16.
 */
public interface CategoryListContract {
    interface View{
        void showLoading();
        void dismissLoading();
        void handlerError(Throwable e);
        void showCategoryList(Document doc);
        void notifyCategoryLoaded(Document doc);
        void openBargainDetails(Bargain bargain);
    }

    interface Actions{
        void openBargain(Bargain bargain);

        void loadBargainCategory();

        void loadBargainList(String category);
    }
}
