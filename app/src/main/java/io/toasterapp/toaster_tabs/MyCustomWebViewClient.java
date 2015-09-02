package io.toasterapp.toaster_tabs;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyCustomWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);

//            if (url.contains("posts")) {
//
//            }

        return false;
    }

//    @Override
//    public void onPageFinished(WebView view, String url) {
//        super.onPageFinished(view, url);
//        // Display the keyboard automatically when relevant
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (url.contains("newPost")) {
//            imm.showSoftInput(view, 0);
//        } else {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//        if (url.contains("post")){
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
}
