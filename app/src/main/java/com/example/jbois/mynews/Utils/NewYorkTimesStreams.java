package com.example.jbois.mynews.Utils;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.NewsArticles;
import com.example.jbois.mynews.R;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewYorkTimesStreams {

        public static Observable<News> streamFetchTopStoriesArticles(String section){
            NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
            return newYorkTimeService.getArticles(section)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS);
        }
}
