package io.toasterapp.toaster_tabs;

/**
 * Created by jennykim on 9/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyChromeClient extends WebChromeClient {

    Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
//        Log.d("context", mContext.toString());

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

        if (message.contains("share")) {
            //TODO
        }

        result.cancel();
        return true;

    }

}
