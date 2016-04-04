package com.ibraheem.ibraheem;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ibraheem.ibraheem.utils.ReusableClass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateTimeService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                final Location locationA = new Location("locationA");
                locationA.setLatitude(ReusableClass.startPointLat);
                locationA.setLongitude(ReusableClass.startPointLng);
                if (location.distanceTo(locationA) < 100
                        && ReusableClass.getFromPreference("stopTime", CalculateTimeService.this).equalsIgnoreCase("")) {
                    long time = System.currentTimeMillis();

                    ReusableClass.saveInPreference("startTime", String.valueOf(time), CalculateTimeService.this);
                    Toast.makeText(CalculateTimeService.this, "START", Toast.LENGTH_LONG).show();
                }

                final Location locationB = new Location("locationB");
                locationB.setLatitude(ReusableClass.stopPointLat);
                locationB.setLongitude(ReusableClass.stopPointLng);
                if (location.distanceTo(locationA) < 200
                        && !ReusableClass.getFromPreference("startTime", CalculateTimeService.this).equalsIgnoreCase("")) {
                    long time = System.currentTimeMillis();

                    ReusableClass.saveInPreference("stopTime", String.valueOf(time), CalculateTimeService.this);

                    long timeDiff = (Long.parseLong(ReusableClass.getFromPreference("startTime", CalculateTimeService.this)) -
                            Long.parseLong(ReusableClass.getFromPreference("startTime", CalculateTimeService.this)));

                    String duration = new SimpleDateFormat("mm:ss:SSS").format(new Date(timeDiff));

                    ReusableClass.saveInPreference("duration", duration, CalculateTimeService.this);


                    ReusableClass.saveInPreference("startTime", "", CalculateTimeService.this);
                    ReusableClass.saveInPreference("stopTime", "", CalculateTimeService.this);

                    Toast.makeText(CalculateTimeService.this, "Distance: " + ReusableClass.getFromPreference("duration", CalculateTimeService.this), Toast.LENGTH_LONG).show();
                }

                //stopSelf();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 0, locationListener);

        return super.onStartCommand(intent, flags, startId);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
}
