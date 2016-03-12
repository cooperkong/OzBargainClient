package com.littlesword.ozbargain.categorylist;

/**
 * Created by kongw1 on 12/03/16.
 */
public interface CategoryListContract {
    interface View{
        void showLoading();
        void dismissLoading();
        void showCategoryList();
        void setUserActionListener(UserAction userActoin);
    }

    interface UserAction{
        void openBargain();
    }
}
