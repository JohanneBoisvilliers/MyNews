package com.example.jbois.mynews.Models;

public class News {
    private String mTitle;
    private String mDate;
    private String mCategory;

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setCategory(String category) {
        mCategory = category;
    }
}
