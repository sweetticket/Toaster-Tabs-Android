package io.toasterapp.toaster_tabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.Window;
import android.view.WindowManager;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;


public class MainActivity extends AppCompatActivity {

    static final int LOGIN_REQUIRED_REQUEST = 1;
    static final int SIGNED_IN = 2;
    static final int RESTART = 3;
    // Declaring Your View and Variables

    Toolbar mToolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"New","Hot"};
    int Numboftabs = 2;
    static boolean firstLoadComplete;
    static boolean oneTabLoadComplete;

    private PrivateChannel mChannel;
    private Pusher mPusher;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        firstLoadComplete = false;
        oneTabLoadComplete = false;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        int statusbar_splash_color = Color.rgb(255, 94, 58);
//        window.setStatusBarColor(statusbar_splash_color);

        int statusbar_splash_color = Color.rgb(255, 94, 58);
        window.setStatusBarColor(statusbar_splash_color);

        SharedPreferences prefs = getSharedPreferences("UserInfo", 0);
        if (prefs.getString("userId", "").toString() != null) {
            mUserId = prefs.getString("userId", "").toString();
            Log.d("mUserId", mUserId);

            HttpAuthorizer authorizer = new HttpAuthorizer(GlobalVariables.ROOT_URL + "/pusher/auth");
            PusherOptions options = new PusherOptions().setAuthorizer(authorizer);

            mPusher = new Pusher("3f8ba7f168a24152f488", options);

            mPusher.connect(new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange change) {
                    System.out.println("State changed to " + change.getCurrentState() +
                            " from " + change.getPreviousState());
                    if (change.getCurrentState() == ConnectionState.CONNECTED) {
                        mPusher.connect();
                    }
                }

                @Override
                public void onError(String message, String code, Exception e) {
                    System.out.println("There was a problem connecting!");
                }
            }, ConnectionState.ALL);

            // Subscribe to a channel
            mChannel = mPusher.subscribePrivate("private-" + mUserId,
                    new PrivateChannelEventListener() {
                        @Override
                        public void onEvent(String channelName, String eventName, String data) {
                            //TODO
                            Log.d("channel event", channelName);
                        }

                        @Override
                        public void onSubscriptionSucceeded(String channelName) {
                            //TODO
                            Log.d("subscription success", channelName);
                        }

                        @Override
                        public void onAuthenticationFailure(String message, Exception e) {
                            System.out.println(
                                    String.format("Authentication failure due to [%s], exception was [%s]", message, e)
                            );
                        }

                        // Other ChannelEventListener methods
                    });
        }

        if (getIntent().hasExtra("restart")) {
            if (getIntent().getBooleanExtra("restart", true)) {
                findViewById(R.id.splash_screen).setVisibility(View.GONE);
                statusbar_splash_color = Color.rgb(255, 70, 79);
                this.getWindow().setStatusBarColor(statusbar_splash_color);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.newPost) {

            Intent intent = new Intent(this, NewPostActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, LOGIN_REQUIRED_REQUEST);
            return true;
        }

        if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.notice) {
            Intent intent = new Intent(this, NotificationsActivity.class);
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
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
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

            String userId = data.getStringExtra("userId");
            SharedPreferences prefs = getSharedPreferences("UserInfo", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("userId", userId);
            editor.apply();

            Intent intent = getIntent();
//            finish();
//            startActivityForResult(intent, RESTART);
            intent.putExtra("restart", true);

            finish();
            startActivity(intent);
        }

        if (requestCode == RESTART) {
            findViewById(R.id.splash_screen).setVisibility(View.GONE);
            int statusbar_color = Color.rgb(255, 70, 79);
            this.getWindow().setStatusBarColor(statusbar_color);
            firstLoadComplete = true;
            oneTabLoadComplete = true;
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
            int statusbar_color = Color.rgb(255, 70, 79);
            this.getWindow().setStatusBarColor(statusbar_color);
            return true;
        } else {
//            Log.d("checkIfLoaded", "only one tab loaded");
            oneTabLoadComplete = true;
            return false;
        }
    }


//    public void toPostShow(String url) {
//        Intent intent = new Intent(this, PostShowActivity.class);
//        intent.putExtra("url", url);
//        startActivity(intent);
//    }
}
