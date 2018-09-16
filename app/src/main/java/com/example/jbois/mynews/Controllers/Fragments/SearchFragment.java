package com.example.jbois.mynews.Controllers.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.jbois.mynews.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
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

    private String mPageTitle;
    private DateTime mMyCalendar = new DateTime();
    private DatePickerDialog.OnDateSetListener mDate;
    private EditText mEditText;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        mSearchButton.setEnabled(false);
        this.getBundleToSetTitle();
        this.configureActivityContent();
        this.configureDatePicker();
        this.textChangedListener();
        return view;
    }
    //Get into the bundle the title sent by the activity
    private void getBundleToSetTitle(){
        Bundle bundle = this.getArguments();
        mPageTitle = bundle.getString(KEY_TITLE);
    }
    //set the title of the toolbar according to what bundle is containing
    private void configureActivityContent(){
        if(mPageTitle.contentEquals("Search Articles")){
            mNotificationOption.setVisibility(View.INVISIBLE);
            mNotificationOption.getLayoutParams().height=0;
        }else{
            mDateOption.setVisibility(View.INVISIBLE);
            mDateOption.getLayoutParams().height=0;
            mSearchButton.setVisibility(View.INVISIBLE);
            mSearchButton.getLayoutParams().height=0;
        }
    }
    //Create a new date picker dialog, set mMycalendar with the date picked by the user, and add a listener on two editTexts
    private void configureDatePicker(){

         mDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mMyCalendar=mMyCalendar.withYear(year);
                mMyCalendar=mMyCalendar.withMonthOfYear(monthOfYear);
                mMyCalendar=mMyCalendar.withDayOfMonth(dayOfMonth);
                updateLabel(mEditText);
            }
        };
        this.dateButtonClickListener(mBeginDate);
        this.dateButtonClickListener(mEndDate);

    }
    //According to the date picked by the user in the date picker dialog, we update the label of the editText
    private void updateLabel(EditText editText) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; //In which you need put here
        DateTimeFormatter dtf =  DateTimeFormat.forPattern(pattern);
        DateTime jodatime = dtf.parseDateTime(mMyCalendar.toString());
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yy");

        editText.setText(dtfOut.print(jodatime));
    }
    //listener for the two editText that will containing dates picked by the user
    private void dateButtonClickListener(final EditText editText){
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), mDate,mMyCalendar
                        .getYear(), mMyCalendar.getMonthOfYear(),
                        mMyCalendar.getDayOfMonth()).show();

                mEditText=editText;
            }
        });
    }
    //Add a listener on the editText to know when user add a term for research, and when he does, method set enable the search button
    private void textChangedListener(){
        mSearchTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}
