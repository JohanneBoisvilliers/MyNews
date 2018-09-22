package com.example.jbois.mynews.Controllers.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jbois.mynews.Controllers.Fragments.BaseFragment;

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

    //Method that create a new instance of fragment
    @Override
    public Fragment getItem(int position) {
        //Page to return
        return(BaseFragment.newInstance(position));
    }

    @Override
    public int getCount() {
        return (5);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
