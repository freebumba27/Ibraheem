package com.ibraheem.ibraheem;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ibraheem.ibraheem.widget.AdvertiseListAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_list);
        ButterKnife.bind(this);

        place = getIntent().getStringExtra("place");
        setSupportActionBar(actionbarToolbar);
        actionbarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (place.equalsIgnoreCase("ksa"))
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorPrimary));
        else
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorAccent));

        linearLayoutManager = new LinearLayoutManager(this);
        AdsList.setLayoutManager(linearLayoutManager);
        advertiseListAdapter = new AdvertiseListAdapter(this, place);
        AdsList.setAdapter(advertiseListAdapter);
    }
}
