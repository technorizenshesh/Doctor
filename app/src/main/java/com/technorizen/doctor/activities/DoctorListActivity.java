package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterDoctorTalktime;
import com.technorizen.doctor.adapters.AdapterDoctorsList;
import com.technorizen.doctor.databinding.ActivityDoctorListBinding;
import com.technorizen.doctor.models.ModelDoctors;
import com.technorizen.doctor.models.ModelDoctorsList;
import com.technorizen.doctor.utils.ProjectUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {

    Context mContext;
    ActivityDoctorListBinding binding;
    String header = "" ,id = "";
    ModelDoctorsList doctorsList;
    boolean isnearby,isDoctor,isService;
    ModelDoctorsList modelNearByDoctorsList;
    AdapterDoctorsList adapterDoctorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor_list);
        mContext = DoctorListActivity.this;

        header = getIntent().getStringExtra("header");
        id = getIntent().getStringExtra("id");
        isnearby = getIntent().getBooleanExtra("isnearby",false);
        isDoctor = getIntent().getBooleanExtra("isDoctor",false);
        isService = getIntent().getBooleanExtra("isService",false);

        if(isnearby) {
            modelNearByDoctorsList = (ModelDoctorsList) getIntent().getSerializableExtra("doctornearbylist");
        }

        init();

    }

    private void init() {

        binding.headerText.setText(header);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        if(isnearby) {
            adapterDoctorsList = new AdapterDoctorsList(mContext,modelNearByDoctorsList.getResult());
            binding.rvDoctorsList.setLayoutManager(new LinearLayoutManager(mContext));
            binding.rvDoctorsList.setAdapter(adapterDoctorsList);
        } else if(isDoctor){
            getAllDoctors();
        } else if(isService) {
            getAllServices();
        }

        /* binding.llClinicDetails.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorClenicDetailActivity.class));
        });

        binding.contactClinic.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorClenicDetailActivity.class));
        });

        binding.contactClinic2.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorClenicDetailActivity.class));
        });

        binding.sponsored.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorClenicDetailActivity.class));
        });

        binding.sponsored2.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorClenicDetailActivity.class));
        });

        binding.llClinicDetails2.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorClenicDetailActivity.class));
        }); */

    }

    private void getAllDoctors() {

        Log.e("sdfdsfds","category_id = " + id);

        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.post("http://mobileappdevelop.in/health/webservice/doctorList_by_category")
                .addBodyParameter("category_id",id)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")){
                                doctorsList = new Gson().fromJson(response,ModelDoctorsList.class);

                                ProjectUtil.pauseProgressDialog();

                                adapterDoctorsList = new AdapterDoctorsList(mContext,doctorsList.getResult());
                                binding.rvDoctorsList.setLayoutManager(new LinearLayoutManager(mContext));
                                binding.rvDoctorsList.setAdapter(adapterDoctorsList);

                            } else {
                                ProjectUtil.pauseProgressDialog();
                                Toast.makeText(mContext, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                    }
                });

    }

    private void getAllServices() {

        Log.e("sdfdsfds","service_id = " + id);

        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.post("http://mobileappdevelop.in/health/webservice/doctorList_by_service")
                .addBodyParameter("service_id",id)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.e("sdfdsfds","response = " + response);

                            if(jsonObject.getString("status").equals("1")){
                                doctorsList = new Gson().fromJson(response,ModelDoctorsList.class);

                                ProjectUtil.pauseProgressDialog();

                                adapterDoctorsList = new AdapterDoctorsList(mContext,doctorsList.getResult());
                                binding.rvDoctorsList.setLayoutManager(new LinearLayoutManager(mContext));
                                binding.rvDoctorsList.setAdapter(adapterDoctorsList);

                            } else {
                                ProjectUtil.pauseProgressDialog();
                                Toast.makeText(mContext, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                    }
                });

    }

}
