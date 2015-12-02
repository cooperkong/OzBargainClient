package com.littlesword.ozbargain.view;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.littlesword.ozbargain.R;

/**
 * Created by kongw1 on 2/12/15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}
