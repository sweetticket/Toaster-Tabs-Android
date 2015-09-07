package io.toasterapp.toaster_tabs;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
        this.mWebView = (WebView) v.findViewById(R.id.webview);

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
        DetailWebViewClient client = new DetailWebViewClient();
        client.setContext(mContext);
        client.setWheel(v.findViewById(R.id.progress_wheel));
        mWebView.setWebViewClient(client);
        MyChromeClient chromeClient = new MyChromeClient();
        chromeClient.setContext(mContext);
        chromeClient.setWheel(v.findViewById(R.id.progress_wheel));
//        chromeClient.setProgressBar((ProgressBar) v.findViewById(R.id.pB1));
        mWebView.setWebChromeClient(chromeClient);

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        mWebView.addJavascriptInterface(new MyJSI(mContext), "myjsi");

//        mWebView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View arg0, boolean hasFocus) {
//
//                if (!hasFocus) {
//                    Log.d("hasFocus", "false");
//                    mWebView.requestFocus(View.FOCUS_DOWN);
//                    mWebView.setFocusable(true);
//                }
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(mWebView, 0);
//            }
//        });

        if (savedInstanceState==null) {
            mWebView.loadUrl(GlobalConstants.mRootURL + "/signIn");
        }

        return v;
    }
}
