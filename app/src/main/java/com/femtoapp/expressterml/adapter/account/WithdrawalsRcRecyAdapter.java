package com.femtoapp.expressterml.adapter.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.bean.Pay;

import java.util.ArrayList;

/**
 * Created by Autism on 2018/2/9.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class WithdrawalsRcRecyAdapter extends RecyclerView.Adapter<WithdrawalsRcRecyAdapter.ViewHolder> {


    private ArrayList<Pay> mData;

    public WithdrawalsRcRecyAdapter(ArrayList<Pay> mData) {
        this.mData = mData;
    }

    public void updateData(ArrayList<Pay> Data) {
        this.mData = Data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_withdrawals_rs_item, parent, false);
        WithdrawalsRcRecyAdapter.ViewHolder holder = new WithdrawalsRcRecyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WithdrawalsRcRecyAdapter.ViewHolder holder, int position) {
        Pay pay = mData.get(position);
        holder.tv_time_value.setText(pay.getApply_time());
        holder.tv_money_value.setText(pay.getMoney() + "元");
        if (pay.getApply_status() == 0) {
            holder.tv_status_value.setText("申请中");
        } else if (pay.getApply_status() == 1) {
            holder.tv_status_value.setText("审核中");
        } else if (pay.getApply_status() == 2) {
            holder.tv_status_value.setText("通过");
        } else if (pay.getApply_status() == 3) {
            holder.tv_status_value.setText("不通过");
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tv_time_value;
        private TextView tv_money_value;
        private TextView tv_status_value;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time_value = (TextView) itemView.findViewById(R.id.tv_time_value);
            tv_money_value = (TextView) itemView.findViewById(R.id.tv_money_value);
            tv_status_value = (TextView) itemView.findViewById(R.id.tv_status_value);
        }

        @Override
        public void onClick(View v) {

        }
    }


}
