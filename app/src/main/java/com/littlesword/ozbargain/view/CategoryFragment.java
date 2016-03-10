package com.littlesword.ozbargain.view;

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
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.util.NotificationUtil;
import com.littlesword.ozbargain.util.TimeUtil;

import org.jsoup.nodes.Document;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by kongw1 on 14/11/15.
 */
public class CategoryFragment extends Fragment implements onBargainItemClicklistener {

    @Bind(R.id.bargain_menu_recyclerview)
    RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BargainMenuRecyclerViewAdapter mAdapter;
    private MainInterface mainInterface;

    @Override
    public void onAttach(Context context) {
        mainInterface = (MainInterface) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bargain_list, container, false);
        ButterKnife.bind(this, v);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLayoutManager);
        ArrayList<Bargain> list = DocExtractor.getBargainItems(mainInterface.getHomeDoc());
        mAdapter = new BargainMenuRecyclerViewAdapter(getContext(), list, this);
        try {
            updateTimestamp(list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mRecycleView.setAdapter(mAdapter);
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
        mainInterface.getNodeDoc(bargain.nodeId).subscribe(new Action1<Object>() {
               @Override
               public void call(Object document) {
                   processDocument((Document) document, bargain);
               }
           }
        );

    }

    private void processDocument(Document document, Bargain bargain) {
        mainInterface.dismissLoading();
        //get to the bargain node details page.
        bargain.comments = DocExtractor.getComments(document);
        bargain.coupon = DocExtractor.getCoupon(document);
        bargain.description = DocExtractor.getDescription(document);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BargainDetailFragment.newInstance(bargain))
                .addToBackStack("detail_fragment")
                .commit();
    }


    public interface MainInterface{
        Document getHomeDoc();
        Observable<Document> getNodeDoc(String nodeId);
        void dismissLoading();

    }
}
