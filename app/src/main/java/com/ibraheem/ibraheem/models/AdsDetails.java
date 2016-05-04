package com.ibraheem.ibraheem.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by anirban on 5/4/16.
 */
public class AdsDetails {
    private String id;
    private String customer_name;
    private String customer_phone;
    private String customer_email;
    private String title;
    private String description;
    private String image;
    private String website_link;
    private String ads_for;
    private String ads_type;
    private String created_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getAds_for() {
        return ads_for;
    }

    public void setAds_for(String ads_for) {
        this.ads_for = ads_for;
    }

    public String getAds_type() {
        return ads_type;
    }

    public void setAds_type(String ads_type) {
        this.ads_type = ads_type;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public static ArrayList<AdsDetails> toList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<AdsDetails>>() {
        }.getType());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
