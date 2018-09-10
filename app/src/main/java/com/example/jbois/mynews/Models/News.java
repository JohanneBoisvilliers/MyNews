package com.example.jbois.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("results")
    @Expose
    private List<Articles> results = null;

    public List<Articles> getResults() {
        return results;
    }

    public class Articles{
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("multimedia")
        @Expose
        private List<MediasArticles> multimedia = null;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("short_url")
        @Expose
        private String shortUrl;
        @SerializedName("media")
        @Expose
        private List<MediasArticles> media = null;

        public String getSection () {
            return section; }
        public String getSubsection() {
            return subsection;
        }
        public String getTitle() {
            return title;
        }
        public String getPublishedDate() {
            return publishedDate;
        }
        public String getShortUrl() {
            return shortUrl;
        }
        public List<MediasArticles> getMultimedia() {
            return multimedia;
        }
        public List<MediasArticles> getMedia() {
            return media;
        }
    }

    public class MediasArticles {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }
        @SerializedName("media-metadata")
        @Expose
        private List<MediaMetada> mediaMetadata = null;

        public List<MediaMetada> getMediaMetadata() {
            return mediaMetadata;
        }
    }

    public class MediaMetada {
        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }
    }
    }
