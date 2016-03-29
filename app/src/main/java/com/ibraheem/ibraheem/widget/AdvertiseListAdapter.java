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

import com.ibraheem.ibraheem.AdvertiseMentDetails;
import com.ibraheem.ibraheem.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirban on 26/03/2016.
 */
public class AdvertiseListAdapter extends RecyclerView.Adapter<AdvertiseListAdapter.MyHolder> {

    private Context context;
    private String place;

    public AdvertiseListAdapter(Context context, String place) {
        this.context = context;
        this.place = place;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_list, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.title.setText("11 Month Old Honda");
        holder.subTitle.setText("Only 3700KM travelled. It's in good condition. You can come to see the car.");
        holder.car_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.honda_newbranding));
        holder.adsListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdvertiseMentDetails.class);
                i.putExtra("place", place);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
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
