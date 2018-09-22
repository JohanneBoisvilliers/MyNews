package com.example.jbois.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jbois.mynews.Controllers.Adapters.ArticleAdapter;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.NewYorkTimesStreams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.SEARCH_QUERY_TERMS;

public class ResultSearchFragment extends BaseFragment {


    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;

    private Disposable mDisposable;
    private ArticleAdapter mAdapter;
    private String mTerms;
    private List<News.Articles> mSearchArticlesList =new ArrayList<>();
    private List<SearchResult.Doc> mTempList =new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result= inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this,result);

        this.getSearchQueryTermsForRequest();
        this.executeHttpRequestWithRetrofit(mTerms,0);
        super.configureRecyclerView(mSearchArticlesList);

        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getSearchQueryTermsForRequest(){
        Bundle bundle = this. getArguments();
        mTerms=bundle.getString(SEARCH_QUERY_TERMS);
    }

    protected void executeHttpRequestWithRetrofit(String search, int position) {
        this.mDisposable = NewYorkTimesStreams.streamFetchSearchArticles(search).subscribeWith(new DisposableObserver<SearchResult>() {
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

    private void listConverter(List<SearchResult.Doc> docList,List<News.Articles> articlesList){
        for(int i=0;i<docList.size();i++){
            articlesList.add(new News().new Articles());
            articlesList.get(i).setTitle(docList.get(i).getHeadline().getMain());
            articlesList.get(i).setSection(docList.get(i).getSectionName());
            articlesList.get(i).setPublishedDate(docList.get(i).getPubDate());
            List<News.MediasArticles> fakeMediaList = new ArrayList<>();
            fakeMediaList.add(new News().new MediasArticles());
            articlesList.get(i).setMultimedia(fakeMediaList);
            if(docList.get(i).getMultimedia().size()>=1)
                articlesList.get(i).getMultimedia().get(0).setUrl("https://static01.nyt.com/"+docList.get(i).getMultimedia().get(2).getUrl());
            articlesList.get(i).setShortUrl(docList.get(i).getWebUrl());
        }
    }
}
