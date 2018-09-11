package com.example.jbois.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewArticlesActivity extends AppCompatActivity {

    @BindView(R.id.webview) WebView myWebView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_articles);
        //Serialize views
        ButterKnife.bind(this);

        this.configureToolBar();
        this.loadUrl();
    }

    private void loadUrl(){
        //Get the Intent which contain the Url to load
        mURL = getIntent().getStringExtra("URL");
        //Set that when we ask to open an article, the opening is on a webview not into an external navigator
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                view.loadUrl(url);
                return true;
            }
        });

        myWebView.loadUrl(mURL);
    }

    private void configureToolBar(){

        //Set the toolbar
        setSupportActionBar(mToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
