package com.example.jbois.mynews.Controllers.Adapters;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jbois.mynews.Controllers.Fragments.BaseFragment;
import com.example.jbois.mynews.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PageAdapter extends FragmentStatePagerAdapter {
    private String[] mTitlesOfPageVP;
    //Constructor
    public PageAdapter(FragmentManager mgr,String[] strings) {
        super(mgr);
        this.mTitlesOfPageVP=strings;
    }
    //Method that set the title of each page
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlesOfPageVP[position];
    }

    //Method that get the position of the actual page in the viewpager
    @Override
    public Fragment getItem(int position) {
        //Page to return
        return(BaseFragment.newInstance(position));
    }

    @Override
    public int getCount() {
        return (5);
    }
}
