package com.example.jbois.mynews.Views;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Models.NewsArticles;
import com.example.jbois.mynews.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.article_title) TextView articleTitleTextView;
    @BindView(R.id.category) TextView categoryTextView;
    @BindView(R.id.date) TextView dateTextView;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    //method that update the interface of viewholder
    public void UpdateUINews(NewsArticles news){
        this.articleTitleTextView.setText(news.getTitle());
        this.categoryTextView.setText(news.getSection()+">"+news.getSubsection());
        this.ConvertDate(news);
    }
    //Method that convert ISO date received by the API into date time
    private void ConvertDate (NewsArticles news){
        String dateToConvert = news.getPublishedDate();
        // Format for input
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        // Parsing the date
        DateTime jodatime = dtf.parseDateTime(dateToConvert);
        // Format for output
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yy");
        // Printing the date
        this.dateTextView.setText(dtfOut.print(jodatime));
    }
}
