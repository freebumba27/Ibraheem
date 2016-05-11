package com.ibraheem.ibraheem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ibraheem.ibraheem.utils.ReusableClass;
import com.ibraheem.ibraheem.widget.MyPagerAdapter;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.SmartLocation;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    MyPagerAdapter myPagerAdapter;
    @Bind(R.id.adView)
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!ReusableClass.haveNetworkConnection(this))
            Toast.makeText(this, "Please turn on the internet to get latest update.", Toast.LENGTH_LONG).show();

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (!SmartLocation.with(this).location().state().isGpsAvailable()) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    MainActivity.this.startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(MainActivity.this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Toast.makeText(MainActivity.this, "You need to give the location permission to enjoy the app full features.", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            });
            dialog.show();
        } else {
            //========================================================================================
            // ++++++++++++++++++++++++++++++ Start Tracker service ++++++++++++++++++++++++++++++++++
            //========================================================================================

            Intent myIntent1 = new Intent(this, MyMainReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent1, 0);

            AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
            long recurring = (10 * 1000);  // in milliseconds
            alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), recurring, pendingIntent);

            //========================================================================================
            // ++++++++++++++++++++++++++++++ Start Tracker service ++++++++++++++++++++++++++++++++++
            //========================================================================================
        }

        loadAds();
    }

    private void loadAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
