package io.toasterapp.toaster_tabs;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by jennykim on 9/2/15.
 */
public class SignInActivity extends AppCompatActivity {
    Toolbar mToolbar;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_left);
        setContentView(R.layout.detail);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        int statusbar_color = Color.rgb(255, 70, 79);
        window.setStatusBarColor(statusbar_color);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("SIGN UP");
        setSupportActionBar(mToolbar);


        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();
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
        client.setContext(this);
        client.setWheel(findViewById(R.id.progress_wheel));
        mWebView.setWebViewClient(client);
        MyChromeClient chromeClient = new MyChromeClient();
        chromeClient.setContext(this);
        chromeClient.setWheel(findViewById(R.id.progress_wheel));
//        chromeClient.setProgressBar((ProgressBar) findViewById(R.id.pB1));

        mWebView.setWebChromeClient(chromeClient);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        if (savedInstanceState==null) {
        String postURL = "http://192.168.0.104:3000/signIn";
        mWebView.loadUrl(postURL);
//        }

    }
//
//    public void toSignUp() {
//        mWebView.loadUrl("http://192.168.0.104:3000/signUp");
//        mToolbar.setTitle("SIGN UP");
//    }
//
//    public void toSignIn() {
//        mWebView.loadUrl("http://192.168.0.104:3000/signIn");
//        mToolbar.setTitle("SIGN IN");
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the state of the WebView
//        mWebView.restoreState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

//        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public WebView getWebView() {
        return mWebView;
    }

}