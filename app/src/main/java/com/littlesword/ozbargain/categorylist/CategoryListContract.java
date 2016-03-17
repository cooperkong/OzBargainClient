package com.littlesword.ozbargain.categorylist;

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
        void setUserActionListener(Actions userActoin);
    }

    interface Actions{
        void openBargain();

        void loadBargainCategory();

        void loadBargainList(String category);
    }
}
