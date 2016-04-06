package com.ibraheem.ibraheem.utils;

import android.net.Uri;

/**
 * Created by anirban on 05/04/2016.
 */
public class URLConstantClass {

    public static final String BASE_URL = "http://www.freebumba27.com/7dood/api";
    private static String timeUrl;

    public static String updateTimeUrl(String direction, String imeiNo, String deviceUpdatedTime, String durationMilisec) {
        return Uri.parse(BASE_URL).buildUpon()
                .appendPath("updating_duration_time.php")
                .appendQueryParameter("direction", direction)
                .appendQueryParameter("imei_no", imeiNo)
                .appendQueryParameter("device_updated_time", deviceUpdatedTime)
                .appendQueryParameter("duration_milisec", durationMilisec)
                .toString();
    }

    public static String getTimeUrl(String direction) {
        return Uri.parse(BASE_URL).buildUpon()
                .appendPath("get_latest_time.php")
                .appendQueryParameter("direction", direction)
                .toString();
    }
}