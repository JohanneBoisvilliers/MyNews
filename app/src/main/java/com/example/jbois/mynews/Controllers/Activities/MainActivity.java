package com.example.jbois.mynews.Controllers.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TableLayout;

import com.example.jbois.mynews.Controllers.Adapters.PageAdapter;
import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_main_viewpager)ViewPager pager;
    @BindView(R.id.activity_main_tabs) TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
    // That method configure the toolbar (serialize & sets it)
    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        ButterKnife.bind(this);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }
    private void configureViewPager(){
        //Get Views from layout
        ButterKnife.bind(this);
        //Set Adapter PageAdapter and glue it to viewpager
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager)) {
        });
        //Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
    }
}
