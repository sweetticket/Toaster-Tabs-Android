package io.toasterapp.toaster_tabs;

import android.app.Application;
import android.content.res.Configuration;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by jennykim on 9/12/15.
 */
public class MyApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "nGWY63hAKCyyMHS41xmjNiL4mCIqsJ0TBGWAG4vy", "w1ps0nxnPNfpJvIGnw52wCl5Og5eOLgiwiuXHn6i");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // background save wrapped in Async to prevent freezing
//        new BackgroundSave().execute(getApplicationContext());
        ParseInstallation.getCurrentInstallation().saveInBackground();
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
