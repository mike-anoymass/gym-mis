package com.example.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WebViewBrowser extends Activity {
    WebView webView;
    EditText urlText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview);

        initialize();

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true); // allow html meta tag viewport
        webView.getSettings().setLoadWithOverviewMode(true); //allow zooming

        ActionBar bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

    }

    public void initialize() {
        webView = (WebView) findViewById(R.id.webViewBrowser);
        urlText = (EditText) findViewById(R.id.urlText);
    }

    public void goBtnClicked(View view) {
        webView.loadUrl("http://" + urlText.getText().toString());

        //hide keyboard after using edit Text
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(urlText.getWindowToken(), 0);
    }

    public void backBtnClicked(View view) {
        if (webView.canGoBack()) webView.goBack();
    }

    public void forwardBtnClicked(View view) {
        if (webView.canGoForward()) webView.goForward();
    }

    public void refreshBtnClicked(View view) {
        webView.reload();
    }

    public void clearBtnClicked(View view) {
        webView.clearHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                Intent i = new Intent("com.example.myfirstapp.ABOUT");
                startActivity(i);
                break;
            case R.id.preference:
                Intent p = new Intent("com.example.myfirstapp.PREFERENCE");
                startActivity(p);
                break;

            case R.id.exit:
                finish();
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }
}
