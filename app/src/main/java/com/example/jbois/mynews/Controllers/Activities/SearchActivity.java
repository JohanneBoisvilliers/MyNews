package com.example.jbois.mynews.Controllers.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.setPageTitle();
    }
    //Set the title in terms of what button user has pressed on the MainActivity toolbar and set the home button visible
    private void setPageTitle(){
        String pageTitle = getIntent().getStringExtra("pageTitle");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(pageTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
