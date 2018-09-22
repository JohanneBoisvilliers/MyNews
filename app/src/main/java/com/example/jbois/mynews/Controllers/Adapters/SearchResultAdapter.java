package com.example.jbois.mynews.Controllers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jbois.mynews.R;
import com.example.jbois.mynews.Views.ResultSearchViewHolder;

public class SearchResultAdapter extends RecyclerView.Adapter<ResultSearchViewHolder> {
    @NonNull
    @Override
    public ResultSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_base_item, parent, false);

        return new ResultSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultSearchViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
