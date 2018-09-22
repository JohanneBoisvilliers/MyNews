package com.example.jbois.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jbois.mynews.Controllers.Adapters.PageAdapter;
import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activity_main_viewpager)ViewPager pager;
    @BindView(R.id.activity_main_tabs) TabLayout tabs;
    public final static String KEY_PAGE_TITLE="pageTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Views from layout
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                Intent intentNotifications = new Intent(this,SearchActivity.class);
                intentNotifications.putExtra(KEY_PAGE_TITLE,"Notifications");
                startActivity(intentNotifications);
                return true;
            case R.id.menu_activity_main_help:
                Toast.makeText(this, "Please contact me for any questions !", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_about:
                Toast.makeText(this, "MyNews version 1.0.0, 12/09/2018", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_search:
                Intent intentSearch = new Intent(this,SearchActivity.class);
                intentSearch.putExtra(KEY_PAGE_TITLE,"Search Articles");
                startActivity(intentSearch);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // That method configure the toolbar (serialize & sets it)
    private void configureToolbar(){
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }
    private void configureViewPager(){
        //Set Adapter PageAdapter and glue it to viewpager
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.pageTitleOfViewPager)){});
        //Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
    }
}
