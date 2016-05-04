package com.ibraheem.ibraheem;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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

        if (place.equalsIgnoreCase("ksa")) {
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorPrimary));

            Response<String> response = null;
            try {
                response = Ion.with(this)
                        .load("POST", URLConstantClass.getAds("KSA to KWT", adsType))
                        .addHeader("Content-Type", "application/json")
                        .setStringBody("")
                        .asString()
                        .withResponse()
                        .get();

                String result = response.getResult();
                ArrayList<AdsDetails> adsList = AdsDetails.toList(result);
                advertiseListAdapter.addAll(adsList);
            } catch (Throwable throwable) {
                Log.e("AJTAG", "ERROR: " + throwable.toString());
            }
        } else {
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorAccent));
            Response<String> response = null;
            try {
                response = Ion.with(this)
                        .load("POST", URLConstantClass.getAds("KWT to KSA", adsType))
                        .addHeader("Content-Type", "application/json")
                        .setStringBody("")
                        .asString()
                        .withResponse()
                        .get();

                String result = response.getResult();
                ArrayList<AdsDetails> adsList = AdsDetails.toList(result);
                advertiseListAdapter.addAll(adsList);
            } catch (Throwable throwable) {
                Log.e("AJTAG", "ERROR: " + throwable.toString());
            }
        }

    }
}
