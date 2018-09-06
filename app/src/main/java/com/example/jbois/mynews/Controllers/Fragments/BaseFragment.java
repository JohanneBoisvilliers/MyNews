package com.example.jbois.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jbois.mynews.Controllers.Activities.WebViewArticlesActivity;
import com.example.jbois.mynews.Controllers.Adapters.ArticleAdapter;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.NewsArticles;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.ItemClickSupport;
import com.example.jbois.mynews.Utils.NewYorkTimesCall;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements NewYorkTimesCall.Callbacks{

    //Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private ArticleAdapter mAdapter;
    private List<NewsArticles> mFinalNewsList=new ArrayList<>();

    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.test_retrofit) TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get layout of BaseFragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);

        //Get the base view from layout and serialise it
        ButterKnife.bind(this, result);

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return result;
    }

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

    // --SETTINGS-- //

    //Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        //Create mAdapter passing the list of users
        this.mAdapter = new ArticleAdapter(this.mFinalNewsList, Glide.with(this));
        //Attach the mAdapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.mAdapter);
        //Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    //Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_base_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getActivity(), WebViewArticlesActivity.class);
                        String url = mFinalNewsList.get(position).getShortUrl();
                        intent.putExtra("URL",url);
                        startActivity(intent);
                    }
                });
    }
    //Method that sort the articles in recyclerView to show the most recent article at the bottom of the list
    private void sortNewsList(){
        Collections.sort(mFinalNewsList,new Comparator<NewsArticles>() {
            @Override
            public int compare(NewsArticles s1, NewsArticles s2) {
                return s1.getPublishedDate().compareToIgnoreCase(s2.getPublishedDate());
            }
        });
        Collections.reverse(mFinalNewsList);
    }

    // --HTTP REQUEST-- //

    // Execute HTTP request
   private void executeHttpRequestWithRetrofit(){
       NewYorkTimesCall.fetchArticles(this,"home");
   }
   @Override
   // On request response, that method update the recyclerView's items
   public void onResponse(News newslist) {
        this.updateUI(newslist);
   }
   @Override
   public void onFailure(){}

    // --UPDATE UI-- //

    //get the list of articles from request response, add elements into a list, and notify to the adapter that some data as changed
    private void updateUI(News newsList){
        List<NewsArticles> articles = newsList.getResults();
        mFinalNewsList.addAll(articles);
        this.sortNewsList();
        mAdapter.notifyDataSetChanged();
    }
}
