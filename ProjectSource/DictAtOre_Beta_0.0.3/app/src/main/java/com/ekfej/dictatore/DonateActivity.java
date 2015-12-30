package com.ekfej.dictatore;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DonateActivity extends Activity {
    WebView donweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        donweb = (WebView) findViewById(R.id.donweb);
        WebSettings webSettings = donweb.getSettings();
        //donweb.setWebChromeClient(new WebChromeClient());
        //donweb.loadUrl("file:///android_asset/www/donate.html");
        donweb.loadUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5LLEWCSV23RW8");
        //donweb.loadUrl("http://www.google.com");

        webSettings.setBuiltInZoomControls(true);
        // Enable JavaScript
        webSettings.setJavaScriptEnabled(true);

        //Let links appear in this WebView
        donweb.setWebViewClient(new WebViewClient());
    }

}
