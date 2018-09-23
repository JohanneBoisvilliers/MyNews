package com.example.jbois.mynews.Utils;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.SearchResult;

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

    // Set the urls to request
    @GET("svc/topstories/v2/{section}.json")
    Observable<News> getArticles(
            @Path("section")String section,
            @Query("api-key") String apiKey);
    @GET("svc/mostpopular/v2/mostviewed/{section}/7.json")
    Observable<News> getMostViewedArticles(
            @Path("section")String section,
            @Query("api-key") String apiKey);
    @GET("svc/search/v2/articlesearch.json")
    Observable<SearchResult> getSearchArticles(
            @Query("q") String section,
            @Query("fq")String category,
            @Query("sort") String sort,
            @Query("api-key") String apiKey);

    // Set a listener to know all about requests
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
