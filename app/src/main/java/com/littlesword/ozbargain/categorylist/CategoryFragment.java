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

import com.littlesword.ozbargain.Injection;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.adapter.BargainMenuRecyclerViewAdapter;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.mvp.view.BargainDetailFragment;
import com.littlesword.ozbargain.mvp.view.DialogFragment;
import com.littlesword.ozbargain.mvp.view.onBargainItemClicklistener;
import com.littlesword.ozbargain.network.APIInterface;
import com.littlesword.ozbargain.util.CatUrls;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.util.NotificationUtil;
import com.littlesword.ozbargain.util.TimeUtil;

import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by kongw1 on 14/11/15.
 */
public class CategoryFragment extends Fragment implements onBargainItemClicklistener, CategoryListContract.View {
    private static final String DIALOG_TAG = "loading_dialog_tag";
    private static final String CAT_ID = "category_string";

    @Bind(R.id.bargain_menu_recyclerview)
    RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BargainMenuRecyclerViewAdapter mAdapter;
    APIInterface api = Injection.getAPIImp();
    Document document;
    private List<String> categories = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static CategoryFragment newInstance(String title){
        CategoryFragment ret = new CategoryFragment();
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

        api.getHomePageAsync(CatUrls.BASE_URL +  getArguments().getString(CAT_ID))
                .subscribe(new Subscriber<Object>() {
                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {
                                   handlerError(e);
                               }

                               @Override
                               public void onNext(Object o) {
                                   processDoc((Document) o);

                               }
                           }
                );
        return v;
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
    public void onBargainClicked(final Bargain bargain) {
        getNodeDoc(bargain.nodeId).subscribe(new Action1<Object>() {
               @Override
               public void call(Object document) {
                   processDocument((Document) document, bargain);
               }
           }
        );

    }

    private void processDocument(Document document, Bargain bargain) {
        dismissLoading();
        //get to the bargain node details page.
        bargain.comments = DocExtractor.getComments(document);
        bargain.coupon = DocExtractor.getCoupon(document);
        bargain.description = DocExtractor.getDescription(document);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BargainDetailFragment.newInstance(bargain))
                .addToBackStack("detail_fragment")
                .commit();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {
        DialogFragment loading = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if(loading != null)
            loading.dismiss();
    }

    @Override
    public void showCategoryList() {
            //display the loading dialog.
        DialogFragment dialog = DialogFragment.newInstant();
        dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), DIALOG_TAG);

    }

    @Override
    public void setUserActionListener(CategoryListContract.Actions userActoin) {

    }

    public Observable<Document> getNodeDoc(String nodeId) {
        showLoading();
        return api.getHomePageAsync(CatUrls.BASE_URL + "/" + nodeId);
    }

    public Document getHomeDoc() {
        return document;
    }


    private void handlerError(Throwable error) {
        dismissLoading();
    }

    private void processDoc(Document doc) {
//        DocExtractor.getCategories(doc).subscribe(
//                new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        if (!categories.contains(s) && isHomeSelected) {
//                            categories.add(s);
//                            mNavigationView.getMenu().add(s);
//                        }
//                        //update settings for selecting category
//                        //in case there is an update in categories, eg: Gaming is added/removed
//                        updateSettingsCategory(categories);
//                    }
//                }
//        );
//        document = doc;
//        getActivity().getSupportFragmentManager().popBackStack();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, CategoryFragment.newInstance())
//                .commit();
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

        ArrayList<Bargain> list = DocExtractor.getBargainItems(doc);
        mAdapter = new BargainMenuRecyclerViewAdapter(getContext(), list, this);
        try {
            updateTimestamp(list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        showLoading();
        mRecycleView.setAdapter(mAdapter);

    }
}
