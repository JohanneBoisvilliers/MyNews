package com.example.jbois.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.notifications_search_option) View mNotificationOption;
    @BindView(R.id.date_options) View mDateOption;
    @BindView(R.id.button_search) Button mSearchButton;

    private String mPageTitle;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);

        Bundle bundle = this.getArguments();
        mPageTitle = bundle.getString("TITLE");

        this.configureActivityContent();
        return view;
    }

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

}
