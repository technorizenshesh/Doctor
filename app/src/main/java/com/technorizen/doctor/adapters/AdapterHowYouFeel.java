package com.technorizen.doctor.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.BloodPressureActivity;
import com.technorizen.doctor.activities.BloodSugarActivity;
import com.technorizen.doctor.activities.ConditionsAndTherapyActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterHowYouFeel extends RecyclerView.Adapter<AdapterHowYouFeel.AdapterHowYouFeelViewHolder> {

    Context mContext;
    ArrayList<String> feelList;
    int previousPosition = 0;
    String whatScreen;

    public AdapterHowYouFeel(Context mContext, ArrayList<String> feelList,String whatScreen) {
        this.mContext = mContext;
        this.feelList = feelList;
        this.whatScreen = whatScreen;
    }

    @NonNull
    @Override
    public AdapterHowYouFeelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter__how_you_feel,parent,false);
        return new AdapterHowYouFeelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHowYouFeelViewHolder holder, int position) {

        holder.tvFeel.setText(feelList.get(position));

        holder.tvFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("Pressure".equalsIgnoreCase(whatScreen)){
                    BloodPressureActivity.feelDataBloodPressure = (feelList.get(position));
                }else if("Sugar".equalsIgnoreCase(whatScreen)){
                    BloodSugarActivity.feelDataSugar = (feelList.get(position));
                }

                previousPosition = position;
                notifyDataSetChanged();

            }
        });

        if(previousPosition == position) {
            holder.tvFeel.setBackgroundResource(R.drawable.blue_circle_back);
            holder.tvFeel.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.tvFeel.setBackgroundResource(R.drawable.gray_outline_back);
            holder.tvFeel.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return feelList.size();
    }

    public class AdapterHowYouFeelViewHolder extends RecyclerView.ViewHolder{

        TextView tvFeel;

        public AdapterHowYouFeelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeel = itemView.findViewById(R.id.tvFeel);
        }

    }

}
