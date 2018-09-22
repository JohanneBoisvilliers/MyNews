package com.example.jbois.mynews.Views;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.article_image) ImageView imageOfArticle;
    @BindView(R.id.article_title) TextView articleTitleTextView;
    @BindView(R.id.category) TextView categoryTextView;
    @BindView(R.id.date) TextView dateTextView;
    private String mUrl;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    //method that update the interface of viewholder
    public void UpdateUINews(News.Articles news, RequestManager glide){
        // Check if there is an image thumbnail link for this article and set the image if it does
        if(news.getMultimedia()!=null && news.getMultimedia().size()>=1){
            glide.load(news.getMultimedia().get(0).getUrl()).apply(RequestOptions.centerInsideTransform()).into(imageOfArticle);
        }else if (news.getMedia()!=null && news.getMedia().size()>=1){
            glide.load(news.getMedia().get(0).getMediaMetadata().get(1).getUrl()).apply(RequestOptions.centerInsideTransform()).into(imageOfArticle);
        }
        this.articleTitleTextView.setText(news.getTitle());
        if(news.getSubsection()==null){
            this.categoryTextView.setText(news.getSection());
        }else if(news.getSubsection().isEmpty()){
            this.categoryTextView.setText(news.getSection());
        }else{
            this.categoryTextView.setText(news.getSection()+" > "+news.getSubsection());
        }
        this.ConvertDate(news);
        this.mUrl = news.getShortUrl();
    }
    //Method that convert ISO date received by the API into date time
    private void ConvertDate (News.Articles news){
        // Get the date into json file
        String dateToConvert = news.getPublishedDate();
        if(isValidDate(dateToConvert)) {
            String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
            this.SettingConversionDate(pattern,dateToConvert);
        }else{//same thing with another date time format
            String pattern = "yyyy-MM-dd";
            this.SettingConversionDate(pattern,dateToConvert);
        }
    }
    //NewYorkTimes API deliver two date format so we check the date format to know how to treat the datas
    boolean isValidDate(String dateToValidate) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        try {
            // Set format for input
            DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
            // Parsing the date to convert
            fmt.parseDateTime(dateToValidate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void SettingConversionDate(String pattern, String dateToConvert){
        // Set format for input
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        // Parsing the date to convert
        DateTime jodatime = dtf.parseDateTime(dateToConvert);
        // Set format for output
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yy");
        // Set the text of the view's containing the date with the new formated date
        this.dateTextView.setText(dtfOut.print(jodatime));
    }
}
