package com.littlesword.ozbargain.categorylist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.adapter.BargainMenuRecyclerViewAdapter;
import com.littlesword.ozbargain.dagger.DaggerBargainListComponent;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.bargaindetail.BargainDetailFragment;
import com.littlesword.ozbargain.mvp.view.DialogFragment;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.util.NotificationUtil;
import com.littlesword.ozbargain.util.TimeUtil;

import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 14/11/15.
 */
public class BargainListFragment extends Fragment implements BargainListContract.View {
    private static final String DIALOG_TAG = "loading_dialog_tag";
    private static final String CAT_ID = "category_string";
    @Inject BargainListContract.Actions actions;
    @Bind(R.id.bargain_menu_recyclerview)
    RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BargainMenuRecyclerViewAdapter mAdapter;
    private CallBack callBack;

    @Override
    public void onAttach(Context context) {
        callBack = (CallBack) context;
        super.onAttach(context);
    }

    public static BargainListFragment newInstance(String title){
        BargainListFragment ret = new BargainListFragment();
        Bundle args = new Bundle();
        args.putString(CAT_ID, title);
        ret.setArguments(args);
        return ret;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bargain_list, container, false);
        ButterKnife.bind(this, v);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLayoutManager);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaggerBargainListComponent.builder().appComponent()
        actions.setView(this);
        showLoading();
        //fetching the Home page of OZBargain
        actions.loadBargainList(getArguments().getString(CAT_ID));
    }

    private void updateTimestamp(ArrayList<Bargain> list) throws ParseException {

        String savedLastTimestamp = getActivity().getSharedPreferences(NotificationUtil.SHARED_PREF,Context.MODE_PRIVATE)
                .getString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP, "11/11/2011 11:11");
        if(TimeUtil.isNew(savedLastTimestamp, list.get(0).submittedOn)) {
            SharedPreferences.Editor mPref = getActivity().getSharedPreferences(NotificationUtil.SHARED_PREF,Context.MODE_PRIVATE).edit();
            mPref.putString(NotificationUtil.LATEST_BARGAIN_TIMESTAMP, list.get(0).submittedOn);
            mPref.apply();
        }
    }


    @Override
    public void showLoading() {
        DialogFragment dialog = DialogFragment.newInstant();
        dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), DIALOG_TAG);

    }

    @Override
    public void dismissLoading() {
        DialogFragment loading = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if(loading != null)
            loading.dismiss();
    }

    @Override
    public void showCategoryList(Document doc) {
        ArrayList<Bargain> list = DocExtractor.getBargainItems(doc);
        mAdapter = new BargainMenuRecyclerViewAdapter(getActivity(), list, actions);
        try {
            updateTimestamp(list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mRecycleView.setAdapter(mAdapter);
        dismissLoading();

    }

    @Override
    public void notifyCategoryLoaded(Document doc) {
        callBack.onCategoryLoaded(doc);
    }

    @Override
    public void openBargainDetails(Bargain bargain) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BargainDetailFragment.newInstance(bargain))
                .addToBackStack("detail_fragment")
                .commit();
    }

    @Override
    public void handlerError(Throwable error) {
        //TODO display some sort of error message.
        dismissLoading();
    }

    private void processDoc(Document doc) {
//        //in case MainActivity is opened from notification
//        Bargain bargain = null;
//        if(getIntent() != null)
//            bargain = (Bargain) getIntent().getSerializableExtra(NOTIFICATION_EXTRA);
//        if(bargain != null){
//            final Bargain finalBargain = bargain;
//            api.getHomePageAsync(CatUrls.BASE_URL + "/" + bargain.nodeId).subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//                        processNotificationAction(document, finalBargain);
//                    }
//                }
//            );
//        }


    }

    public interface CallBack {
        void onCategoryLoaded(Document doc);
    }
}
