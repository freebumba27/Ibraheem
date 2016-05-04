package com.ibraheem.ibraheem.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibraheem.ibraheem.AdvertiseListActivity;
import com.ibraheem.ibraheem.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirban on 22/03/2016.
 */
public class AdsRecyclerViewAdapter extends RecyclerView.Adapter<AdsRecyclerViewAdapter.MyViewHolder> {

    String adsList[] = new String[]{"Restaurants", "Hotels", "Other Ads"};
    private String place;
    private Context context;


    public AdsRecyclerViewAdapter(String place, Context context) {
        this.place = place;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (place.equalsIgnoreCase("ksa"))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_list_blue_row, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_list_green_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(adsList[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdvertiseListActivity.class);
                i.putExtra("place", place);
                i.putExtra("ads_type", adsList[position]);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView)
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
