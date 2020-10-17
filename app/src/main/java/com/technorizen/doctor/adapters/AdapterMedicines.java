package com.technorizen.doctor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Interfaces.onClickProductListener;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.shops.MedicensActivity;
import com.technorizen.doctor.activities.shops.MedicinesDetailActivity;
import com.technorizen.doctor.models.ModelProduct;

import java.util.ArrayList;

public class AdapterMedicines extends RecyclerView.Adapter<AdapterMedicines.AdapterMedicinesViewHolder> {

    Context mContext;
    ArrayList<ModelProduct> shopsList;
    private onClickProductListener listener;

    public AdapterMedicines(Context mContext, ArrayList<ModelProduct> shopsList) {
        this.mContext = mContext;
        this.shopsList = shopsList;
    }
    public AdapterMedicines callBack(onClickProductListener listener){
        this.listener=listener;
        return this;
    }

    @NonNull
    @Override
    public AdapterMedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_medicens,parent,false);
        return new AdapterMedicinesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMedicinesViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            listener.onClickProduct(shopsList.get(position));
        });
        ImageView productImage=holder.itemView.findViewById(R.id.productImage);
        TextView tvProductName=holder.itemView.findViewById(R.id.tvProductName);
        TextView tvProductDescription=holder.itemView.findViewById(R.id.tvProductDescription);
        TextView tvProductPrice=holder.itemView.findViewById(R.id.tvProductPrice);
        Picasso.get().load(shopsList.get(position).getImage()).placeholder(R.drawable.medicines1).into(productImage);
        tvProductName.setText(shopsList.get(position).getName());
        tvProductDescription.setText(shopsList.get(position).getDescription());
        tvProductPrice.setText("$"+shopsList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }

    public class AdapterMedicinesViewHolder extends RecyclerView.ViewHolder{

        public AdapterMedicinesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
