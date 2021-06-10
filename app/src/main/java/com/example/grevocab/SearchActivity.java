package com.example.grevocab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    EditText searchText;
    Button searchButton;
    WebView webView;

    Button wordActivity,bookmarkActivity,profileActivity;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        wordActivity=findViewById(R.id.words);
        bookmarkActivity=findViewById(R.id.bookmark);
        profileActivity=findViewById(R.id.orders);
        searchText=findViewById(R.id.searchEditText);
        searchButton=findViewById(R.id.search);
        webView=findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());


        searchButton.setOnClickListener(v -> {
            String url = "https://www.google.com/search?q="+searchText.getText().toString();
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(url);
        });

        wordActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        bookmarkActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this,BookmarksActivity.class);
                startActivity(intent);
            }
        });
        profileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


    }

    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}