package com.technorizen.doctor.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.DoctorBookingActivity;
import com.technorizen.doctor.activities.DoctorClenicDetailActivity;
import com.technorizen.doctor.models.ModelDoctorsList;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDoctorsList extends RecyclerView.Adapter<AdapterDoctorsList.AdapterDoctorsListViewHolder> {

    Context mContext;
    ArrayList<ModelDoctorsList.Result> doctorsList;

    public AdapterDoctorsList(Context mContext, ArrayList<ModelDoctorsList.Result> doctorsList) {
        this.mContext = mContext;
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public AdapterDoctorsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_doctors_list,parent,false);
        return new AdapterDoctorsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctorsListViewHolder holder, int position) {

        ModelDoctorsList.Result data = doctorsList.get(position);

        holder.tvDoctorName.setText(data.getUser_name());
        holder.tvExperience.setText(data.getYear_of_experience() + " years of experience");
        holder.tvAddress.setText(data.getAddress());
        holder.tvQualifications.setText(data.getQualification());
        holder.tvFees.setText("₹ " + data.getFees() + " onwards");

        if(!data.getImage().endsWith("/")) {
            Picasso.get().load(data.getImage()).into(holder.doctorImage);
        } else {
            holder.doctorImage.setImageResource(R.drawable.profile3);
        }

        holder.contact_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DoctorBookingActivity.class)
                        .putExtra("category_id",data.getCategory_id())
                        .putExtra("doctor_detail",data)
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    public class AdapterDoctorsListViewHolder extends RecyclerView.ViewHolder {

        TextView tvDoctorName,tvQualifications,tvQualType,tvExperience,tvAddress,tvFees,contact_clinic;
        CircleImageView doctorImage;

        public AdapterDoctorsListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            tvQualifications = itemView.findViewById(R.id.tvQualifications);
            tvQualType = itemView.findViewById(R.id.tvQualType);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvFees = itemView.findViewById(R.id.tvFees);
            doctorImage = itemView.findViewById(R.id.doctorImage);
            contact_clinic = itemView.findViewById(R.id.contact_clinic);
        }


    }


}
