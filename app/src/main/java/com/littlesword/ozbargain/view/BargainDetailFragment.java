package com.littlesword.ozbargain.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongw1 on 25/11/15.
 */
public class BargainDetailFragment extends Fragment {
    private static final String BARGAIN_DETAIL_KEY = "bargain_details_key";

    @Bind(R.id.bargain_detail_title)
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bargain_fragment, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        if(bundle != null){
            Bargain mBargain = (Bargain) bundle.getSerializable(BARGAIN_DETAIL_KEY);
            if(mBargain != null)
                title.setText(mBargain.title);
        }
        return v;
    }



    public static Fragment newInstance(Bargain bargain) {
        BargainDetailFragment f = new BargainDetailFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable(BARGAIN_DETAIL_KEY, bargain);
        f.setArguments(args);
        return f;
    }
}
