package com.example.jbois.mynews.Utils;

import com.example.jbois.mynews.Models.News;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewYorkTimeService {
    // Set the url to request
    @GET("svc/topstories/v2/{section}.json?api-key=4e02cd95f9de4ade810f04de4c8f51ff")
    Observable<News> getArticles(@Path("section")String section);
    @GET("svc/mostpopular/v2/mostviewed/{section}/7.json?api-key=4e02cd95f9de4ade810f04de4c8f51ff")
    Observable<News> getMostViewedArticles(@Path("section")String section);
    @GET("svc/search/v2/articlesearch.json?q={search}&sort=newest&api-key=4e02cd95f9de4ade810f04de4c8f51ff")
    Observable<News> getSearchArticles(@Path("search")String section);

    // Set a listener to know all about request
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    //New instance of retrofit to set the endpoint,the Gson converter and the RxJava adapter
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
}
