package com.example.jbois.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.R;

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
    public void UpdateUINews(News news){
        this.articleTitleTextView.setText(news.getTitle());
        this.categoryTextView.setText(news.getCategory());
        this.dateTextView.setText(news.getDate());
    }
}
