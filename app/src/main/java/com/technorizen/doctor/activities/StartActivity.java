package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.doctormodule.FirstActivity;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    Context mContext;
    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start);
        mContext = StartActivity.this;

        init();

    }

    private void init() {
        binding.llmobile.setOnClickListener(v -> {

            if(binding.cbCheckbox.isChecked()) {
                startActivity(new Intent(mContext,MobileNumberActivity.class));
            } else {
                Toast.makeText(mContext, "Please accept terms an condition", Toast.LENGTH_SHORT).show();
            }


        });


        binding.llLogin.setOnClickListener(v -> {
            startActivity(new Intent(mContext,LoginActivity.class));
        });
        binding.llDoctor.setOnClickListener(v->{
            startActivity(new Intent(this, FirstActivity.class));
        });
    }


}
