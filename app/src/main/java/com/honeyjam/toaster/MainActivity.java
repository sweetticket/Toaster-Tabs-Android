package com.honeyjam.toaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import at.markushi.ui.ActionView;


public class MainActivity extends AppCompatActivity {

    static final int LOGIN_REQUIRED_REQUEST = 1;
    static final int SIGNED_IN = 2;
    static final int RESTART = 3;
    static final int NEW_POST = 4;
    // Declaring Your View and Variables

    Toolbar mToolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"New","Hot"};
    int Numboftabs = 2;
    static boolean firstLoadComplete;
    static boolean oneTabLoadComplete;
    static boolean menuAccessAllowed;
    int badgeCount;

    Context mContext;
    TextView mBadge;

    private String mUserId;

    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Log.d("Activity", "MainActivity onCreate");

        firstLoadComplete = false;
        oneTabLoadComplete = false;
        menuAccessAllowed = false;
        mContext = this;
        badgeCount = 0;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            int statusbar_color = Color.rgb(255, 94, 58);
            window.setStatusBarColor(statusbar_color);
        }

        SharedPreferences prefs = getSharedPreferences("UserInfo", 0);
        if (prefs.getString("userId", "").toString() != null) {
            mUserId = prefs.getString("userId", "").toString();
//            Log.d("mUserId", mUserId);
        }

        if (getIntent().hasExtra("restart")) {
            menuAccessAllowed = true;
            if (getIntent().getBooleanExtra("restart", true)) {
                findViewById(R.id.splash_screen).setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int statusbar_splash_color = Color.rgb(255, 70, 79);
                    this.getWindow().setStatusBarColor(statusbar_splash_color);
                }
                firstLoadComplete = true;
                oneTabLoadComplete = true;

            } else {
                findViewById(R.id.splash_screen).setVisibility(View.VISIBLE);
            }
        }


        // Creating The Toolbar and setting it as the Toolbar for the activity

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        SpannableString s = new SpannableString("toaster");
        s.setSpan(new CustomTypefaceSpan(this, "Oduda.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mToolbar.setTitle(s);

        setSupportActionBar(mToolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs, this);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.clear();
        if (menuAccessAllowed) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            pager.setClickable(true);

            View noticeActionView = menu.findItem(R.id.notice).getActionView();
            mBadge = (TextView) noticeActionView.findViewById(R.id.actionbar_notifcation_textview);
            noticeActionView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    resetBadgeCount();
                    Intent intent = new Intent(mContext, FirstDetailActivity.class);
                    intent.putExtra("path", "/notifications");
                    intent.putExtra("title", "NOTIFICATIONS");
                    intent.putExtra("menu_layout", R.menu.menu_blank);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);

                }
            });
        }
        else {
            pager.setClickable(false);
            getMenuInflater().inflate(R.menu.menu_blank, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_blank, menu);

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.d("Activity", "MainActivity onStart");
        active = true;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("Activity", "MainActivity onResume");
        invalidateOptionsMenu();
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d("Activity", "MainActivity onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d("Activity", "MainActivity onStop");
        active = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d("Activity", "MainActivity onDestroy");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.newPost) {

            Intent intent = new Intent(this, NewPostActivity.class);
            startActivityForResult(intent, NEW_POST);
            return true;
        }

        if (id == R.id.settings) {

            Intent intent = new Intent(this, FirstDetailActivity.class);
            intent.putExtra("path", "/settings");
            intent.putExtra("title", "SETTINGS");
            intent.putExtra("menu_layout", R.menu.menu_settings);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            startActivityForResult(intent, LOGIN_REQUIRED_REQUEST);

            return true;
        }

        if (id == R.id.profile) {
            Intent intent = new Intent(this, FirstDetailActivity.class);
            intent.putExtra("path", "/profile");
            intent.putExtra("title", "MY PROFILE");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return true;
        }

        if (id == R.id.notice) {

            Intent intent = new Intent(this, FirstDetailActivity.class);
            intent.putExtra("path", "/notifications");
            intent.putExtra("title", "NOTIFICATIONS");
            intent.putExtra("menu_layout", R.menu.menu_blank);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == LOGIN_REQUIRED_REQUEST) {
//            Log.d("activityResult", "requestCode: LOGIN_REQUIRED_REQUEST");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
//                Log.d("activityResult", "resultCode: RESULT_OK");
                boolean loginRequired = data.getExtras().getBoolean("login-required");
                if (loginRequired) {
//                    mPusher.disconnect();
                    SharedPreferences prefs = getSharedPreferences("UserInfo", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.remove("userId");
                    editor.apply();
                    toSignUp();
                }
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }

        if (requestCode == SIGNED_IN) {
            Intent intent = getIntent();

            if (data != null) {
                String userId = data.getStringExtra("userId");
                SharedPreferences prefs = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userId", userId);
                editor.apply();

//            finish();
//            startActivityForResult(intent, RESTART);
                intent.putExtra("restart", true);
            }

            finish();
            startActivity(intent);
        }

        if (requestCode == RESTART) {
            findViewById(R.id.splash_screen).setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int statusbar_color = Color.rgb(255, 70, 79);
                this.getWindow().setStatusBarColor(statusbar_color);
            }
            firstLoadComplete = true;
            oneTabLoadComplete = true;
        }

        if (requestCode == NEW_POST) {
            if (data.getBooleanExtra("submitted", false)) {
                pager.setCurrentItem(0);
            }
        }
    }

    public void toSignUp() {
        Intent loginIntent = new Intent(this, SignUpTabActivity.class);
        startActivityForResult(loginIntent, SIGNED_IN);
    }

    public boolean checkIfLoaded() {
        if (firstLoadComplete) {
//            Log.d("checkIfLoaded", "already loaded");
            return true;
        } else if (oneTabLoadComplete) {
//            Log.d("checkIfLoaded", "both tabs loaded");
            firstLoadComplete = true;
            //Hide Image
            findViewById(R.id.splash_screen).setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int statusbar_color = Color.rgb(255, 70, 79);
                this.getWindow().setStatusBarColor(statusbar_color);
            }
            return true;
        } else {
//            Log.d("checkIfLoaded", "only one tab loaded");
            oneTabLoadComplete = true;
            return false;
        }
    }

    public void setBadgeCount(String numUnread) {
        badgeCount = Integer.parseInt(numUnread);
        if (menuAccessAllowed) {
            mBadge.setVisibility(View.VISIBLE);
            mBadge.setText(numUnread);
        }
    }

    public void resetBadgeCount() {
        badgeCount = 0;
        mBadge.setVisibility(View.GONE);
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}
