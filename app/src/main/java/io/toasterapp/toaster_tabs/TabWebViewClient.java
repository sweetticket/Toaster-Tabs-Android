package io.toasterapp.toaster_tabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TabWebViewClient extends WebViewClient {

    Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        Log.d("url", url);

//        Log.d("url", "shouldOverrideUrlLoading called");


        view.loadUrl(url);

        return false;
    }

    @Override
    public void onLoadResource(WebView  view, String  url){
//        Log.d("url", url);
//        if( url.equals("http://cnn.com") ){
//            // do whatever you want
//        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

//        if (url.contains("post")){
//            Log.d("url", url);
//            Intent intent = new Intent(mContext, PostShowActivity.class);
//            intent.putExtra("url", url);
//            mContext.startActivity(intent);
//        }

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        Log.d("url", url);
        // Display the keyboard automatically when relevant
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (url.contains("newPost")) {
//            imm.showSoftInput(view, 0);
        } else {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//        if (url.contains("post")){
//            Log.d("url", url);
////            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            Intent intent = new Intent(mContext, PostShowActivity.class);
//            intent.putExtra("url", url);
//            mContext.startActivity(intent);
//
//        }
//        view.loadUrl("javascript:myjsi.toPostShow(window.location.href);");
        super.onPageFinished(view, url);
    }
}
