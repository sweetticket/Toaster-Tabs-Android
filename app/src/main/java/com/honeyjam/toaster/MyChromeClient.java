package com.honeyjam.toaster;

/**
 * Created by jennykim on 9/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyChromeClient extends WebChromeClient {

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

//            Log.d("url  ", message);
//            Log.d("context", mContext.toString());
            Intent intent = new Intent(mContext, PostShowActivity.class);
            intent.putExtra("url", message);
            result.cancel();
            mContext.startActivity(intent);
//            ((MainActivity) mContext).toPostShow(message);

        }

        if (message.contains("logout")) {
            if (!isSigningIn) {
                isSigningIn = true;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("login-required", true);
                ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);
                result.cancel();
                ((Activity) mContext).finish();
            }
        }

        if (message.contains("about")) {
            Intent intent = new Intent(mContext, SettingsDetailActivity.class);
            intent.putExtra("url", "/settings/about");
            result.cancel();
            mContext.startActivity(intent);
        }

        if (message.contains("terms")) {
            Intent intent = new Intent(mContext, SettingsDetailActivity.class);
            intent.putExtra("url", "/settings/terms");
            result.cancel();
            mContext.startActivity(intent);
        }

        if (message.contains("signed-in")) {
            result.cancel();
            view.loadUrl("javascript:Meteor.call(\"registerUserIdToParse\", \"" + GlobalVariables.PARSE_OBJECT_ID +"\");");
            String userId = message.substring(message.lastIndexOf(":") + 1);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("userId", userId);
            ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);
            ((Activity) mContext).finish();
            isSigningIn = false;
        }

        if (message.contains("start-signup")) {
            if (!isSigningIn) {
                isSigningIn = true;
                ((MainActivity) mContext).toSignUp();
            }
        }

        if (message.contains("loadingEnd")) {
            mWheel.setVisibility(View.GONE);
        }

        if (message.contains("share")) {
            //TODO
        }

        if (message.contains("badgeCount")) {
            String newUnread = message.substring(message.lastIndexOf(":") + 1);
//            ((MainActivity) mContext).setBadgeText(newUnread);
        }

        result.cancel();
        return true;

    }


}