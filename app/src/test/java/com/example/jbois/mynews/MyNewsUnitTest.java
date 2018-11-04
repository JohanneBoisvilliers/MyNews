package com.example.jbois.mynews;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.jbois.mynews.Controllers.Activities.ResultSearchActivity;
import com.example.jbois.mynews.Controllers.Fragments.BaseFragment;
import com.example.jbois.mynews.Controllers.Fragments.ResultSearchFragment;
import com.example.jbois.mynews.Models.News;
import com.example.jbois.mynews.Views.ArticleViewHolder;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.BEGIN_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.CATEGORY;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.END_DATE;
import static com.example.jbois.mynews.Controllers.Fragments.SearchFragment.QUERY_TERMS;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MyNewsUnitTest {

    private BaseFragment mBaseFragment = new BaseFragment();
    private ResultSearchFragment mResultSearchFragment = new ResultSearchFragment();
    private News.Articles mArticle1 = new News().new Articles();
    private News.Articles mArticle2 = new News().new Articles();
    private News.Articles mArticle3 = new News().new Articles();
    private List mList = asList(mArticle1, mArticle2, mArticle3);

    //Test if articles are sorted to have the newest to the top of recycler view
    @Test
    public void testSortNewsList() {
        mArticle1.setPublishedDate("2018-10-05T10:52:01-04:00");
        mArticle2.setPublishedDate("2018-09-05T10:52:01-04:00");
        mArticle3.setPublishedDate("2018-10-01T10:52:01-04:00");

        List expectedList = asList(mArticle1, mArticle3, mArticle2);
        mBaseFragment.sortNewsList(mList);
        assertEquals(expectedList, mList);
    }
    //SearchArticles API need a date Format to make request so we have to test if the date conversion is OK
    @Test
    public void testConvertDateToRequest() {
        String date = "08/10/18";
        String dateNull = "Select a date";
        String actual = mResultSearchFragment.convertDateToRequest(date);
        String actualNull = mResultSearchFragment.convertDateToRequest(dateNull);
        String expected = "20181008";
        String expectedNull = null;
        assertEquals(expected, actual);
        assertEquals(expectedNull, actualNull);
    }
    @Test
    public void testconstructPhraseAccordingToCheckboxes(){
        List list = asList("banane","poire","kiwi");
        ArrayList<String> mCategory = new ArrayList<>();
        mCategory.addAll(list);
        String actual = mResultSearchFragment.constructPhraseAccordingToCheckboxes(mCategory);
        String expected = "\"banane\" \"poire\" \"kiwi\" ";
        assertEquals(expected,actual);
    }
}