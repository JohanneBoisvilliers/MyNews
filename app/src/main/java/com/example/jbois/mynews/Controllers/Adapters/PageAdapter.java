package com.example.jbois.mynews.Controllers.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jbois.mynews.Controllers.Fragments.BaseFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    //Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }
    //Method that set the title of each page
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page "+position;
    }

    //Method that get the position of the actual page in the viewpager
    @Override
    public Fragment getItem(int position) {
        //Page to return
        return(BaseFragment.newInstance(position));
    }

    @Override
    public int getCount() {
        return (8);
    }
}
