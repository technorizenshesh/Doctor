package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityChooseGenderBinding;

public class ChooseGenderActivity extends AppCompatActivity {

    Context mContext;
    ActivityChooseGenderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose_gender);
        mContext = ChooseGenderActivity.this;

        init();

    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnNext.setOnClickListener(v -> {
           startActivity(new Intent(mContext,BloodPressureActivity.class));
        });

    }

}
