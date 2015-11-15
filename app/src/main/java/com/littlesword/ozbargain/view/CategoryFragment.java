package com.littlesword.ozbargain.view;

import android.content.Context;
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
import com.littlesword.ozbargain.util.DocExtractor;

import org.jsoup.nodes.Document;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 14/11/15.
 */
public class CategoryFragment extends Fragment {

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
        mAdapter = new BargainMenuRecyclerViewAdapter(DocExtractor.getBargainItems(mainInterface.getDoc()));
        mRecycleView.setAdapter(mAdapter);
        return v;
    }

    public interface MainInterface{
        Document getDoc();
    }
}
