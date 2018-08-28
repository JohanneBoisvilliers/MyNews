package com.example.jbois.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    //Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private static final String KEY_COLOR="color";

    @BindView(R.id.fragment_base_rootview) LinearLayout baseView;
    @BindView(R.id.base_fragment_title) TextView textView;

    public BaseFragment() { }
    //Method that will create a new instance of BaseFragment, and add data to its bundle.
    public static BaseFragment newInstance(int position, int color) {

        //Create new fragment
        BaseFragment frag = new BaseFragment();

        //Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        frag.setArguments(args);

        return(frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get layout of BaseFragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);

        //Get the base view from layout and serialise it
        ButterKnife.bind(this, result);

        //Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);

        //Update base view with it
        baseView.setBackgroundColor(color);
        textView.setText("Page numéro "+position);

        return result;
    }

}
