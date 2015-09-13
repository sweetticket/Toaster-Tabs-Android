package com.honeyjam.toaster;

import android.os.AsyncTask;

import com.parse.ParseInstallation;

/**
 * Created by jennykim on 9/12/15.
 */
public class BackgroundSave extends AsyncTask {
    public BackgroundSave(){
    }
    @Override
    protected Object doInBackground(Object... arg0) {
        try{
            ParseInstallation.getCurrentInstallation().saveInBackground();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
