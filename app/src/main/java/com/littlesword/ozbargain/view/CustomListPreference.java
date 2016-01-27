package com.littlesword.ozbargain.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.util.NotificationUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongw1 on 22/01/16.
 */

/**
 * custom list preference for favorite category list
 */
public class CustomListPreference extends MultiSelectListPreference {

    String myEntryValues[] = null;
    List<String> tmp ;
    Gson gson = new Gson();

    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        Type listType = new TypeToken<List<String>>() {}.getType();
        SharedPreferences pref = context.getSharedPreferences(NotificationUtil.SHARED_PREF, Context.MODE_PRIVATE);
        tmp = gson.fromJson(pref.getString(MainActivity.CATEGORIES,"{}"), listType);
        myEntryValues = tmp.toArray(new String[tmp.size()]);
        setEntries(myEntryValues);
        setEntryValues(myEntryValues);
    }

    public CustomListPreference(Context context) {
        super(context);
        setEntries(myEntryValues);
        setEntryValues(myEntryValues);
    }

    private String[] initEntries(){

        return myEntryValues;
    }

}
