package com.technorizen.doctor.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.DoctorListActivity;
import com.technorizen.doctor.fragments.TalkDoctorFragment;
import com.technorizen.doctor.models.ModelDoctors;
import java.util.ArrayList;

public class AdapterDoctorTalktime extends RecyclerView.Adapter<AdapterDoctorTalktime.AdapterDoctorTalktimeViewHolder> {

    Context mContext;
    ArrayList<ModelDoctors.Result> doctorsList;
    boolean isHome;
    TalkDoctorCallback doctorCallback;

    public interface TalkDoctorCallback {
        void openDilaogDoctorCallback(String type);
    }

    public AdapterDoctorTalktime(Context mContext, ArrayList<ModelDoctors.Result> doctorsList, boolean isHome,TalkDoctorCallback doctorCallback) {
        this.mContext = mContext;
        this.doctorsList = doctorsList;
        this.isHome = isHome;
        this.doctorCallback = doctorCallback;
    }

    @NonNull
    @Override
    public AdapterDoctorTalktime.AdapterDoctorTalktimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_talk_doctors, parent, false);
        return new AdapterDoctorTalktime.AdapterDoctorTalktimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctorTalktime.AdapterDoctorTalktimeViewHolder holder, int position) {

        ModelDoctors.Result data = doctorsList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // TalkDoctorFragment.openLocationPickerDialog(doctorsList.get(position).getCategory_name());

                doctorCallback.openDilaogDoctorCallback(doctorsList.get(position).getId());

             /* mContext.startActivity(new Intent(mContext, DoctorListActivity.class)
                        .putExtra("header", data.getCategory_name())
                        .putExtra("id", data.getId())
                );*/

            }
        });

        holder.tvName.setText(data.getCategory_name());
        Picasso.get().load(data.getImage()).into(holder.doctor_icon_img);

    }


    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    public class AdapterDoctorTalktimeViewHolder extends RecyclerView.ViewHolder {

        ImageView doctor_icon_img;
        TextView tvName;

        public AdapterDoctorTalktimeViewHolder(@NonNull View itemView) {
            super(itemView);

            doctor_icon_img = itemView.findViewById(R.id.doctor_icon_img);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }

}
