package com.example.jbois.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jbois.mynews.Controllers.Fragments.SearchFragment;
import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private SearchFragment mSearchFragment;
    private String mPageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.setPageTitle();
        this.configureAndShowSearchFragment();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", mPageTitle);
        mSearchFragment.setArguments(bundle);

    }
    //Set the title in terms of what button user has pressed on the MainActivity toolbar and set the home button visible
    private void setPageTitle(){
        mPageTitle= getIntent().getStringExtra("pageTitle");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mPageTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureAndShowSearchFragment(){
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment_container);

        if (mSearchFragment == null) {
            //Create new main fragment
            mSearchFragment = new SearchFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_fragment_container, mSearchFragment)
                    .commit();
        }
    }

}