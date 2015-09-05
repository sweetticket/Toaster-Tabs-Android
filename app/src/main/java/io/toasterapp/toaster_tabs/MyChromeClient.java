package io.toasterapp.toaster_tabs;

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
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyChromeClient extends WebChromeClient {

    Context mContext;
    ProgressBar mProgressBar;


    public void setContext(Context context) {
        this.mContext = context;
//        Log.d("context", mContext.toString());
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        Log.d("alert", "message: " + message);

        if (message.contains("/posts/")) {

//            Log.d("url  ", message);
//            Log.d("context", mContext.toString());
            Intent intent = new Intent(mContext, PostShowActivity.class);
            intent.putExtra("url", message);
            mContext.startActivity(intent);
//            ((MainActivity) mContext).toPostShow(message);

        }

        if (message.contains("logout")) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("login-required", true);
            ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);
            ((Activity) mContext).finish();
        }

        if (message.contains("about")) {
            Log.d("alert", "is about");
            Intent intent = new Intent(mContext, SettingsDetailActivity.class);
            intent.putExtra("url", "/settings/about");
            mContext.startActivity(intent);
        }

        if (message.contains("terms")) {
            Intent intent = new Intent(mContext, SettingsDetailActivity.class);
            intent.putExtra("url", "/settings/terms");
            mContext.startActivity(intent);
        }

        if (message.contains("signed-in")) {
            Intent resultIntent = new Intent();
            ((Activity) mContext).setResult(Activity.RESULT_OK, resultIntent);
            ((Activity) mContext).finish();
        }

        if (message.contains("toSignUp")) {
            ((LoginActivity) mContext).toSignUp();
        }

        if (message.contains("toSignIn")) {
            ((LoginActivity) mContext).toSignIn();
        }

        if (message.contains("start-signup")) {
            ((MainActivity) mContext).toSignUp();
        }

        if (message.contains("share")) {
            //TODO
        }

        result.cancel();
        return true;

    }

    public void onProgressChanged(WebView view, int progress)
    {
        if(progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE){
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
        mProgressBar.setProgress(progress);
        if(progress == 100) {
            mProgressBar.setVisibility(ProgressBar.GONE);
        }
    }

}
