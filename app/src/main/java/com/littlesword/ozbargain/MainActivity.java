package com.littlesword.ozbargain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
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
import com.littlesword.ozbargain.util.DocExtractor;
import com.littlesword.ozbargain.view.CategoryFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        APIImp api = new APIImp();
        api.getMainDocumentAsync("https://ozbargain.com.au").subscribe(
                doc -> processDoc((Document) doc),
                error -> handlerError(error)
        );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void handlerError(Throwable error) {

    }

    private void processDoc(Document doc) {
        DocExtractor.getCategories(doc).subscribe(
                s -> mNavigationView.getMenu().add(s)
        );
        document = doc;
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
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Document getDoc() {
        return document;
    }
}
