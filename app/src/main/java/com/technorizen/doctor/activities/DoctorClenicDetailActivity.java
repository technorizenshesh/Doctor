package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.Dailog.ReviewDialog;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityDoctorDetailBinding;
import com.technorizen.doctor.models.ModelClinic;
import com.technorizen.doctor.utils.ProjectUtil;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class DoctorClenicDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    Context mContext;
    ActivityDoctorDetailBinding binding;
    GoogleMap googleMap;
    String doctor_id = "";
    String docAddress = "";
    ModelClinic modelClinic;
    SupportMapFragment mapFragment;
    LatLng cliniclatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil. setContentView(this,R.layout.activity_doctor_detail);
        mContext = DoctorClenicDetailActivity.this;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        doctor_id = getIntent().getStringExtra("category_id");

        init();

    }

    private void init() {

        getClinicDetial();

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btBook.setOnClickListener(v -> {
            startActivity(new Intent(mContext,DoctorBookingActivity.class));
        });
        binding.tvFeedback.setOnClickListener(v->ReviewDialog.get(mContext).callback(new ReviewDialog.onCallback() {
            @Override
            public void onSuccess(float rating, String comment) {
                HashMap<String,String>param=new HashMap<>();
                param.put("user_id", SessionManager.get(mContext).getUserID());
                param.put("clinic_id",modelClinic.getResult().get(0).getId());
                param.put("rating",""+rating);
                param.put("comment",""+comment);
                ApiCallBuilder.build(DoctorClenicDetailActivity.this).setParam(param).isShowProgressBar(true)
                        .setUrl(BaseClass.get().addReview()).execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Toast.makeText(mContext, "Feedback submit successfully", Toast.LENGTH_SHORT).show();
                        binding.tvFeedback.setVisibility(View.GONE);
                    }
                    @Override
                    public void Failed(String error) {

                    }
                });
            }
        }).show());

    }

    private void getClinicDetial() {

        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.post("http://mobileappdevelop.in/health/webservice/clinicList_by_doctor")
                .addBodyParameter("doctor_id",doctor_id)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtil.pauseProgressDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.e("responseslgkjfdgs","response = "+response);

                            if(jsonObject.getString("status").equals("1")){

                                modelClinic = new Gson().fromJson(response,ModelClinic.class);

                                cliniclatLng = new LatLng(Double.parseDouble(modelClinic.getResult().get(0).getLat())
                                        , Double.parseDouble(modelClinic.getResult().get(0).getLon()));

                                docAddress = modelClinic.getResult().get(0).getAddress();

                                binding.tvCliniName.setText(modelClinic.getResult().get(0).getName());
                                binding.tvlocation.setText(modelClinic.getResult().get(0).getAddress());
                                binding.tvTime.setText(modelClinic.getResult().get(0).getOpen_time() + "   "
                                        + modelClinic.getResult().get(0).getClose_time());

                                mapFragment.getMapAsync(DoctorClenicDetailActivity.this);

                            } else {
                               // Toast.makeText(mContext, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        googleMap.addMarker(new MarkerOptions().position(cliniclatLng)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cliniclatLng));
        CameraUpdate location1 = CameraUpdateFactory.newLatLngZoom(
                cliniclatLng, 6);
        googleMap.animateCamera(location1);

    }


}
