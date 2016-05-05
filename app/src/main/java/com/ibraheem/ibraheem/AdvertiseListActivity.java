package com.ibraheem.ibraheem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ibraheem.ibraheem.models.AdsDetails;
import com.ibraheem.ibraheem.utils.URLConstantClass;
import com.ibraheem.ibraheem.widget.AdvertiseListAdapter;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdvertiseListActivity extends AppCompatActivity {

    @Bind(R.id.AdsList)
    RecyclerView AdsList;

    LinearLayoutManager linearLayoutManager;
    AdvertiseListAdapter advertiseListAdapter;
    @Bind(R.id.actionbar_toolbar)
    Toolbar actionbarToolbar;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.noData)
    TextView noData;
    private String place;
    private String adsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_list);
        ButterKnife.bind(this);

        place = getIntent().getStringExtra("place");
        adsType = getIntent().getStringExtra("ads_type");
        setSupportActionBar(actionbarToolbar);
        actionbarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        AdsList.setLayoutManager(linearLayoutManager);
        advertiseListAdapter = new AdvertiseListAdapter(this, place);
        AdsList.setAdapter(advertiseListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (place.equalsIgnoreCase("ksa")) {
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorPrimary));
            progress.setVisibility(View.VISIBLE);
            Response<String> response = null;
            try {
                response = Ion.with(this)
                        .load("POST", URLConstantClass.getAds("KSA to KWT", adsType, ""))
                        .addHeader("Content-Type", "application/json")
                        .setStringBody("")
                        .asString()
                        .withResponse()
                        .get();

                if (response.getHeaders().code() == 200) {
                    progress.setVisibility(View.INVISIBLE);

                    String result = response.getResult();
                    ArrayList<AdsDetails> adsList = AdsDetails.toList(result);
                    if (adsList.size() > 0) {
                        noData.setVisibility(View.INVISIBLE);
                        advertiseListAdapter.addAll(adsList);
                    } else
                        noData.setVisibility(View.VISIBLE);

                } else
                    progress.setVisibility(View.INVISIBLE);
            } catch (Throwable throwable) {
                progress.setVisibility(View.INVISIBLE);
                Log.e("AJTAG", "ERROR: " + throwable.toString());
            }
        } else {
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorAccent));
            Response<String> response = null;
            try {
                response = Ion.with(this)
                        .load("POST", URLConstantClass.getAds("KWT to KSA", adsType, ""))
                        .addHeader("Content-Type", "application/json")
                        .setStringBody("")
                        .asString()
                        .withResponse()
                        .get();

                if (response.getHeaders().code() == 200) {
                    progress.setVisibility(View.INVISIBLE);
                    String result = response.getResult();
                    ArrayList<AdsDetails> adsList = AdsDetails.toList(result);
                    if (adsList.size() > 0) {
                        noData.setVisibility(View.INVISIBLE);
                        advertiseListAdapter.addAll(adsList);
                    } else
                        noData.setVisibility(View.VISIBLE);

                    advertiseListAdapter.addAll(adsList);
                } else
                    progress.setVisibility(View.INVISIBLE);
            } catch (Throwable throwable) {
                progress.setVisibility(View.INVISIBLE);
                Log.e("AJTAG", "ERROR: " + throwable.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ads_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_ads_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //searchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color.text_hint_color));
        searchAutoComplete.setHint("Search...");
        searchAutoComplete.setTextColor(Color.WHITE);

        ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //searchCloseIcon.setImageResource(R.drawable.close_icon);

        MenuItemCompat.collapseActionView(searchItem);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (place.equalsIgnoreCase("ksa")) {
                        Response<String> response = null;
                        try {
                            response = Ion.with(AdvertiseListActivity.this)
                                    .load("POST", URLConstantClass.getAds("KSA to KWT", adsType, query.toString()))
                                    .addHeader("Content-Type", "application/json")
                                    .setStringBody("")
                                    .asString()
                                    .withResponse()
                                    .get();

                            if (response.getHeaders().code() == 200) {
                                progress.setVisibility(View.INVISIBLE);
                                String result = response.getResult();
                                ArrayList<AdsDetails> adsList = AdsDetails.toList(result);
                                if (adsList.size() > 0) {
                                    noData.setVisibility(View.INVISIBLE);
                                    advertiseListAdapter.addAll(adsList);
                                } else
                                    noData.setVisibility(View.VISIBLE);

                                advertiseListAdapter.addAll(adsList);
                            } else
                                progress.setVisibility(View.INVISIBLE);
                        } catch (Throwable throwable) {
                            progress.setVisibility(View.INVISIBLE);
                            Log.e("AJTAG", "ERROR: " + throwable.toString());
                        }
                    } else {
                        Response<String> response = null;
                        try {
                            response = Ion.with(AdvertiseListActivity.this)
                                    .load("POST", URLConstantClass.getAds("KWT to KSA", adsType, query.toString()))
                                    .addHeader("Content-Type", "application/json")
                                    .setStringBody("")
                                    .asString()
                                    .withResponse()
                                    .get();

                            if (response.getHeaders().code() == 200) {
                                progress.setVisibility(View.INVISIBLE);
                                String result = response.getResult();
                                ArrayList<AdsDetails> adsList = AdsDetails.toList(result);
                                if (adsList.size() > 0) {
                                    noData.setVisibility(View.INVISIBLE);
                                    advertiseListAdapter.addAll(adsList);
                                } else
                                    noData.setVisibility(View.VISIBLE);

                                advertiseListAdapter.addAll(adsList);
                            } else
                                progress.setVisibility(View.INVISIBLE);
                        } catch (Throwable throwable) {
                            progress.setVisibility(View.INVISIBLE);
                            Log.e("AJTAG", "ERROR: " + throwable.toString());
                        }
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

}
