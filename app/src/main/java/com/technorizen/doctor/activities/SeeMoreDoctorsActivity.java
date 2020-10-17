package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterDoctors;
import com.technorizen.doctor.databinding.ActivitySeeMoreDoctorsBinding;
import com.technorizen.doctor.models.ModelDoctors;
import com.technorizen.doctor.utils.ProjectUtil;

import java.util.ArrayList;

public class SeeMoreDoctorsActivity extends AppCompatActivity {

    Context mContext;
    ActivitySeeMoreDoctorsBinding binding;
    AdapterDoctors adapterDoctors;
    ArrayList<ModelDoctors.Result> doctorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_see_more_doctors);
        mContext = SeeMoreDoctorsActivity.this;

        init();

    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        getAllDoctors();

        /* binding.llDoctors1.setOnClickListener(v -> {
            startActivity(new Intent(mContext,DoctorListActivity.class));
        });

        binding.llDoctors2.setOnClickListener(v -> {
            startActivity(new Intent(mContext,DoctorListActivity.class));
        }); */

    }

    private void getAllDoctors() {

        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.get("http://mobileappdevelop.in/health/webservice/get_category")
                .build()
                .getAsObject(ModelDoctors.class, new ParsedRequestListener<ModelDoctors>() {
                    @Override
                    public void onResponse(ModelDoctors response) {
                        doctorList = response.getResult();
                        ProjectUtil.pauseProgressDialog();

                        adapterDoctors = new AdapterDoctors(mContext,doctorList,false);
                        binding.rvDoctors.setLayoutManager(new GridLayoutManager(mContext,3));
                        binding.rvDoctors.setAdapter(adapterDoctors);

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                    }
                });


    }


}
