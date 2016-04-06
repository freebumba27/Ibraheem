package com.ibraheem.ibraheem.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by anirban on 05/04/2016.
 */
public class TravelTimeResponce {
    private String id;
    private String imei_no;
    private String server_updated_time;
    private String device_updated_time;
    private String duration_milisec;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImei_no() {
        return imei_no;
    }

    public void setImei_no(String imei_no) {
        this.imei_no = imei_no;
    }

    public String getServer_updated_time() {
        return server_updated_time;
    }

    public void setServer_updated_time(String server_updated_time) {
        this.server_updated_time = server_updated_time;
    }

    public String getDevice_updated_time() {
        return device_updated_time;
    }

    public void setDevice_updated_time(String device_updated_time) {
        this.device_updated_time = device_updated_time;
    }

    public String getDuration_milisec() {
        return duration_milisec;
    }

    public void setDuration_milisec(String duration_milisec) {
        this.duration_milisec = duration_milisec;
    }

    public static ArrayList<TravelTimeResponce> toList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<TravelTimeResponce>>() {
        }.getType());
    }
}
