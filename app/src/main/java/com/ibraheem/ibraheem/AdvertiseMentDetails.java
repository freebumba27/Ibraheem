package com.ibraheem.ibraheem;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ibraheem.ibraheem.models.AdsDetails;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdvertiseMentDetails extends AppCompatActivity {

    private String place;

    @Bind(R.id.actionbar_toolbar)
    Toolbar actionbarToolbar;
    @Bind(R.id.imageViewAds)
    ImageView imageViewAds;
    @Bind(R.id.textViewTitle)
    TextView textViewTitle;
    @Bind(R.id.textViewDesc)
    TextView textViewDesc;
    @Bind(R.id.textViewAdsLink)
    TextView textViewAdsLink;

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
        AdsDetails adsDetails = new Gson().fromJson(getIntent().getStringExtra("adsDetails"), AdsDetails.class);

        if (place.equalsIgnoreCase("ksa"))
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorPrimary));
        else
            actionbarToolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorAccent));

        populateData(adsDetails);
    }

    private void populateData(AdsDetails adsDetails) {
        if (adsDetails.getImage() != null) {
            Glide.with(this).load(adsDetails.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(imageViewAds);
        } else {
            imageViewAds.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder));
        }
        textViewTitle.setText(adsDetails.getTitle());
        textViewDesc.setText(adsDetails.getDescription());
        String text = "<a href='" + adsDetails.getWebsite_link() + "'> " + adsDetails.getWebsite_link() + " </a>";
        textViewAdsLink.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAdsLink.setText(Html.fromHtml(text));
    }
}
