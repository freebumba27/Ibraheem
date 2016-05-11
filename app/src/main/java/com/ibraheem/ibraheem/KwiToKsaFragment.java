package com.ibraheem.ibraheem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibraheem.ibraheem.models.TravelTimeResponce;
import com.ibraheem.ibraheem.utils.URLConstantClass;
import com.ibraheem.ibraheem.widget.AdsRecyclerViewAdapter;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class KwiToKsaFragment extends Fragment {

    @Bind(R.id.adsRecyclerView)
    RecyclerView adsRecyclerView;
    LinearLayoutManager linearLayoutManager;
    @Bind(R.id.timeTextViewTime)
    TextView timeTextViewTime;
    @Bind(R.id.timeTextViewUpdatedTime)
    TextView timeTextViewUpdatedTime;

    AdsRecyclerViewAdapter adsRecyclerViewAdapter;

    public KwiToKsaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kwi_to_ksa, container, false);
        ButterKnife.bind(this, view);

        try {
            Response<String> response = Ion.with(this)
                    .load("POST", URLConstantClass.getTimeUrl("BtoA"))
                    .addHeader("Content-Type", "application/json")
                    .setStringBody("")
                    .asString()
                    .withResponse()
                    .get();
            String result = response.getResult();

            ArrayList<TravelTimeResponce> travelTimeResponse = TravelTimeResponce.toList(result);

            long miliSec = Long.parseLong(travelTimeResponse.get(0).getDuration_milisec());
            long since = Long.parseLong(travelTimeResponse.get(0).getDevice_updated_time());

            long hours = TimeUnit.MILLISECONDS.toHours(miliSec);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(miliSec);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(miliSec);

            if (hours == 0) {
                if (minutes == 0) {
                    timeTextViewTime.setText(seconds + " sec or less");
                } else
                    timeTextViewTime.setText(minutes + " min or less");
            } else {
                timeTextViewTime.setText(new SimpleDateFormat("hh:mm").format(new Date(miliSec)) + " hours or less");
                timeTextViewTime.setTextSize(15);
            }
            long timeDiff = (System.currentTimeMillis() - since);

            long daySince = TimeUnit.MILLISECONDS.toDays(timeDiff);
            long hoursSince = TimeUnit.MILLISECONDS.toHours(timeDiff);
            long minutesSince = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
            long secondsSince = TimeUnit.MILLISECONDS.toSeconds(timeDiff);

            if (hoursSince == 0) {
                if (minutesSince == 0) {
                    timeTextViewUpdatedTime.setText("Since " + String.format("%01d", secondsSince) + " sec");
                } else
                    timeTextViewUpdatedTime.setText("Since " + String.format("%01d", minutesSince) + " min");
            } else {
                if (hoursSince > 24) {
                    timeTextViewUpdatedTime.setText("Since " + String.format("%01d", daySince) + " days");
                    timeTextViewUpdatedTime.setTextSize(15);
                } else {
                    timeTextViewUpdatedTime.setText("Since " + hoursSince + ":" + String.format("%01d", (minutesSince - hoursSince * 60)) + " hours");
                    timeTextViewUpdatedTime.setTextSize(15);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        linearLayoutManager = new LinearLayoutManager(getContext());
        adsRecyclerView.setLayoutManager(linearLayoutManager);

        adsRecyclerViewAdapter = new AdsRecyclerViewAdapter("kwi", getContext());
        adsRecyclerView.setAdapter(adsRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
