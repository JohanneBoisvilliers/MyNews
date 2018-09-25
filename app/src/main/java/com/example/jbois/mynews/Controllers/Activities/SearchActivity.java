package com.example.jbois.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.jbois.mynews.Controllers.Fragments.SearchFragment;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.AlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jbois.mynews.Controllers.Activities.MainActivity.KEY_PAGE_TITLE;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private SearchFragment mSearchFragment;
    private String mPageTitle;
    public final static String KEY_TITLE = "TITLE";
    private AlarmManager mAlarmManager;
    private PendingIntent mAlarmIntent;
    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.setPageTitle();
        this.configureAndShowSearchFragment();
        this.setBundleToSetTitle();
    }

    private void setBundleToSetTitle(){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, mPageTitle);
        mSearchFragment.setArguments(bundle);
    }
    //Set the title in terms of what button user has pressed on the MainActivity toolbar and set the home button visible
    private void setPageTitle(){
        mPageTitle= getIntent().getStringExtra(KEY_PAGE_TITLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mPageTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureAndShowSearchFragment(){
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_search);

        if (mSearchFragment == null) {
            //Create new main fragment
            mSearchFragment = new SearchFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_fragment_container, mSearchFragment)
                    .commit();
        }
    }

    private void setAlarm(){
        mAlarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        // Set the alarm to start at 4:00a.m
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 4);

        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, mAlarmIntent);
    }
}
