package com.ibraheem.ibraheem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by anirban on 01/04/2016.
 */
public class MyMainReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("broadcast", "Inside broadcast");

        Intent i = new Intent(context, CalculateTimeService.class);
        context.startService(i);
    }
}