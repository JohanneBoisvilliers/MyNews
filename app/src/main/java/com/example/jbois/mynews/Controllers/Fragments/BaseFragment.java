package com.example.jbois.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jbois.mynews.Controllers.Adapters.ArticleAdapter;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    //Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private ArticleAdapter mAdapter;
    private List<News> mFinalNewsList=new ArrayList<News>();

    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;

    //Method that will create a new instance of BaseFragment, and add data to its bundle.
    public static BaseFragment newInstance(int position) {

        //Create new fragment
        BaseFragment frag = new BaseFragment();

        //Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get layout of BaseFragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);

        //Get the base view from layout and serialise it
        ButterKnife.bind(this, result);

        this.configureFakeNews();
        this.configureRecyclerView();

        return result;
    }

    //Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        //Create mAdapter passing the list of users
        this.mAdapter = new ArticleAdapter(mFinalNewsList);
        //Attach the mAdapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.mAdapter);
        //Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureFakeNews(){

        String[] titleNewsArray = getResources().getStringArray(R.array.titleFakeNews);
        String[] categoryNewsArray = getResources().getStringArray(R.array.CategoryFakeNews);
        String[] dateNewsArray = getResources().getStringArray(R.array.dateFakeNews);

        Collections.addAll(mFinalNewsList,new News(),new News(),new News(),new News(),new News());
        int i=0;
        for (News news : mFinalNewsList){
            news.setTitle(titleNewsArray[i]);
            news.setCategory(categoryNewsArray[i]);
            news.setDate(dateNewsArray[i]);
            i++;
        }
    }
}
