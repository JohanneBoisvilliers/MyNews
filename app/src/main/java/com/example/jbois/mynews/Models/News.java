package com.example.jbois.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("results")
    @Expose
    private List<Articles> results = null;
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

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
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("media")
        @Expose
        private List<MediasArticles> media = null;

        public String getSection () { return section; }
        public void setSection(String section) {
            this.section=section;
        }
        public String getSubsection() {
            return subsection;
        }
        public void setSubsection(String subsection) { this.subsection = subsection; }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title=title;
        }
        public String getPublishedDate() {
            return publishedDate;
        }
        public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }
        public String getShortUrl() {
            return shortUrl;
        }
        public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }
        public List<MediasArticles> getMultimedia() {
            return multimedia;
        }
        public void setMultimedia(List<MediasArticles> multimedia) { this.multimedia = multimedia; }
        public List<MediasArticles> getMedia() {
            return media;
        }
        public void setMedia(List<MediasArticles> media) { this.media = media; }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class MediasArticles {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

    public class Response{

        @SerializedName("docs")
        @Expose
        private List<Doc> docs = null;
        @SerializedName("meta")
        @Expose
        private Meta meta;

        public List<Doc> getDocs() {
            return docs;
        }

        public void setDocs(List<Doc> docs) {
            this.docs = docs;
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

    }

    public class Doc{
        @SerializedName("web_url")
        @Expose
        private String webUrl;
        @SerializedName("snippet")
        @Expose
        private String snippet;
        @SerializedName("lead_paragraph")
        @Expose
        private String leadParagraph;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("print_page")
        @Expose
        private String printPage;
        @SerializedName("blog")
        @Expose
        private List<String> blog = null;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("headline")
        @Expose
        private Headline headline;
        @SerializedName("keywords")
        @Expose
        private Keywords keywords;
        @SerializedName("pub_date")
        @Expose
        private String pubDate;
        @SerializedName("document_type")
        @Expose
        private String documentType;
        @SerializedName("news_desK")
        @Expose
        private String newsDesK;
        @SerializedName("section_name")
        @Expose
        private String sectionName;
        @SerializedName("subsection_name")
        @Expose
        private String subsectionName;
        @SerializedName("byline")
        @Expose
        private Byline byline;
        @SerializedName("type_of_material")
        @Expose
        private String typeOfMaterial;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("word_count")
        @Expose
        private String wordCount;
        @SerializedName("slideshow_credits")
        @Expose
        private String slideshowCredits;
        @SerializedName("multimedia")
        @Expose
        private List<MediasArticles> multimedia = null;

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public String getLeadParagraph() {
            return leadParagraph;
        }

        public void setLeadParagraph(String leadParagraph) {
            this.leadParagraph = leadParagraph;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
        }

        public String getPrintPage() {
            return printPage;
        }

        public void setPrintPage(String printPage) {
            this.printPage = printPage;
        }

        public List<String> getBlog() {
            return blog;
        }

        public void setBlog(List<String> blog) {
            this.blog = blog;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Headline getHeadline() {
            return headline;
        }

        public void setHeadline(Headline headline) {
            this.headline = headline;
        }

        public Keywords getKeywords() {
            return keywords;
        }

        public void setKeywords(Keywords keywords) {
            this.keywords = keywords;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        public String getNewsDesK() {
            return newsDesK;
        }

        public void setNewsDesK(String newsDesK) {
            this.newsDesK = newsDesK;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getSubsectionName() {
            return subsectionName;
        }

        public void setSubsectionName(String subsectionName) {
            this.subsectionName = subsectionName;
        }

        public Byline getByline() {
            return byline;
        }

        public void setByline(Byline byline) {
            this.byline = byline;
        }

        public String getTypeOfMaterial() {
            return typeOfMaterial;
        }

        public void setTypeOfMaterial(String typeOfMaterial) {
            this.typeOfMaterial = typeOfMaterial;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWordCount() {
            return wordCount;
        }

        public void setWordCount(String wordCount) {
            this.wordCount = wordCount;
        }

        public String getSlideshowCredits() {
            return slideshowCredits;
        }

        public void setSlideshowCredits(String slideshowCredits) {
            this.slideshowCredits = slideshowCredits;
        }

        public List<MediasArticles> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<MediasArticles> multimedia) {
            this.multimedia = multimedia;
        }

    }

    public class Meta{
        @SerializedName("hits")
        @Expose
        private Integer hits;
        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("offset")
        @Expose
        private Integer offset;

        public Integer getHits() {
            return hits;
        }

        public void setHits(Integer hits) {
            this.hits = hits;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }}

    public class Byline{
    @SerializedName("organization")
    @Expose
    private String organization;
        @SerializedName("original")
        @Expose
        private String original;
        @SerializedName("person")
        @Expose
        private List<String> person = null;

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        public List<String> getPerson() {
            return person;
        }

        public void setPerson(List<String> person) {
            this.person = person;
        }
    }

    public class Headline{
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("kicker")
        @Expose
        private String kicker;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getKicker() {
            return kicker;
        }

        public void setKicker(String kicker) {
            this.kicker = kicker;
        }

    }

    public class Keywords{
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    }
