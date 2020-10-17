package com.technorizen.doctor.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.DoctorListActivity;
import com.technorizen.doctor.models.ModelDoctors;
import com.technorizen.doctor.models.ModelServices;

import java.util.ArrayList;

public class AdapterDoctors extends RecyclerView.Adapter<AdapterDoctors.AdapterDoctorsViewHolder> {

    Context mContext;
    ArrayList<ModelDoctors.Result> doctorsList;
    final int limit = 3;
    boolean isHome;

    public AdapterDoctors(Context mContext, ArrayList<ModelDoctors.Result> doctorsList,boolean isHome) {
        this.mContext = mContext;
        this.doctorsList = doctorsList;
        this.isHome = isHome;
    }

    @NonNull
    @Override
    public AdapterDoctors.AdapterDoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_doctors, parent, false);
        return new AdapterDoctors.AdapterDoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctors.AdapterDoctorsViewHolder holder, int position) {

        ModelDoctors.Result data = doctorsList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ididididid","id = " + data.getId());

                mContext.startActivity(new Intent(mContext, DoctorListActivity.class)
                        .putExtra("header" , data.getCategory_name())
                        .putExtra("id" , data.getId())
                        .putExtra("isDoctor" , true)
                        .putExtra("isService" , false)
                        .putExtra("isnearby",false)
                );

            }
        });

        holder.tvDoctorCat.setText(data.getCategory_name());
        Picasso.get().load(data.getImage()).into(holder.doctor_icon_img);

    }

    @Override
    public int getItemCount() {
        if(isHome){
            if(doctorsList.size() > limit){
                return limit;
            } else {
                return doctorsList.size();
            }
        } else{
            return doctorsList.size();
        }
    }

    public class AdapterDoctorsViewHolder extends RecyclerView.ViewHolder {

        ImageView doctor_icon_img;
        TextView tvDoctorCat;

        public AdapterDoctorsViewHolder(@NonNull View itemView) {
            super(itemView);

            doctor_icon_img = itemView.findViewById(R.id.doctor_icon_img);
            tvDoctorCat = itemView.findViewById(R.id.tvDoctorCat);

        }
    }

}
