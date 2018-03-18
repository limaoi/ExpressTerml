package com.femtoapp.expressterml.adapter.other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.bean.Dot;

import java.util.ArrayList;

/**
 * Created by Autism on 2018/1/23.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class DotInfoRecyAdapter extends RecyclerView.Adapter<DotInfoRecyAdapter.ViewHolder> {


    private ArrayList<Dot> mData;

    public DotInfoRecyAdapter(ArrayList<Dot> mData) {
        this.mData = mData;
    }

    public void updateData(ArrayList<Dot> Data) {
        this.mData = Data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        DotInfoRecyAdapter.ViewHolder holder = new DotInfoRecyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DotInfoRecyAdapter.ViewHolder holder, int position) {
        Dot dot = mData.get(position);
        holder.tv_region.setText(dot.getRegion());
        holder.tv_street.setText(dot.getStreet());
        holder.tv_distance.setText(dot.getDistance());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_region;
        private TextView tv_street;
        private TextView tv_distance;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_region = (TextView) itemView.findViewById(R.id.tv_region);
            tv_street = (TextView) itemView.findViewById(R.id.tv_street);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
        }
    }
}
