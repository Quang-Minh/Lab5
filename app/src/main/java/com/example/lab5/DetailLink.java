package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailLink extends AppCompatActivity {
    WebView webView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_link);
        webView = findViewById(R.id.webView);
        intent = getIntent();
        String link = intent.getStringExtra("linkURl");
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}