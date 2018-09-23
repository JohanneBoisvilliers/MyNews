package com.example.jbois.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jbois.mynews.Controllers.Activities.WebViewArticlesActivity;
import com.example.jbois.mynews.Controllers.Adapters.ArticleAdapter;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.ItemClickSupport;
import com.example.jbois.mynews.Utils.NewYorkTimesStreams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.CHECKBOX_CATEGORY;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.SEARCH_QUERY_TERMS;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.CATEGORY;

public class ResultSearchFragment extends BaseFragment {


    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;

    private Disposable mDisposable;
    private ArticleAdapter mAdapter;
    private String mTerms;
    private List<News.Articles> mSearchArticlesList =new ArrayList<>();
    private List<SearchResult.Doc> mTempList =new ArrayList<>();
    private ArrayList<String> mCategory=new ArrayList<>();
    private String mTermForFQParam =" ";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result= inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this,result);

        this.getSearchQueryTermsForRequest();
        this.executeHttpRequestWithRetrofit(mTerms,0,"news_desk:("+mTermForFQParam+")");
        super.configureRecyclerView(mSearchArticlesList);
        super.configureOnClickRecyclerView(mSearchArticlesList);

        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getSearchQueryTermsForRequest(){
        Bundle bundle = this. getArguments();
        mTerms=bundle.getString(SEARCH_QUERY_TERMS);
        mCategory.addAll(bundle.getStringArrayList(CHECKBOX_CATEGORY));
        this.constructPhraseAccordingToCheckboxes();
    }

    private String constructPhraseAccordingToCheckboxes(){
        for(String str : mCategory){
            mTermForFQParam +="\""+str+"\""+" ";
        }
        return mTermForFQParam;
    }

    protected void executeHttpRequestWithRetrofit(String search, int position,String category) {
        this.mDisposable = NewYorkTimesStreams.streamFetchSearchArticles(search,category).subscribeWith(new DisposableObserver<SearchResult>() {
            @Override
            public void onNext(SearchResult newslist) {
                mTempList.addAll(newslist.getResponse().getDocs());
                listConverter(mTempList,mSearchArticlesList);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onComplete() {
            }
        });
    }

    private void listConverter(List<SearchResult.Doc> docList, List<News.Articles> articlesList){
        for(int i=0;i<docList.size();i++){
            articlesList.add(new News().new Articles());
            articlesList.get(i).setTitle(docList.get(i).getHeadline().getMain());
            articlesList.get(i).setSection(docList.get(i).getSectionName());
            articlesList.get(i).setPublishedDate(docList.get(i).getPubDate());
            List<News.MediasArticles> fakeMediaList = new ArrayList<>();
            fakeMediaList.add(new News().new MediasArticles());
            articlesList.get(i).setMultimedia(fakeMediaList);
            if(docList.get(i).getMultimedia().size()>=1)
            articlesList.get(i).getMultimedia().get(0).setUrl("https://static01.nyt.com/"+docList.get(i).getMultimedia().get(1).getUrl());
            articlesList.get(i).setUrl(docList.get(i).getWebUrl());
        }
    }

    //private int browseMediaList(List<SearchResult.Doc> docList){
    //    int indexOfThumbnail=0;
    //    for(int i=0;i<docList.get(0).getMultimedia().size();i++){
    //                if(docList.get(0).getMultimedia().get(i).getSubtype().equals("thumbnail")){
    //                    indexOfThumbnail=i;
    //                    Log.e("INDEX",""+indexOfThumbnail);
    //                }
//
//
    //    }
    //    return indexOfThumbnail;
    //}
}
