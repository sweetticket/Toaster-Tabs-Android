package com.honeyjam.toaster;

import android.app.Application;
import android.content.res.Configuration;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by jennykim on 9/12/15.
 */
public class ToasterApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "nGWY63hAKCyyMHS41xmjNiL4mCIqsJ0TBGWAG4vy", "w1ps0nxnPNfpJvIGnw52wCl5Og5eOLgiwiuXHn6i");

        // background save wrapped in Async to prevent freezing
//        new BackgroundSave().execute(getApplicationContext());
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();
        GlobalVariables.setParseObjectId(installation.getObjectId());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
