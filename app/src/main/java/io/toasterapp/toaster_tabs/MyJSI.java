package io.toasterapp.toaster_tabs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by jennykim on 9/2/15.
 */
public class MyJSI {

    Context mContext;

    public MyJSI (Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void toPostShow(String url) {
        Log.d("url", url);
        if (url.contains("post")){
            Intent intent = new Intent(mContext, PostShowActivity.class);
            intent.putExtra("url", url);
            mContext.startActivity(intent);
        }
    }
}
