package com.littlesword.ozbargain;

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
import com.littlesword.ozbargain.network.APIImp;
import com.littlesword.ozbargain.scheduler.BargainFetcher;
import com.littlesword.ozbargain.util.CatUrls;
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.view.CategoryFragment;
import com.littlesword.ozbargain.view.DialogFragment;

import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        mNavigationView.getMenu().add(getString(R.string.home));
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().getItem(0).setCheckable(true);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
    }

    public String readTextFile(int resId) {
        InputStream inputStream = getResources().openRawResource(resId); // getting XML

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    private void handlerError(Throwable error) {
        dismissLoading();
    }

    private void dismissLoading() {
        DialogFragment loading = (DialogFragment) getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if(loading != null)
            loading.dismiss();
    }

    private void processDoc(Document doc) {
        DocExtractor.getCategories(doc).subscribe(
                s -> mNavigationView.getMenu().add(s)
        );
        document = doc;
        dismissLoading();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new CategoryFragment()).commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setCheckable(true);
        item.setChecked(true);
        mNavigationView.setCheckedItem(id);
        updateTitle(item.getTitle().toString());
        api.getMainDocumentAsync(CatUrls.BASE_URL).subscribe(
                doc -> processDoc((Document) doc),
                error -> handlerError(error)
        );

        DialogFragment dialog = DialogFragment.newInstant();
        dialog.show(getSupportFragmentManager().beginTransaction(),DIALOG_TAG );
//        api.getMainDocumentAsyncString(readTextFile(R.raw.sad)).subscribe(
//                doc -> processDoc((Document) doc),
//                error -> handlerError(error)
//        );
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateTitle(String s) {
        getSupportActionBar().setTitle(s);
    }

    @Override
    public Document getDoc() {
        return document;
    }
}
