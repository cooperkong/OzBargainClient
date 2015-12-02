package com.littlesword.ozbargain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.littlesword.ozbargain.model.Bargain;
import com.littlesword.ozbargain.network.APIImp;
import com.littlesword.ozbargain.scheduler.BargainFetcher;
import com.littlesword.ozbargain.util.CommonUtil;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.view.CategoryFragment;
import com.littlesword.ozbargain.view.DialogFragment;
import com.littlesword.ozbargain.view.SettingsActivity;
import com.littlesword.ozbargain.view.SettingsFragment;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CategoryFragment.MainInterface {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    Document document;
    APIImp api = new APIImp();
    private static final String DIALOG_TAG = "loading_dialog_tag";;
    private List<String> categories = new ArrayList<>();
    private boolean isHomeSelected;

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
        BargainFetcher.scheduleTask(this);
    }

    private void selectHome() {
        isHomeSelected = true;
        mNavigationView.getMenu().add(getString(R.string.home));
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().getItem(0).setCheckable(true);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
    }


    private void handlerError(Throwable error) {
        dismissLoading();
    }

    @Override
    public void dismissLoading() {
        DialogFragment loading = (DialogFragment) getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if(loading != null)
            loading.dismiss();
    }

    private void processDoc(Document doc) {
        DocExtractor.getCategories(doc).subscribe(
                s -> {
                    if (!categories.contains(s) && isHomeSelected) {
                        categories.add(s);
                        mNavigationView.getMenu().add(s);
                    }
                }//add category to menu item.
        );
        document = doc;
        dismissLoading();
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new CategoryFragment())
                .commit();
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
        showLoading();
        String uri = "";
        if(item.getTitle().toString().compareToIgnoreCase(getString(R.string.home)) != 0){
            isHomeSelected = false;
            uri = "/cat/" + item.getTitle().toString().replace("&","-").replace(" ","").toLowerCase();
        }
//        api.getMainDocumentAsync(CatUrls.BASE_URL +  uri).subscribe(
//                doc -> processDoc((Document) doc),
//                this :: handlerError
//        );
        api.getMainDocumentAsyncString(CommonUtil.readTextFile(getResources().openRawResource(R.raw.sad))).subscribe(
                doc -> processDoc((Document) doc),
                error -> handlerError(error)
        );
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLoading() {
        //display the loading dialog.
        DialogFragment dialog = DialogFragment.newInstant();
        dialog.show(getSupportFragmentManager().beginTransaction(), DIALOG_TAG);
    }

    private void updateTitle(String s) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(s);
    }

    @Override
    public Document getHomeDoc() {
        return document;
    }

    @Override
    public Observable<Object> getNodeDoc(Bargain bargain) {
        showLoading();
//        return api.getMainDocumentAsync(CatUrls.BASE_URL + "/" + bargain.nodeId);
        return api.getMainDocumentAsyncString(CommonUtil.readTextFile(getResources().openRawResource(R.raw.sad3)));
    }

}
