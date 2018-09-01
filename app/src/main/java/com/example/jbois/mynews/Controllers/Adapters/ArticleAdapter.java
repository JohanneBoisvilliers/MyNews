package com.example.jbois.mynews.Controllers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Views.ArticleViewHolder;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<News> mNews;

    public ArticleAdapter(List<News> news) {
        mNews = news;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_base_item, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder viewHolder,int position) {
        viewHolder.UpdateUINews(this.mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
