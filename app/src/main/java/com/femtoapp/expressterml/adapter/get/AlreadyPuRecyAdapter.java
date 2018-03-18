package com.femtoapp.expressterml.adapter.get;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.femtoapp.expressterml.activity.printer.PrintActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.bean.Order;
import com.femtoapp.expressterml.activity.get.RecipientActivity;

import java.util.ArrayList;

/**
 * Created by Autism on 2018/1/31.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class AlreadyPuRecyAdapter extends RecyclerView.Adapter<AlreadyPuRecyAdapter.ViewHolder> {

    private ArrayList<Order> mData;

    public AlreadyPuRecyAdapter(ArrayList<Order> mData) {
        this.mData = mData;
    }

    public void updateData(ArrayList<Order> Data) {
        this.mData = Data;
        notifyDataSetChanged();
    }

    @Override
    public AlreadyPuRecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_already_pu_item, parent, false);
        AlreadyPuRecyAdapter.ViewHolder holder = new AlreadyPuRecyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlreadyPuRecyAdapter.ViewHolder holder, int position) {
        final Order order = mData.get(position);
        holder.tv_order_number.setText(order.getF_cOrderNumber());
        holder.tv_sender_name.setText(order.getF_cFromPerson());
        if (order.getStatus() == 0) {
            holder.tv_order_transportState.setText("保存");
        } else if (order.getStatus() == 1) {
            holder.tv_order_transportState.setText("提交");
        } else if (order.getStatus() == 2) {
            holder.tv_order_transportState.setText("待揽收");
        } else if (order.getStatus() == 3) {
            holder.tv_order_transportState.setText("发货");
        } else if (order.getStatus() == 4) {
            holder.tv_order_transportState.setText("途中");
        } else if (order.getStatus() == 5) {
            holder.tv_order_transportState.setText("到货");
        } else if (order.getStatus() == 6) {
            holder.tv_order_transportState.setText("中转");
        } else if (order.getStatus() == 7) {
            holder.tv_order_transportState.setText("派送中");
        } else if (order.getStatus() == 8) {
            holder.tv_order_transportState.setText("已签收");
        }


        holder.rl_order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PrintActivity.class);
                intent.putExtra("id", order.getId());
                intent.putExtra("f_cFromPerson", order.getF_cFromPerson());
                intent.putExtra("f_cFromTel", order.getF_cFromTel());
                intent.putExtra("f_cFromStation", order.getF_cFromStation());
                intent.putExtra("f_cFromAddress", order.getF_cFromAddress());
                intent.putExtra("f_cFromAddress_port", order.getF_cFromAddress_port());
                intent.putExtra("f_cToPerson", order.getF_cToPerson());
                intent.putExtra("f_cToTel", order.getF_cToTel());
                intent.putExtra("f_cToStation", order.getF_cToStation());
                intent.putExtra("f_cToAddress", order.getF_cToAddress());
                intent.putExtra("f_cToAddress_port", order.getF_cToAddress_port());
                intent.putExtra("f_cGoods", order.getF_cGoods());
                intent.putExtra("f_nPiece", order.getF_nPiece());
                intent.putExtra("f_nWeight", order.getF_nWeight());
                intent.putExtra("nlong", order.getNlong());
                intent.putExtra("nwidth", order.getNwidth());
                intent.putExtra("nheight", order.getNheight());
                intent.putExtra("f_nCubicMetre", order.getF_nCubicMetre());
                intent.putExtra("f_cOrderNumber", order.getF_cOrderNumber());
                intent.putExtra("status", order.getStatus());
                intent.putExtra("f_nMoney", order.getF_nMoney());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_order_number;
        TextView tv_sender_name;
        TextView tv_order_transportState;
        TextView tv_go;
        RelativeLayout rl_order_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_number = (TextView) itemView.findViewById(R.id.tv_order_number);
            tv_sender_name = (TextView) itemView.findViewById(R.id.tv_sender_name);
            tv_order_transportState = (TextView) itemView.findViewById(R.id.tv_order_transportState);
            tv_go = (TextView) itemView.findViewById(R.id.tv_go);
            rl_order_item = (RelativeLayout) itemView.findViewById(R.id.rl_order_item);
            rl_order_item.setOnClickListener(this);
            tv_go.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_go:
                    Intent intent = new Intent(v.getContext(), RecipientActivity.class);
                    v.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
