package com.example.jbois.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultSearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        ButterKnife.bind(this);

        this.setPageTitle();
    }

    //Set the title in terms of what button user has pressed on the MainActivity toolbar and set the home button visible
    private void setPageTitle(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
