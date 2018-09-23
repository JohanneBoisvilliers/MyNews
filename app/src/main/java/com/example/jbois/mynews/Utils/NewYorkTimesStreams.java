package com.example.jbois.mynews.Utils;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStreams {

    public static final String apiKey = "4e02cd95f9de4ade810f04de4c8f51ff";

        public static Observable<News> streamFetchTopStoriesArticles(String section){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getArticles(section,apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }

        public static Observable<News> streamFetchMostViewedArticles(String section){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getMostViewedArticles(section,apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }

        public static Observable<SearchResult> streamFetchSearchArticles(String search,String category){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getSearchArticles(search,category,"newest",apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }
}
