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
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.BloodPressureActivity;
import com.technorizen.doctor.activities.BloodSugarActivity;
import com.technorizen.doctor.activities.ConditionsAndTherapyActivity;
import com.technorizen.doctor.activities.DoctorListActivity;
import com.technorizen.doctor.models.ModelServices;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterServices extends RecyclerView.Adapter<AdapterServices.AdapterServicesViewHolder> {

    Context mContext;
    ArrayList<ModelServices.Result> servicesList;
    final int limit = 3;
    boolean isHome;

    public AdapterServices(Context mContext, ArrayList<ModelServices.Result> servicesList,boolean isHome) {
        this.mContext = mContext;
        this.servicesList = servicesList;
        this.isHome = isHome;
    }

    @NonNull
    @Override
    public AdapterServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_services,parent,false);
        return new AdapterServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServicesViewHolder holder, int position) {
        ModelServices.Result data = servicesList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getId().equals("1")){
                    mContext.startActivity(new Intent(mContext, BloodPressureActivity.class).putExtra("data",servicesList.get(position)));
                }if (data.getId().equals("2")){
                    mContext.startActivity(new Intent(mContext, BloodSugarActivity.class).putExtra("data",servicesList.get(position)));
                }if (data.getId().equals("3")){
                    mContext.startActivity(new Intent(mContext, ConditionsAndTherapyActivity.class).putExtra("data",servicesList.get(position)));
                }
               /* mContext.startActivity(new Intent(mContext, DoctorListActivity.class)
                        .putExtra("header" , data.getName())
                        .putExtra("id" , data.getId())
                        .putExtra("isDoctor" , false)
                        .putExtra("isService" , true)
                        .putExtra("isnearby",false)
                );*/
            }
        });

        holder.tvServiceCat.setText(data.getName());
        Picasso.get().load(data.getImage()).into(holder.services_icon_img);

    }

    @Override
    public int getItemCount() {
        if(isHome){
            if(servicesList.size() > limit){
                return limit;
            } else {
                return servicesList.size();
            }
        } else{
            return servicesList.size();
        }

    }

    public class AdapterServicesViewHolder extends RecyclerView.ViewHolder{

        ImageView services_icon_img;
        TextView tvServiceCat;

        public AdapterServicesViewHolder(@NonNull View itemView) {
            super(itemView);

            services_icon_img = itemView.findViewById(R.id.services_icon_img);
            tvServiceCat = itemView.findViewById(R.id.tvServiceCat);

        }
    }


}
