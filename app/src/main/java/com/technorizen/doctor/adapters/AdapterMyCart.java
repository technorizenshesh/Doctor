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

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.technorizen.doctor.R;
import com.technorizen.doctor.models.ModelProduct;

import java.util.ArrayList;

public class AdapterMyCart extends RecyclerView.Adapter<AdapterMyCart.AdapterMyCartViewHolder> {

    Context mContext;
    ArrayList<ModelProduct> shopsList;

    public AdapterMyCart(Context mContext, ArrayList<ModelProduct> shopsList) {
        this.mContext = mContext;
        this.shopsList = shopsList;
    }

    @NonNull
    @Override
    public AdapterMyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_card_item,parent,false);
        return new AdapterMyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyCartViewHolder holder, int position) {
        RoundedImageView item_img=holder.itemView.findViewById(R.id.item_img);
        TextView item_name=holder.itemView.findViewById(R.id.item_name);
        TextView tvPriceandquantity=holder.itemView.findViewById(R.id.tvPriceandquantity);
        ImageView delete=holder.itemView.findViewById(R.id.delete);
        Picasso.get().load(shopsList.get(position).getImage()).into(item_img);
        item_name.setText(shopsList.get(position).getName());
        tvPriceandquantity.setText(shopsList.get(position).getPrice()+"X"+shopsList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }

    public class AdapterMyCartViewHolder extends RecyclerView.ViewHolder{

        public AdapterMyCartViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
