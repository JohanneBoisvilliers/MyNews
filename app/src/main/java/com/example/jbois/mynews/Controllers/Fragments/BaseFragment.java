package com.example.jbois.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.ItemClickSupport;
import com.example.jbois.mynews.Utils.NewYorkTimesStreams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment{

    //Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private ArticleAdapter mAdapter;
    private List<News.Articles> mTopStoriesList =new ArrayList<>();
    private List<News.Articles> mMostViewedList =new ArrayList<>();
    private Disposable mDisposable;

    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get layout of BaseFragment
        View result = inflater.inflate(R.layout.fragment_base, container, false);
        //Get the base view from layout and serialise it
        ButterKnife.bind(this, result);

        this.configureRecyclerView(mTopStoriesList);
        this.configureOnClickRecyclerView();
        this.getPositionToRequest();

        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
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
    private void configureRecyclerView(List list){
        //Create mAdapter passing the list of users
        this.mAdapter = new ArticleAdapter(list, Glide.with(this));
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
                        String url = mTopStoriesList.get(position).getShortUrl();
                        intent.putExtra("URL",url);
                        startActivity(intent);
                    }
                });
    }
    //Method that sort the articles in recyclerView to show the most recent article at the bottom of the list
    private void sortNewsList(List list){
        Collections.sort(list,new Comparator<News.Articles>() {
            @Override
            public int compare(News.Articles s1, News.Articles s2) {
                return s1.getPublishedDate().compareToIgnoreCase(s2.getPublishedDate());
            }
        });
        Collections.reverse(list);
    }

    private void getPositionToRequest(){
        Bundle bundle = this.getArguments();
        int position = bundle.getInt(KEY_POSITION);
        this.executeHttpRequestWithRetrofit(getResources().getStringArray(R.array.requestList)[position],position);
    }

    // --HTTP REQUEST-- //

    // Execute HTTP request
   private void executeHttpRequestWithRetrofit(String section, final int position){
        if (position==1){
            this.mDisposable = NewYorkTimesStreams.streamFetchMostViewedArticles(section).subscribeWith(new DisposableObserver<News>() {
                @Override
                public void onNext(News newslist) {
                    updateUI(newslist,position,mMostViewedList);
                }
                @Override
                public void onError(Throwable e) {
                }
                @Override
                public void onComplete() {
                }
            });
        }else{
            // Execute the stream subscribing to Observable defined inside GithubStream
            this.mDisposable = NewYorkTimesStreams.streamFetchTopStoriesArticles(section).subscribeWith(new DisposableObserver<News>() {
                @Override
                public void onNext(News newslist) {
                    updateUI(newslist,position,mTopStoriesList);
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }
            });
        }
   }

   private void disposeWhenDestroy(){
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }
    // --UPDATE UI-- //

    //get the list of articles from request response, add elements into a list, and notify to the adapter that some data as changed
    private void updateUI(News newsList,int position,List list){
        if(position==1){
            this.settingListToUpdateUI(newsList,list);
            this.configureRecyclerView(mMostViewedList);
        }else{
            this.settingListToUpdateUI(newsList,list);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void settingListToUpdateUI(News newsList,List list){
        List<News.Articles>articles = newsList.getResults();
        list.addAll(articles);
        this.sortNewsList(list);
    }
}
