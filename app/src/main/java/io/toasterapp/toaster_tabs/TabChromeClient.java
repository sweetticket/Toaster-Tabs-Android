package io.toasterapp.toaster_tabs;

/**
 * Created by jennykim on 9/2/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class TabChromeClient extends WebChromeClient {

    Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
//        Log.d("context", mContext.toString());

    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

        if (message.contains("/posts/")) {

//            Log.d("url  ", message);
//            Log.d("context", mContext.toString());
            Intent intent = new Intent(mContext, PostShowActivity.class);
            intent.putExtra("url", message);
            mContext.startActivity(intent);
//            ((MainActivity) mContext).toPostShow(message);

        }
        result.cancel();
        return true;

    }

}
