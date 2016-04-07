package com.littlesword.ozbargain.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.test.ActivityInstrumentationTestCase2;

import com.littlesword.ozbargain.MainActivity;
import com.littlesword.ozbargain.model.Bargain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;

/**
 * Created by kongw1 on 7/04/16.
 */
@RunWith(AndroidJUnit4.class)
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
    public void build() throws Exception {
        mDevice.pressHome();
        int id = NotificationUtil.generateId();
        Notification n = NotificationUtil.build(mActivity, "title", "content",any(Bargain.class));
        NotificationManager mgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.notify(id , n);
        mDevice.openNotification();//open the notification drawer
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void testGenerateId() throws Exception {

    }
}