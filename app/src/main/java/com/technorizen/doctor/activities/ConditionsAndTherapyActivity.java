package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterHowYouFeel;
import com.technorizen.doctor.databinding.ActivityConditionsAndTherapyBinding;
import com.technorizen.doctor.models.ModelLogin;
import com.technorizen.doctor.models.ModelServices;
import com.technorizen.doctor.utils.AppConstant;
import com.technorizen.doctor.utils.ProjectUtil;
import com.technorizen.doctor.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ConditionsAndTherapyActivity extends AppCompatActivity {

    Context mContext;
    ActivityConditionsAndTherapyBinding binding;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    String diabetes = "";
    private ModelServices.Result data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_conditions_and_therapy);
        mContext = ConditionsAndTherapyActivity.this;

        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUserDetails(AppConstant.USER_DETAILS);
        data=(ModelServices.Result)getIntent().getExtras().getSerializable("data");

        init();

    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.cbDontHupertension.setOnClickListener(v -> {
            binding.cbHypertension.setChecked(false);
        });

        binding.cbDontDiabetes.setOnClickListener(v -> {
            binding.cbDibetes.setChecked(false);
        });

        binding.cbDontKnow.setOnClickListener(v -> {
            binding.cbDibetes.setChecked(false);
            binding.cbHypertension.setChecked(false);
        });

        binding.btNext.setOnClickListener(v -> {
            Toast.makeText(mContext, "Your report submitted", Toast.LENGTH_SHORT).show();
            finish();
          /*  mContext.startActivity(new Intent(mContext, DoctorListActivity.class)
                    .putExtra("header" , data.getName())
                    .putExtra("id" , data.getId())
                    .putExtra("isDoctor" , false)
                    .putExtra("isService" , true)
                    .putExtra("isnearby",false)
            );*/
        });

        if(binding.cbDibetes.isChecked()) {
            binding.llDiabetesTypesAndYears.setVisibility(View.VISIBLE);
        }

        binding.cbDibetes.setOnClickListener(v -> {
            if(binding.cbDibetes.isChecked()) {
                binding.llDiabetesTypesAndYears.setVisibility(View.VISIBLE);
            } else {
                binding.llDiabetesTypesAndYears.setVisibility(View.GONE);
            }
        });

    }

}
