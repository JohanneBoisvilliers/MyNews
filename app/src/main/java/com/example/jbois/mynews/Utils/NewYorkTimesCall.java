package com.example.jbois.mynews.Utils;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.NewsArticles;
import com.example.jbois.mynews.R;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewYorkTimesCall {

    //Creating a callback
    public interface Callbacks {
        void onResponse(News newslist);
        void onFailure();
    }
    //Public method to start fetching articles on New York Times API
    public static void fetchArticles(Callbacks callbacks,String section){
        //Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);
        //Get a Retrofit instance and the related endpoints
        NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        //Create the call on NewYork Time API
        Call<News> call = newYorkTimeService.getArticles(section);
        //Start the call
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //Call the proper callback used in controller (BaseFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //Call the proper callback used in controller (BaseFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}
