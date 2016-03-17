package com.littlesword.ozbargain.categorylist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.littlesword.ozbargain.R;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.mvp.view.BargainDetailFragment;
import com.littlesword.ozbargain.mvp.view.SettingsActivity;
import com.littlesword.ozbargain.mvp.view.SettingsFragment;
import com.littlesword.ozbargain.scheduler.BargainFetcher;
import com.littlesword.ozbargain.util.CatUrls;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.util.NotificationUtil;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    Document document;
    private List<String> categories = new ArrayList<>();
    private boolean isHomeSelected;
    private static final String TAG = "MainActivity";
    public static final String NOTIFICATION_EXTRA = "notification_action";
    public static final String CATEGORIES = "categories";
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        selectHome();
        BargainFetcher.scheduleTask(this, getIntervalFromPref());
    }

    private void initFragment(Fragment notesFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, notesFragment);
        transaction.commit();
    }

    private void selectHome() {
        isHomeSelected = true;
        mNavigationView.getMenu().add(getString(R.string.home));
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().getItem(0).setCheckable(true);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
    }


    private void updateSettingsCategory(List<String> categories) {
        SharedPreferences.Editor pref = getSharedPreferences(NotificationUtil.SHARED_PREF, Context.MODE_PRIVATE).edit();
        pref.putString(CATEGORIES, gson.toJson(categories));
        pref.apply();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Display the fragment as the main content.
            Intent i = new Intent();
            i.setClass(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.setCheckable(true);
        item.setChecked(true);
        updateTitle(item.getTitle().toString());
        String uri = "";
        if(item.getTitle().toString().compareToIgnoreCase(getString(R.string.home)) != 0){
            isHomeSelected = false;
            uri = "/cat/" + item.getTitle().toString().replace("&","-").replace(" ","").toLowerCase();
        }

        initFragment(CategoryFragment.newInstance(uri));
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void updateTitle(String s) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(s);
    }





    private void processNotificationAction(Document document, Bargain bargain) {
//        mainInterface.dismissLoading();
        //get to the bargain node details page.
        bargain.comments = DocExtractor.getComments(document);
        bargain.coupon = DocExtractor.getCoupon(document);
        bargain.description = DocExtractor.getDescription(document);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BargainDetailFragment.newInstance(bargain))
                .addToBackStack("detail_fragment")
                .commit();
    }

    public long getIntervalFromPref() {
        return Long.parseLong(PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SettingsFragment.INTERVAL_NOTIFICATION, SettingsFragment.DEFAULT_INTERVAL+""));
    }
}
