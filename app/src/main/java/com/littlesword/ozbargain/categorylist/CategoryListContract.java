package com.littlesword.ozbargain.categorylist;

/**
 * Created by kongw1 on 12/03/16.
 */
public interface CategoryListContract {
    interface View{
        void showLoading();
        void dismissLoading();
        void showCategoryList();
        void setUserActionListener(Actions userActoin);
    }

    interface Actions{
        void openBargain();

        void loadBargainCategory();

        void loadBargainList();
    }
}
