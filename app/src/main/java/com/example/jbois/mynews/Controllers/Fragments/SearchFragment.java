package com.example.jbois.mynews.Controllers.Fragments;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.AlarmReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.example.jbois.mynews.Controllers.Activities.SearchActivity.KEY_TITLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.notifications_search_option) View mNotificationOption;
    @BindView(R.id.date_options) View mDateOption;
    @BindView(R.id.button_search) Button mSearchButton;
    @BindView(R.id.select_begin_date) EditText mBeginDate;
    @BindView(R.id.select_end_date) EditText mEndDate;
    @BindView(R.id.search_term_query) EditText mSearchTerm;
    @BindViews({R.id.checkbox_arts, R.id.checkbox_business, R.id.checkbox_entrepreneurs, R.id.checkbox_politics, R.id.checkbox_sports, R.id.checkbox_travels}) List<CheckBox> mCheckBoxList;
    @BindView(R.id.switch_enable_notifications) Switch mSwitchNotifications;

    private String mPageTitle,mQueryTermsToSetAlarm,mCategoryToJson;
    private DateTime mMyCalendar = new DateTime();
    private DatePickerDialog.OnDateSetListener mDate;
    private EditText mEditText;
    private boolean mTestExistingTerms = false;
    private ArrayList<String> mCategory = new ArrayList<>();
    private boolean mTestCheck,mState;
    private AlarmManager mAlarmManager;
    private PendingIntent mAlarmIntent;
    private DateTime mBeginDateToComparison=new DateTime();
    private DateTime mEndDateToComparison=new DateTime();
    private SharedPreferences mMySharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String QUERY_TERMS = "Query terms";
    public static final String CATEGORY = "Category";
    public static final String BEGIN_DATE = "beginDate";
    public static final String END_DATE = "endDate";
    public static final String PREFS_NAME = "MySharedPreferences";


    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        mMySharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
        mEditor = mMySharedPreferences.edit();
        this.getBundleToSetTitle();
        this.configureActivityContent();
        this.configureDatePicker();
        this.textChangedListener();
        this.listenerOnSearchButton();
        this.setAlarm();
        if(mDateOption.getVisibility()==View.INVISIBLE){
            Gson gson=new Gson();
            mSearchTerm.setText(mMySharedPreferences.getString("queryToAlarm",""));
            String categoryFromJson = mMySharedPreferences.getString("categoryToAlarm","");
            ArrayList<String> category = gson.fromJson(categoryFromJson,new TypeToken<ArrayList<String>>(){}.getType());
            if(category!=null){
                for (int i = 0; i < category.size(); i++  ) {
                    for (int j = 0; j < mCheckBoxList.size(); j++) {
                        if (mCheckBoxList.get(j).getText().toString().equals(category.get(i))) {
                            mCheckBoxList.get(j).toggle();
                        }
                    }
                }
            }
        }
        mState = mMySharedPreferences.getBoolean("switchkey", false);
        mSwitchNotifications.setChecked(mState);

        return view;
    }

    //Get into the bundle the title sent by the activity
    private void getBundleToSetTitle() {
        Bundle bundle = this.getArguments();
        mPageTitle = bundle.getString(KEY_TITLE);
    }

    //set the title of the toolbar according to what bundle is containing
    private void configureActivityContent() {
        if (mPageTitle == null || mPageTitle.contentEquals("Search Articles")) {
            mNotificationOption.setVisibility(View.INVISIBLE);
            mNotificationOption.getLayoutParams().height = 0;
            if (mPageTitle == null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search Articles");
            }
        } else {
            mDateOption.setVisibility(View.INVISIBLE);
            mDateOption.getLayoutParams().height = 0;
            mSearchButton.setVisibility(View.INVISIBLE);
            mSearchButton.getLayoutParams().height = 0;
        }
    }

    //Create a new date picker dialog, set mMycalendar with the date picked by the user, and add a listener on two editTexts
    private void configureDatePicker() {

        mDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mMyCalendar = mMyCalendar.withYear(year);
                mMyCalendar = mMyCalendar.withMonthOfYear(monthOfYear);
                mMyCalendar = mMyCalendar.withDayOfMonth(dayOfMonth);
                updateLabel(mEditText,mMyCalendar);

            }
        };
        this.dateButtonClickListener(mBeginDate);
        this.dateButtonClickListener(mEndDate);

    }

    //According to the date picked by the user in the date picker dialog, we update the label of the editText
    public void updateLabel(EditText editText,DateTime dateTime) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; //In which you need put here
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        DateTime jodatime = dtf.parseDateTime(dateTime.toString());
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yy");

        editText.setText(dtfOut.print(jodatime));
    }

    //listener for the two editText that will containing dates picked by the user
    private void dateButtonClickListener(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), mDate, mMyCalendar
                        .getYear(), mMyCalendar.getMonthOfYear(),
                        mMyCalendar.getDayOfMonth()).show();

                mEditText = editText;
            }
        });
    }

    //Add a listener on the editText to know when user add a term for research, and when he does, method set enable the search button
    private void textChangedListener() {
        mSearchTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTestExistingTerms = true;
                if(mDateOption.getVisibility()==View.INVISIBLE){
                    mQueryTermsToSetAlarm=mSearchTerm.getText().toString();
                    Log.e("ALARMTEST","les mots clés à save :"+mQueryTermsToSetAlarm);
                    mEditor.putString("queryToAlarm",mQueryTermsToSetAlarm);
                    mEditor.commit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    //When user select two dates in datePicker, we need to check if this period is a valid period
    private boolean testIfPeriodIsCorrect(){
        boolean response = true;
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yy");
        if(!mBeginDate.getText().toString().equals("Select a date")){
            mBeginDateToComparison = dtf.parseDateTime(mBeginDate.getText().toString());
        }
        if(!mEndDate.getText().toString().equals("Select a date")){
            mEndDateToComparison = dtf.parseDateTime(mEndDate.getText().toString());
        }
        int diff = (Days.daysBetween(mBeginDateToComparison , mEndDateToComparison)).getDays();
        if(!mBeginDate.getText().toString().equals("Select a date") && !mEndDate.getText().toString().equals("Select a date")) {
            if (diff <= 0) {
                response= false;
            }
        }
        return response;
    }

    //Listener to know what to do when user press the search button
    private void listenerOnSearchButton() {
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTestExistingTerms && testCheckBoxes()) {

                        if(testIfPeriodIsCorrect()){
                            transferInfosToResultActivity();
                        }else{
                            Toast.makeText(getContext(),"Not a valid period !",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                    Toast.makeText(getContext(), "Please enter a search query term and check at least one category box", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Create an intent to transfer the terms set into the editText to the ResultSearch activity
    private void transferInfosToResultActivity() {
        Intent intent = new Intent(getActivity(), ResultSearchActivity.class);
        intent.putExtra(QUERY_TERMS, mSearchTerm.getText().toString());
        intent.putStringArrayListExtra(CATEGORY, mCategory);
        intent.putExtra(BEGIN_DATE, mBeginDate.getText().toString());
        intent.putExtra(END_DATE, mEndDate.getText().toString());
        startActivity(intent);
    }

    //check if there is at least one box checked
    private boolean testCheckBoxes() {
        for (int i = 0; i < mCheckBoxList.size(); i++) {
            if (mCheckBoxList.get(i).isChecked()) {
                mCategory.add(mCheckBoxList.get(i).getText().toString());
            }
            mCheckBoxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCategory.clear();
                    for (int i = 0; i < mCheckBoxList.size(); i++) {
                        if (mCheckBoxList.get(i).isChecked()) {
                            mCategory.add(mCheckBoxList.get(i).getText().toString());
                            }
                    }
                    if(mDateOption.getVisibility()==View.INVISIBLE){
                        Gson gson = new Gson();
                        mCategoryToJson = gson.toJson(mCategory);
                        mEditor.putString("categoryToAlarm",mCategoryToJson);
                        mEditor.commit();
                        }
                }
            }
            );
            for (int j = 0; j < mCheckBoxList.size(); j++) {
                if (mCheckBoxList.get(j).isChecked()) {
                    mTestCheck = true;
                }
            }
        }
        return mTestCheck;
    }

    private void setAlarm() {
        this.testCheckBoxes();
        mSwitchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(!checkConditionToSwitch() && isChecked){
                Toast.makeText(getContext(), "Please enter a search query term and check at least one category box", Toast.LENGTH_SHORT).show();
                mSwitchNotifications.setChecked(false);
                }
                if (checkConditionToSwitch() && isChecked) {
                settingsForAlarm();
                Toast.makeText(getContext(), "Notifications enabled", Toast.LENGTH_SHORT).show();
                }
                if (!isChecked) {
                initializeNotifications();
                mTestExistingTerms=false;
                Toast.makeText(getContext(), "Notifications disabled", Toast.LENGTH_SHORT).show();
                }
                mEditor.putBoolean("switchkey", mSwitchNotifications.isChecked());
                mEditor.apply();
            }
            });
    }
    //desactivate alarm, clear editText, uncheck boxes, clear sharedpreferences
    private void initializeNotifications(){
        if (mAlarmManager != null) {
            mAlarmManager.cancel(mAlarmIntent);
        }
        mSearchTerm.getText().clear();
        for (int i = 0; i < mCheckBoxList.size(); i++) {
            if (mCheckBoxList.get(i).isChecked()) {
                mCheckBoxList.get(i).toggle();
            }
        }
        mEditor.putString("categoryToAlarm", "");
        mEditor.putString("queryToAlarm", "");
        mEditor.commit();
    }
    //method that set the alarm for notifications
    private void settingsForAlarm(){
        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);//Setting intent to class where Alarm broadcast message will be handled

        mAlarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 4);

        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, mAlarmIntent);
    }
    //check if there are all conditions needed to have the possibility to toggle the switch
    public boolean checkConditionToSwitch(){
        boolean conditionToSwitch = false;
        if((mTestExistingTerms && testCheckBoxes())||(!mMySharedPreferences.getString("queryToAlarm","").equals("") && !mMySharedPreferences.getString("categoryToAlarm","").equals(""))){
            conditionToSwitch = true;
        }
           return conditionToSwitch;
    }
}

