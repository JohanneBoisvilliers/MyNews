package com.example.jbois.mynews;

import android.content.Context;
import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import com.bumptech.glide.RequestManager;
import com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity;
import com.example.jbois.mynews.Controllers.Adapters.ArticleAdapter;

import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.BEGIN_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.CATEGORY;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.END_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.QUERY_TERMS;
import static org.mockito.Mockito.*;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;
import com.example.jbois.mynews.Utils.NewYorkTimeService;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;

import static com.example.jbois.mynews.Utils.NewYorkTimesStreams.apiKey;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MyNewsInstrumentedTest {
    private String mSection = "sports";
    private String mSearch = "tesla";
    private String mBeginDate = "20181015";
    private String mEndDate = "20181017";
    private NewYorkTimeService mNewYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
    private News.Articles mArticle1 = new News().new Articles();
    private News.Articles mArticle2 = new News().new Articles();
    private News.Articles mArticle3 = new News().new Articles();
    private List mList = asList(mArticle1, mArticle2, mArticle3);
    private RequestManager glide;


    @Test
    public void testGetMostPopularArticles(){
        this.baseForTestRequestNews(mNewYorkTimeService.getMostViewedArticles(mSection,apiKey));
    }
    @Test
    public void testGetTopStoriesArticles(){
        this.baseForTestRequestNews(mNewYorkTimeService.getArticles(mSection,apiKey));
    }
    @Test
    public void testSearchArticles(){
        baseForTestRequestSearchResult(mNewYorkTimeService.getSearchArticles(mSearch,mSection,mBeginDate,mEndDate,"newest",apiKey));
    }
    @Test
    public void testArticleAdapter(){
        ArticleAdapter articleAdapter = new ArticleAdapter(mList,glide);
        assertEquals(3,articleAdapter.getItemCount());
    }

    private void baseForTestRequestNews(Observable<News>observable){
        //Create observer
        TestObserver<News> testObserver = new TestObserver<>();
        //subscribe to the observable
        observable.subscribeWith(testObserver).assertNoErrors().assertNoTimeout().awaitTerminalEvent();
        //get response
        News news = testObserver.values().get(0);
        //test response
        assertEquals("OK",news.getStatus());
    }

    private void baseForTestRequestSearchResult(Observable<SearchResult>observable){
        //Create observer
        TestObserver<SearchResult> testObserver = new TestObserver<>();
        //subscribe to the observable
        observable.subscribeWith(testObserver).assertNoErrors().assertNoTimeout().awaitTerminalEvent();
        //get response
        SearchResult news = testObserver.values().get(0);
        //test response
        assertEquals("OK",news.getStatus());
    }
}
