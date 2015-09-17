package com.honeyjam.toaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TabWebViewClient extends WebViewClient {

    Context mContext;
    View mWheel;

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setWheel(View wheel) { this.mWheel = wheel; }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {


        view.loadUrl(url);

        return false;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        ((MainActivity) mContext).checkIfLoaded();
        super.onPageFinished(view, url);
    }
}
