package com.technorizen.doctor.activities.shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.HomeActivity;
import com.technorizen.doctor.activities.MPESAExpressActivity;
import com.technorizen.doctor.databinding.ActivitySetDeliveryLocationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class SetDeliveryLocationActivity extends AppCompatActivity {

    Context mContext;
    ActivitySetDeliveryLocationBinding binding;
    private HashMap<String, String> param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_set_delivery_location);
        mContext = SetDeliveryLocationActivity.this;
        init();
        param=(HashMap<String,String>)getIntent().getExtras().getSerializable("data");
    }

    private void init() {
        binding.Done.setOnClickListener(v -> {
            if (binding.etAddress.getText().toString().isEmpty()){
                binding.etAddress.setError("Required");
                binding.etAddress.requestFocus();
                return;
            }
            param.put("address",binding.etAddress.getText().toString());
           startActivity(new Intent(this, MPESAExpressActivity.class).putExtra("data",param));
        });
    }


}
