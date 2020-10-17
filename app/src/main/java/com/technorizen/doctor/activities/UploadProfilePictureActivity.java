package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityUploadProfilePictureBinding;

public class UploadProfilePictureActivity extends AppCompatActivity {

    Context mContext;
    ActivityUploadProfilePictureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_upload_profile_picture);
        mContext = UploadProfilePictureActivity.this;

        init();

    }

    private void init() {
        binding.btNext.setOnClickListener(v -> {
            startActivity(new Intent(mContext,ChooseGenderActivity.class));
        });
    }


}
