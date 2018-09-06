package com.example.jbois.mynews.Utils;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.NewsArticles;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewYorkTimeService {
    // Set the url to request
    @GET("svc/topstories/v2/{section}.json?api-key=4e02cd95f9de4ade810f04de4c8f51ff")
    Call<News> getArticles(@Path("section")String section);
    // Set a listener to know all about request
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
    //New instance of retrofit to set the endpoint and the Gson converter
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
}
