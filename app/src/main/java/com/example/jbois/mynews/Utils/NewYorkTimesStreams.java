package com.example.jbois.mynews.Utils;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;

import java.util.concurrent.TimeUnit;

import butterknife.Optional;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStreams {

    public static final String apiKey = "4e02cd95f9de4ade810f04de4c8f51ff";
        //Observable to fetch top stories articles
        public static Observable<News> streamFetchTopStoriesArticles(String section){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getArticles(section,apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }
        //observable to fetch most viewed articles
        public static Observable<News> streamFetchMostViewedArticles(String section){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getMostViewedArticles(section,apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }
        //observable to fetch articles in search api
        public static Observable<SearchResult> streamFetchSearchArticles(String search, String category, String beginDate, String endDate){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getSearchArticles(search,category,beginDate,endDate,"newest",apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }
}
