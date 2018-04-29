package com.tcs.dhrubaneel.hackathonapp.constants;

import android.util.Base64;

public class ConstantVariables {
    public static String baseURL="http://fex301-publish.adobesandbox.com";
    public static String jsonURL="/content/entities/content-fsi-app580/content/root/children/credit-cards/children/";
    public static String auth_token = basicAuth();




    private static String basicAuth(){
        String username = "fex570";
        String password = "thaDR6f=";
        String credentials = username + ":" + password;
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        return basic;
    }
}
