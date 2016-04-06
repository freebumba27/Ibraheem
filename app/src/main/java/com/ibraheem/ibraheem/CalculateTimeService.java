package com.ibraheem.ibraheem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.ibraheem.ibraheem.utils.ReusableClass;
import com.ibraheem.ibraheem.utils.URLConstantClass;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.concurrent.TimeUnit;

public class CalculateTimeService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                final Location locationA = new Location("locationA");
                locationA.setLatitude(ReusableClass.homeLat);
                locationA.setLongitude(ReusableClass.homeLng);

                final Location locationB = new Location("locationB");
                locationB.setLatitude(ReusableClass.officeLat);
                locationB.setLongitude(ReusableClass.officeLng);
                long stopTime;
                if (ReusableClass.getFromPreference("stopTime", CalculateTimeService.this).equalsIgnoreCase(""))
                    stopTime = 2;
                else
                    stopTime = Long.parseLong(ReusableClass.getFromPreference("stopTime", CalculateTimeService.this));
                long stopTimeDiff = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - stopTime);

                Log.d("MYTAG", "stopTimeDiff: " + stopTimeDiff);
                if (location.distanceTo(locationA) < 200
                        && !ReusableClass.getFromPreference("startTimeBtoA", CalculateTimeService.this).equalsIgnoreCase("")) {
                    long time = System.currentTimeMillis();

                    ReusableClass.saveInPreference("stopTimeBtoA", String.valueOf(time), CalculateTimeService.this);
                    ReusableClass.saveInPreference("stopTime", String.valueOf(time), CalculateTimeService.this);

                    long timeDiff = (Long.parseLong(ReusableClass.getFromPreference("stopTimeBtoA", CalculateTimeService.this)) -
                            Long.parseLong(ReusableClass.getFromPreference("startTimeBtoA", CalculateTimeService.this)));

                    String timeTaken = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeDiff),
                            TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
                            TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));
                    ReusableClass.saveInPreference("timeTakenBtoA", timeTaken, CalculateTimeService.this);


                    ReusableClass.saveInPreference("startTimeBtoA", "", CalculateTimeService.this);
                    ReusableClass.saveInPreference("stopTimeBtoA", "", CalculateTimeService.this);


                    TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    mngr.getDeviceId();

                    try {
                        Response<String> response = Ion.with(CalculateTimeService.this)
                                .load("POST", URLConstantClass.updateTimeUrl("BtoA", mngr.getDeviceId(), String.valueOf(System.currentTimeMillis()), String.valueOf(timeDiff)))
                                .addHeader("Content-Type", "application/json")
                                .setStringBody("")
                                .asString()
                                .withResponse()
                                .get();
                        String result = response.getResult();
                        if (result.equalsIgnoreCase("YES"))
                            Toast.makeText(CalculateTimeService.this, "UPDATED TO SERVER", Toast.LENGTH_SHORT).show();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CalculateTimeService.this, "Time Taken B to A: " + ReusableClass.getFromPreference("timeTakenBtoA", CalculateTimeService.this), Toast.LENGTH_SHORT).show();
                } else if (location.distanceTo(locationB) < 200
                        && !ReusableClass.getFromPreference("startTimeAtoB", CalculateTimeService.this).equalsIgnoreCase("")) {
                    long time = System.currentTimeMillis();

                    ReusableClass.saveInPreference("stopTimeAtoB", String.valueOf(time), CalculateTimeService.this);
                    ReusableClass.saveInPreference("stopTime", String.valueOf(time), CalculateTimeService.this);

                    long timeDiff = (Long.parseLong(ReusableClass.getFromPreference("stopTimeAtoB", CalculateTimeService.this)) -
                            Long.parseLong(ReusableClass.getFromPreference("startTimeAtoB", CalculateTimeService.this)));

                    String timeTaken = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeDiff),
                            TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
                            TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));
                    ReusableClass.saveInPreference("timeTakenAtoB", timeTaken, CalculateTimeService.this);


                    ReusableClass.saveInPreference("startTimeAtoB", "", CalculateTimeService.this);
                    ReusableClass.saveInPreference("stopTimeAtoB", "", CalculateTimeService.this);


                    TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    mngr.getDeviceId();

                    try {
                        Response<String> response = Ion.with(CalculateTimeService.this)
                                .load("POST", URLConstantClass.updateTimeUrl("AtoB", mngr.getDeviceId(), String.valueOf(System.currentTimeMillis()), String.valueOf(timeDiff)))
                                .addHeader("Content-Type", "application/json")
                                .setStringBody("")
                                .asString()
                                .withResponse()
                                .get();
                        String result = response.getResult();
                        if (result.equalsIgnoreCase("YES"))
                            Toast.makeText(CalculateTimeService.this, "UPDATED TO SERVER", Toast.LENGTH_SHORT).show();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CalculateTimeService.this, "Time Taken A to B: " + ReusableClass.getFromPreference("timeTakenAtoB", CalculateTimeService.this), Toast.LENGTH_SHORT).show();
                } else if (location.distanceTo(locationB) < 200 && stopTimeDiff != 0
                        && ReusableClass.getFromPreference("stopTimeBtoA", CalculateTimeService.this).equalsIgnoreCase("")
                        && ReusableClass.getFromPreference("startTimeBtoA", CalculateTimeService.this).equalsIgnoreCase("")) {
                    long time = System.currentTimeMillis();

                    ReusableClass.saveInPreference("startTimeBtoA", String.valueOf(time), CalculateTimeService.this);
                    Toast.makeText(CalculateTimeService.this, "START B to A", Toast.LENGTH_SHORT).show();
                } else if (location.distanceTo(locationA) < 200 && stopTimeDiff != 0
                        && ReusableClass.getFromPreference("stopTimeAtoB", CalculateTimeService.this).equalsIgnoreCase("")
                        && ReusableClass.getFromPreference("startTimeAtoB", CalculateTimeService.this).equalsIgnoreCase("")) {
                    long time = System.currentTimeMillis();

                    ReusableClass.saveInPreference("startTimeAtoB", String.valueOf(time), CalculateTimeService.this);
                    Toast.makeText(CalculateTimeService.this, "START A to B", Toast.LENGTH_SHORT).show();
                } else {
                    locationManager.removeUpdates(this);
                    stopSelf();
                }
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
}
