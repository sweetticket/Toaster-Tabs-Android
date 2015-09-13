package com.honeyjam.toaster;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by jennykim on 9/6/15.
 */
public class GlobalVariables {
    //SERVER IP
//    public static final String ROOT_URL = "http://104.131.158.80:3000";
    // JENNY'S HOUSE IP
    public static final String ROOT_URL = "http://192.168.1.107:3000";
    public static String PARSE_OBJECT_ID;

    public static void setParseObjectId(String token) {
        PARSE_OBJECT_ID = token;
    }

}
