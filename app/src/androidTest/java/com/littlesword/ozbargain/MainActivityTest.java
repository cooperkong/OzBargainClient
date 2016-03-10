package com.littlesword.ozbargain;

import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.littlesword.ozbargain.util.CommonUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by kongw1 on 10/03/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testCommonUtilgetTintedIcon(){
        Drawable d = CommonUtil.getTintedIcon(
                mActivityRule.getActivity().getResources());
        assertNotNull(d);
    }
}