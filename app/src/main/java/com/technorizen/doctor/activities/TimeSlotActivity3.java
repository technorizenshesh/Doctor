package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityTimeSlot3Binding;

public class TimeSlotActivity3 extends AppCompatActivity {

    Context mContext;
    ActivityTimeSlot3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil. setContentView(this,R.layout.activity_time_slot3);
        mContext = TimeSlotActivity3.this;

        init();

    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btBook.setOnClickListener(v -> {
            startActivity(new Intent(mContext,AppointmentActivity.class));
        });

    }


}
