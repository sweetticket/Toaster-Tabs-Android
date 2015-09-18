package com.honeyjam.toaster;

/**
 * Created by jennykim on 9/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyChromeClient extends WebChromeClient {

    Context mContext;
//    ProgressBar mProgressBar;
    View mWheel;


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

//        Log.d("alert", "message: " + message);

        if (message.contains("/posts/")) {

            if (MainActivity.menuAccessAllowed) {

                Intent intent = new Intent(mContext, FirstDetailActivity.class);
                intent.putExtra("path", message);
                intent.putExtra("title", "TOAST DETAIL");
                intent.putExtra("menu_layout", R.menu.menu_blank);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                result.cancel();
                mContext.startActivity(intent);
            }

        }

//        if (message.contains("notVerified")) {
//            Intent intent = new Intent(mContext, FirstDetailActivity.class);
//            intent.putExtra("path", "/notVerified");
//            intent.putExtra("title", "VERIFICATION");
//            intent.putExtra("menu_layout", R.menu.menu_blank);
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            result.cancel();
//            mContext.startActivity(intent);
//
//        }


        if (message.contains("signed-in")) {
            result.cancel();
            view.loadUrl("javascript:Meteor.call(\"registerUserIdToParse\", \"" + GlobalVariables.PARSE_OBJECT_ID + "\");");
            String userId = message.substring(message.lastIndexOf(":") + 1);
            Intent resultIntent;
            if (message.contains("notVerified")) {
//                SharedPreferences prefs = mContext.getSharedPreferences("UserInfo", 0);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putString("userId", userId);
//                editor.apply();

                resultIntent = new Intent(mContext, FirstDetailActivity.class);
                resultIntent.putExtra("path", "/notVerified");
                resultIntent.putExtra("title", "VERIFICATION");
                resultIntent.putExtra("menu_layout", R.menu.menu_blank);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(resultIntent);

            } else {
                SharedPreferences prefs = mContext.getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userId", userId);
                editor.apply();
                ((Activity) mContext).finish();
                resultIntent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(resultIntent);
            }
        }

        if (message.contains("start-signup")) {
            try {
                ((MainActivity) mContext).toSignUp();
            } catch (java.lang.ClassCastException e) {
//            Log.d("exception", "Not MainActivity");
            }

        }

        if (message.contains("loadingEnd")) {
            mWheel.setVisibility(View.GONE);
        }

        if (message.contains("badgeCount")) {
            String newUnread = message.substring(message.lastIndexOf(":") + 1);
            try {
                ((MainActivity) mContext).setBadgeCount(newUnread);
            } catch (java.lang.ClassCastException e) {
//                Log.d("exception", "Not MainActivity");
            }
        }

        if (message.contains("isSignedInVerified")) {
            MainActivity.menuAccessAllowed = true;
            try {
                ((MainActivity) mContext).invalidateOptionsMenu();
            } catch (java.lang.ClassCastException e) {
//                Log.d("exception", "Not MainActivity");
            }
        }

        result.cancel();
        return true;

    }


}
