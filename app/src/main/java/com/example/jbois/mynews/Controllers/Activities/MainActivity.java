package com.example.jbois.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activity_main_viewpager)ViewPager pager;
    @BindView(R.id.activity_main_tabs) TabLayout tabs;
    public final static String KEY_PAGE_TITLE="pageTitle";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Views from layout
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureViewPager();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_topstories:
                pager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_mostpopular:
                pager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_technology:
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_sciences:
                pager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_automobiles:
                pager.setCurrentItem(4);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
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
    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
