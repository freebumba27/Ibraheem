package com.ibraheem.ibraheem.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ibraheem.ibraheem.AdvertiseMentDetails;
import com.ibraheem.ibraheem.R;
import com.ibraheem.ibraheem.models.AdsDetails;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirban on 26/03/2016.
 */
public class AdvertiseListAdapter extends RecyclerView.Adapter<AdvertiseListAdapter.MyHolder> {

    private Context context;
    private String place;
    private ArrayList<AdsDetails> adsList;

    public AdvertiseListAdapter(Context context, String place) {
        this.context = context;
        this.place = place;
        adsList = new ArrayList<>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_list, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.title.setText(adsList.get(position).getTitle());
        holder.subTitle.setText(adsList.get(position).getDescription());
        if (adsList.get(position).getImage() != null) {
            Glide.with(context).load(adsList.get(position).getImage())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(holder.car_image);
        } else {
            holder.car_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder));
        }
        holder.adsListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdsDetails adsDetails = adsList.get(position);
                Intent i = new Intent(context, AdvertiseMentDetails.class);
                i.putExtra("place", place);
                i.putExtra("adsDetails", adsDetails.toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    public void addAll(ArrayList<AdsDetails> myAdsList) {
        adsList = myAdsList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subTitle)
        TextView subTitle;
        @Bind(R.id.car_image)
        ImageView car_image;
        @Bind(R.id.adsListRow)
        LinearLayout adsListRow;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
