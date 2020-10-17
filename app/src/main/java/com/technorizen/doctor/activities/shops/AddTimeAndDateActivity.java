package com.technorizen.doctor.activities.shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityAddTimeAndDateBinding;

public class AddTimeAndDateActivity extends AppCompatActivity {

    Context mContext;
    ActivityAddTimeAndDateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_time_and_date);
        mContext = AddTimeAndDateActivity.this;

        init();

    }

    private void init() {

        binding.btnDone.setOnClickListener(v -> {
            startActivity(new Intent(mContext,SetDeliveryLocationActivity.class));
        });

    }


}
