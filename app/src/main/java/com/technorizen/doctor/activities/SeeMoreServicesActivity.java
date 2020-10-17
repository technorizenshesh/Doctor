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
import com.technorizen.doctor.adapters.AdapterServices;
import com.technorizen.doctor.databinding.ActivitySeeMoreServicesBinding;
import com.technorizen.doctor.models.ModelServices;
import com.technorizen.doctor.utils.ProjectUtil;

import java.util.ArrayList;

public class SeeMoreServicesActivity extends AppCompatActivity {

    Context mContext;
    ActivitySeeMoreServicesBinding binding;
    AdapterServices adapterServices;
    ArrayList<ModelServices.Result> serviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_see_more_services);
        mContext = SeeMoreServicesActivity.this;

        init();

    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        getAllServices();

       /* binding.llServices1.setOnClickListener(v -> {
            startActivity(new Intent(mContext,DoctorListActivity.class));
        });

        binding.llServices2.setOnClickListener(v -> {
            startActivity(new Intent(mContext,DoctorListActivity.class));
        });*/

    }

    private void getAllServices() {

        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.get("http://mobileappdevelop.in/health/webservice/get_services")
                .build()
                .getAsObject(ModelServices.class, new ParsedRequestListener<ModelServices>() {
                    @Override
                    public void onResponse(ModelServices response) {
                        ProjectUtil.pauseProgressDialog();
                        serviceList = response.getResult();

                        adapterServices = new AdapterServices(mContext,serviceList,false);
                        binding.rvServices.setLayoutManager(new GridLayoutManager(mContext,3));
                        binding.rvServices.setAdapter(adapterServices);

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                    }
                });


    }


}
