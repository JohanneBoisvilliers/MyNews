package com.example.jbois.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jbois.mynews.Controllers.Fragments.ResultSearchFragment;
import com.example.jbois.mynews.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.BEGIN_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.CATEGORY;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.END_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.QUERY_TERMS;


public class ResultSearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private ResultSearchFragment mResultSearchFragment;
    private String mQueryTerms,mGetBeginDate,mGetEndDate;
    private ArrayList<String> mCategory;
    public static final String SEARCH_QUERY_TERMS="QueryTerms";
    public static final String CHECKBOX_CATEGORY="categoryFromCheckboxes";
    public static final String GET_BEGIN_DATE="beginDateToRequest";
    public static final String GET_END_DATE="endDateToRequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        this.setPageTitle();
        this.configureAndShowSearchFragment();
        this.getInfosForResearch();
    }

    //Set the  page title
    private void setPageTitle(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //Picking infos sent by searchFragment to transfer them to resultSearch Fragment to do the request
    private void getInfosForResearch(){
        mQueryTerms=getIntent().getStringExtra(QUERY_TERMS);
        mCategory=getIntent().getStringArrayListExtra(CATEGORY);
        mGetBeginDate = getIntent().getStringExtra(BEGIN_DATE);
        mGetEndDate = getIntent().getStringExtra(END_DATE);
        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY_TERMS, mQueryTerms);
        args.putStringArrayList(CHECKBOX_CATEGORY,mCategory);
        args.putString(GET_BEGIN_DATE,mGetBeginDate);
        args.putString(GET_END_DATE,mGetEndDate);
        mResultSearchFragment.setArguments(args);
    }
    //Method that configure a new fragment and create it in his container
    private void configureAndShowSearchFragment(){
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mResultSearchFragment = (ResultSearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment_container);

        if (mResultSearchFragment == null) {
            //Create new main fragment
            mResultSearchFragment = new ResultSearchFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_fragment_container, mResultSearchFragment)
                    .commit();
        }
    }
}
