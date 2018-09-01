package com.example.jbois.mynews.Models;

public class News {
    private String mTitle;
    private String mDate;
    private String mCategory;
    private String mURL;

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

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }
}
