package com.ibraheem.ibraheem;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdvertiseMentDetails extends AppCompatActivity {

    @Bind(R.id.actionbar_toolbar)
    Toolbar actionbarToolbar;
    private String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_ment_details);
        ButterKnife.bind(this);

        setSupportActionBar(actionbarToolbar);
        actionbarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        place = getIntent().getStringExtra("place");

        if (place.equalsIgnoreCase("ksa"))
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorPrimary));
        else
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorAccent));
    }
}
