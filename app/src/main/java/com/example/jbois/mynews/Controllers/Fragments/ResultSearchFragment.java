package com.example.jbois.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jbois.mynews.Controllers.Activities.WebViewArticlesActivity;
import com.example.jbois.mynews.Controllers.Adapters.ArticleAdapter;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Utils.ItemClickSupport;
import com.example.jbois.mynews.Utils.NewYorkTimesStreams;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.CHECKBOX_CATEGORY;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.GET_BEGIN_DATE;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.GET_END_DATE;
import static com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity.SEARCH_QUERY_TERMS;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.CATEGORY;

public class ResultSearchFragment extends BaseFragment {


    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;

    private Disposable mDisposable;
    private ArticleAdapter mAdapter;
    private String mTerms,mBeginDate,mEndDate,mTempBeginDate,mTempEndDate;
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
        mBeginDate=this.convertDateToRequest(mTempBeginDate);
        mEndDate=this.convertDateToRequest(mTempEndDate);
        this.executeHttpRequestWithRetrofit(mTerms,0,"news_desk:("+mTermForFQParam+")",mBeginDate,mEndDate);
        super.configureRecyclerView(mSearchArticlesList);
        super.configureOnClickRecyclerView(mSearchArticlesList);

        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //get infos to know what to put in request url
    private void getSearchQueryTermsForRequest(){
        Bundle bundle = this. getArguments();
        mTerms=bundle.getString(SEARCH_QUERY_TERMS);
        mCategory.addAll(bundle.getStringArrayList(CHECKBOX_CATEGORY));
        mTempBeginDate=bundle.getString(GET_BEGIN_DATE);
        mTempEndDate=bundle.getString(GET_END_DATE);
        this.constructPhraseAccordingToCheckboxes();
    }
    //after picking the dates sent by activity we have to convert them to have a yyyyMMdd format for request
    private String convertDateToRequest(String dateToConvert){
        String pattern = "dd/MM/yy";
        if(!dateToConvert.equals("Select a date")){
            DateTimeFormatter dtf =  DateTimeFormat.forPattern(pattern);
            DateTime jodatime = dtf.parseDateTime(dateToConvert);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyyMMdd");
            dateToConvert=dtfOut.print(jodatime);
        }else{
            dateToConvert=null;
        }
        return dateToConvert;
    }

    //API need a particular format to search in customs category(sports...Arts..)so we format a string for request
    private String constructPhraseAccordingToCheckboxes(){
        for(String str : mCategory){
            mTermForFQParam +="\""+str+"\""+" ";
        }
        return mTermForFQParam;
    }
    //execute the request according to parameters picked by the user
    protected void executeHttpRequestWithRetrofit(String search, int position, String category, String beginDate, String endDate) {
        this.mDisposable = NewYorkTimesStreams.streamFetchSearchArticles(search,category,beginDate,endDate).subscribeWith(new DisposableObserver<SearchResult>() {
            @Override
            public void onNext(SearchResult newslist) {
                if (newslist.getResponse().getDocs().size() == 0) {
                    Toast.makeText(getContext(),"There's no articles corresponding to your research",Toast.LENGTH_LONG).show();
                }
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
    //Search API return a different json object than topStories and mostPopular, but we want to keep the same adapter and prototype,
    // so that method convert Search API object into a topStories object like.
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
