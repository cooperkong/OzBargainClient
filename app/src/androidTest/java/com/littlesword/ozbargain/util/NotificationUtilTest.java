package com.littlesword.ozbargain.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;

import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;

/**
 * Created by kongw1 on 7/04/16.
 */
@RunWith(AndroidJUnit4.class)
//for tests that uses the device functionality rather than app itself.
public class NotificationUtilTest extends ActivityInstrumentationTestCase2<MainActivity>{


    private UiDevice mDevice;
    private MainActivity mActivity;

    public NotificationUtilTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
        mDevice = UiDevice.getInstance(getInstrumentation());

    }


    @Test
    @SdkSuppress(minSdkVersion = 18)
    public void buildAndSendNotification() throws Exception {
        mDevice.pressHome();
        int id = NotificationUtil.generateId();
        Bargain bargain = new Bargain();
        bargain.nodeId = "node123";
        bargain.title = "title";
        bargain.image = "image";
        bargain.description = "content";
        bargain.submittedOn = "11/11 2011 12:00";
        Notification n = NotificationUtil.build(mActivity, "title", "bargain content",bargain);
        NotificationManager mgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.notify(id , n);
        mDevice.openNotification();//open the notification drawer
        mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject notificationText = mDevice.findObject(new UiSelector().text("bargain content"));
        notificationText.clickAndWaitForNewWindow();

        //open the app from notification
        //use Espresso to verify that actual app is opened.
        //check if bargain detail fragment is opened
        onView(withId(R.id.bargain_detail_description)).check(matches(isDisplayed()));
    }

    @Test
    public void testGenerateId() throws Exception {
        assertThat(NotificationUtil.generateId()).isNotEqualTo(NotificationUtil.generateId());//generate unique id
    }
}