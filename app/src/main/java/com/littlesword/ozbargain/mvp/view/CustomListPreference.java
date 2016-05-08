package com.littlesword.ozbargain.mvp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.util.NotificationUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kongw1 on 22/01/16.
 */

/**
 * custom list preference for favorite category list
 */
public class CustomListPreference extends MultiSelectListPreference {

    private String myEntryValues[] = null;
    private List<String> tmp ;
    private Gson gson = new Gson();

    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        Type listType = new TypeToken<List<String>>() {}.getType();
        SharedPreferences pref = context.getSharedPreferences(NotificationUtil.SHARED_PREF, Context.MODE_PRIVATE);
        tmp = gson.fromJson(pref.getString(MainActivity.CATEGORIES,"{}"), listType);
        tmp.add(0, context.getString(R.string.all));
        myEntryValues = tmp.toArray(new String[tmp.size()]);
        setEntries(myEntryValues);
        setEntryValues(myEntryValues);
    }

    public CustomListPreference(Context context) {
        super(context);
        setEntries(myEntryValues);
        setEntryValues(myEntryValues);
    }


}
