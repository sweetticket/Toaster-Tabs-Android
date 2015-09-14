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

            Intent intent = new Intent(mContext, FirstDetailActivity.class);
            intent.putExtra("path", message);
            intent.putExtra("title", "TOAST DETAIL");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            result.cancel();
            mContext.startActivity(intent);

        }

        if (message.contains("notVerified")) {
            Intent intent = new Intent(mContext, FirstDetailActivity.class);
            intent.putExtra("path", "/notVerified");
            intent.putExtra("title", "VERIFICATION");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        if (message.contains("badgeCount")) {
            String newUnread = message.substring(message.lastIndexOf(":") + 1);
            try {
                ((MainActivity) mContext).setBadgeCount(newUnread);
            } catch (java.lang.ClassCastException e) {
                Log.d("catch", "class cast exception");
            }
        }

        result.cancel();
        return true;

    }


}
