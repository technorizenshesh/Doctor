package com.technorizen.doctor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Interfaces.onClickShopListener;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.shops.MedicensActivity;
import com.technorizen.doctor.models.ModelShop;

import java.util.ArrayList;

public class AdapterShops extends RecyclerView.Adapter<AdapterShops.AdapterShopsViewHolder> {

    Context mContext;
    ArrayList<ModelShop> shopsList;
    private onClickShopListener listener;

    public AdapterShops(Context mContext, ArrayList<ModelShop> shopsList) {
        this.mContext = mContext;
        this.shopsList = shopsList;
    }
    public AdapterShops callBack(onClickShopListener listener){
        this.listener=listener;
        return this;
    }

    @NonNull
    @Override
    public AdapterShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shops,parent,false);
        return new AdapterShopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShopsViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            listener.onClickShop(shopsList.get(position));
        });
        RoundedImageView image=holder.itemView.findViewById(R.id.ivGoodsImage);
        TextView name=holder.itemView.findViewById(R.id.tvShippmentName);
        TextView address=holder.itemView.findViewById(R.id.tvAddress);
        Picasso.get().load(shopsList.get(position).getImage()).placeholder(R.drawable.medical_store1).into(image);
        name.setText(shopsList.get(position).getName());
        address.setText(shopsList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }

    public class AdapterShopsViewHolder extends RecyclerView.ViewHolder{

        public AdapterShopsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
