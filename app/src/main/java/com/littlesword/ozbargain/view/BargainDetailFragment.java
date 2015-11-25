package com.littlesword.ozbargain.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.littlesword.ozbargain.R;

/**
 * Created by kongw1 on 25/11/15.
 */
public class BargainDetailFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bargain_fragment, container, false);
        return v;
    }
}
