package com.littlesword.ozbargain.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.littlesword.ozbargain.R;

/**
 * Created by kongw1 on 14/11/15.
 */
public class DialogFragment extends android.support.v4.app.DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static DialogFragment newInstant(){
        DialogFragment dialog = new DialogFragment();
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity(), getTheme());
        dialog.setMessage(getString(R.string.dialog_title));
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;
    }

}
