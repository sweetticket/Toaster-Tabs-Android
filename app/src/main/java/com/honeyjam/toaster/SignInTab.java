package com.honeyjam.toaster;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by hp1 on 21-01-2015.
 */
public class SignInTab extends Fragment {

    WebView mWebView;
    Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_view, container, false);
        this.mWebView = (LiveWebView) v.findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);

        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        settings.setUserAgentString(
                settings.getUserAgentString()
                        + " "
                        + getString(R.string.user_agent_suffix)
        );

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        FirstDetailWebViewClient client = new FirstDetailWebViewClient();
        client.setContext(mContext);
        client.setWheel(v.findViewById(R.id.progress_wheel));
        mWebView.setWebViewClient(client);
        MyChromeClient chromeClient = new MyChromeClient();
        chromeClient.setContext(mContext);
        chromeClient.setWheel(v.findViewById(R.id.progress_wheel));
//        chromeClient.setProgressBar((ProgressBar) v.findViewById(R.id.pB1));
        mWebView.setWebChromeClient(chromeClient);

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        mWebView.requestFocus(View.FOCUS_DOWN);
//        mWebView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                    case MotionEvent.ACTION_UP:
//                        if (!v.hasFocus()) {
//                            v.requestFocus();
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
        if (savedInstanceState==null) {
            mWebView.loadUrl(GlobalVariables.ROOT_URL + "/signIn");
        }

        return v;
    }
}
