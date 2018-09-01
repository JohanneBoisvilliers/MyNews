package com.example.jbois.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jbois.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class WebViewArticlesActivity extends AppCompatActivity {

    @BindView(R.id.webview) WebView myWebView;

    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_articles);
        //Serialize views
        ButterKnife.bind(this);

        URL = getIntent().getStringExtra("URL");
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                view.loadUrl(url);
                return true;
            }
        });

        myWebView.loadUrl(URL);
        myWebView.getSettings().setJavaScriptEnabled(true);
    }
}
