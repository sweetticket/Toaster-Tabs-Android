package com.honeyjam.toaster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.webkit.WebSettings;
        import android.webkit.WebView;

/**
 * Created by jennykim on 9/2/15.
 */
public class FirstDetailActivity extends AppCompatActivity {
    Toolbar mToolbar;
    WebView mWebView;
    int mMenuLayout;
    String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mPath = "/blank";

        Log.d("Activity", "FirstDetailActivity onCreate");

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_right);
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


        mToolbar.setNavigationIcon(R.mipmap.back_ios);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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
        FirstDetailWebViewClient client = new FirstDetailWebViewClient();
        client.setContext(this);
        client.setWheel(findViewById(R.id.progress_wheel));
        mWebView.setWebViewClient(client);
        FirstDetailChromeClient chromeClient = new FirstDetailChromeClient();
        chromeClient.setContext(this);
        chromeClient.setWheel(findViewById(R.id.progress_wheel));
//        chromeClient.setProgressBar((ProgressBar) findViewById(R.id.pB1));
        mWebView.setWebChromeClient(chromeClient);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // may need to change to an blank page
        mWebView.loadUrl(GlobalVariables.ROOT_URL + mPath);

    }

    public void routerGo() {
        mWebView.loadUrl("javascript:Router.go('"+ mPath +"');");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Activity", "FirstDetailActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity", "FirstDetailActivity onResume");
        String title = getIntent().getStringExtra("title");
        mPath = getIntent().getStringExtra("path");
        mMenuLayout = getIntent().getIntExtra("menu_layout", R.menu.menu_blank);
        mToolbar.setTitle(title);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Activity", "FirstDetailActivity onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Activity", "FirstDetailActivity onStop");
        if (mPath.contains("notVerified")) {
            mWebView.loadUrl("javascript:Meteor.logout();Session.set(\"currentUserId\", undefined);");
        }
        if (mPath.contains("notifications")) {
            mWebView.loadUrl("javascript:Meteor.call(\"readAllNotifications\");");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Activity", "FirstDetailActivity onDestroy");
    }

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
        getMenuInflater().inflate(mMenuLayout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if (id == R.id.submit) {
//
//            mWebView.loadUrl("javascript:Template.newPost.submitNewPost();");
//            finish();
//
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent;

        if (mPath.contains("notVerified")) {
            intent = new Intent(getBaseContext(), SignUpTabActivity.class);
//            intent = new Intent();
//            intent.putExtra("login-required", true);
//            intent.putExtra("restart", true);
            mWebView.loadUrl("javascript:Meteor.logout();Session.set(\"currentUserId\", undefined);");
//            setResult(Activity.RESULT_OK, intent);
//            finish();

        } else {
            intent = new Intent(getBaseContext(), MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getBaseContext().startActivity(intent);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

}