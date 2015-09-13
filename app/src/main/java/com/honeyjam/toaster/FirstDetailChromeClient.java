package com.honeyjam.toaster;

/**
 * Created by jennykim on 9/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class FirstDetailChromeClient extends WebChromeClient {

    Context mContext;
//    ProgressBar mProgressBar;
    View mWheel;
    static boolean isSigningIn = false;



    public void setContext(Context context) {
        this.mContext = context;
//        Log.d("context", mContext.toString());
    }

//    public void setProgressBar(ProgressBar progressBar) {
//        this.mProgressBar = progressBar;
//    }

    public void setWheel(View wheel) {
        this.mWheel = wheel;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        Log.d("alert", "message: " + message);

        if (message.contains("/posts/")) {

            Intent intent = new Intent(mContext, SecondDetailActivity.class);
            intent.putExtra("path", message);
            intent.putExtra("title", "TOAST DETAIL");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            result.cancel();
            mContext.startActivity(intent);

        }

        if (message.contains("logout")) {
            if (!isSigningIn) {
//                isSigningIn = true;
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("login-required", true);
//                ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);
                result.cancel();
                ((Activity) mContext).finish();
            }
        }

        if (message.contains("about")) {
            Intent intent = new Intent(mContext, SecondDetailActivity.class);
            intent.putExtra("path", "/settings/about");
            intent.putExtra("title", "ABOUT");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            result.cancel();
            mContext.startActivity(intent);
        }

        if (message.contains("terms")) {
            Intent intent = new Intent(mContext, SecondDetailActivity.class);
            intent.putExtra("path", "/settings/terms");
            intent.putExtra("title", "TERMS OF SERVICE");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            result.cancel();
            mContext.startActivity(intent);
        }

        if (message.contains("loadingEnd")) {
            mWheel.setVisibility(View.GONE);
        }

        if (message.contains("share")) {
            String shareURL = message.substring(message.lastIndexOf(":") + 1);
            if (!shareURL.startsWith("http://") && !shareURL.startsWith("https://")) {
                shareURL = "http://" + shareURL;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareURL));
            mContext.startActivity(browserIntent);
        }

        result.cancel();
        return true;

    }


}
