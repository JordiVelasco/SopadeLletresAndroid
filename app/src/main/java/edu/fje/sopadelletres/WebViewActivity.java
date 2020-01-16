package edu.fje.sopadelletres;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;


    public class WebViewActivity extends SopaDeLetras {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.webview);

            webView = (WebView) findViewById(R.id.webview);
            webView.loadUrl("file:///android_asset/index.html");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);

        }

        public void web(View view){
            webView.loadUrl("https://en.wikipedia.org/wiki/Word_search");

        }



    }

