package com.littlesword.ozbargain;

import android.graphics.drawable.Drawable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.littlesword.ozbargain.util.CommonUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kongw1 on 10/03/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testDrawerLoaded(){
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(open()); // Open Drawer

        onView(allOf(withClassName(endsWith("AppCompatCheckedTextView")), withText("Home")))
                .check(matches(withText("Home")))
                .check(matches(isChecked()))
                .check(matches(isDisplayed()))
                .perform(click());//check Home category is displayed and selected

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed()));//drawer should be closed.

    }

    @Test
    public void testDrawerNavigationFromBargainDetail(){

        onView(withId(R.id.bargain_menu_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.title_container))
                .check(matches(isDisplayed()));//check detail page is opened.
        //open drawer

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(open()); // Open Drawer
        //navigate to Gaming from details screen
        onView(allOf(withClassName(endsWith("AppCompatCheckedTextView")), withText("Gaming")))
                .check(matches(withText("Gaming")))
                .check(matches(isDisplayed()))
                .perform(click());
        //make sure the titile changed to Gaming.
        matchToolbarTitle("Gaming");
    }

    @Test
    public void testSettingsMenu(){
        openActionBarOverflowOrOptionsMenu(mActivityRule.getActivity());//open settings menu
        onView(withText(R.string.action_settings))
                .perform(click());// click on settings
        onView(withText(R.string.pref_notification_title))
                .check(matches(notNullValue()));//check if settings screen is opened

//        onView(withContentDescription(android.support.v7.appcompat.R.string.abc_action_bar_up_description))
//                .perform(click());//click home button
        Espresso.pressBack();

        //previous view should be shown
        onView(withId(R.id.fragment_container))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testCommonUtilgetTintedIcon(){
        Drawable d = CommonUtil.getTintedIcon(
                mActivityRule.getActivity().getResources());
        assertNotNull(d);
    }

    @Test
    public void testOpenBargain(){
        onView(withId(R.id.bargain_menu_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.title_container))
                .check(matches(isDisplayed()));
        Espresso.pressBack();

        //previous view should be shown
        onView(withId(R.id.fragment_container))
                .check(matches(isDisplayed()));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());//test public API rather than look for textviews inside the toolbar.
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
    private static ViewInteraction matchToolbarTitle(
            CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }
}