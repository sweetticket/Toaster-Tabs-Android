package com.honeyjam.toaster;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by jennykim on 9/12/15.
 */
public class MyPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

    }
}
