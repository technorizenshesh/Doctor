package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityPsychiatristBinding;

public class PsychiatristActivity extends AppCompatActivity {

    Context mContext;
    ActivityPsychiatristBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=PsychiatristActivity.this;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_psychiatrist);

        init();
    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

    }


}
