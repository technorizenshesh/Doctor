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
import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.Dailog.ReviewDialog;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityDoctorBookingBinding;
import com.technorizen.doctor.models.ModelClinic;
import com.technorizen.doctor.models.ModelDoctorsList;
import com.technorizen.doctor.utils.ProjectUtil;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class DoctorBookingActivity extends AppCompatActivity implements OnMapReadyCallback {

    Context mContext;
    ActivityDoctorBookingBinding binding;
    GoogleMap googleMap;
    String doctor_id = "";
    String docAddress = "";
    ModelClinic modelClinic;
    SupportMapFragment mapFragment;
    LatLng cliniclatLng;
    ModelDoctorsList.Result data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor_booking);
        mContext = DoctorBookingActivity.this;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        doctor_id = getIntent().getStringExtra("category_id");
        data = (ModelDoctorsList.Result) getIntent().getSerializableExtra("doctor_detail");

        init();

    }

    private void init() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btBook.setOnClickListener(v -> {
            startActivity(new Intent(mContext,TimeSlotActivity2.class)
                    .putExtra("doctor_detail",data)
            );
        });

            if(!data.getImage().endsWith("/")){
                Picasso.get().load(data.getImage()).into(binding.doctorProfileImage);
            }

        binding.tvName.setText(data.getUser_name());
        binding.tvQualification.setText(data.getQualification());
        binding.tvExperience.setText(data.getYear_of_experience() + " years of experience");
        binding.tvOpenClosingTime.setText(data.getTime());
        binding.tvLocation.setText(data.getAddress());

        try {
            cliniclatLng = new LatLng(Double.parseDouble(data.getLat()),Double.parseDouble(data.getLon()));
        } catch (Exception e) {

        }

        mapFragment.getMapAsync(DoctorBookingActivity.this);
        getClinicDetial();
        binding.tvFeedback.setOnClickListener(v-> ReviewDialog.get(mContext).callback(new ReviewDialog.onCallback() {
            @Override
            public void onSuccess(float rating, String comment) {
                HashMap<String,String> param=new HashMap<>();
                param.put("user_id", SessionManager.get(mContext).getUserID());
                param.put("clinic_id",data.getId());
                param.put("rating",""+rating);
                param.put("comment",""+comment);
                ApiCallBuilder.build(DoctorBookingActivity.this).setParam(param).isShowProgressBar(true)
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
        AndroidNetworking.post(BaseClass.get().clinicListByDoctor())
                .addBodyParameter("doctor_id" , doctor_id)
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

                               /* binding.tvCliniName.setText(modelClinic.getResult().get(0).getName());
                                binding.tvlocation.setText(modelClinic.getResult().get(0).getAddress());
                                binding.tvTime.setText(modelClinic.getResult().get(0).getOpen_time() + "   "
                                        + modelClinic.getResult().get(0).getClose_time());*/

                                mapFragment.getMapAsync(DoctorBookingActivity.this);

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

        try {
            googleMap.addMarker(new MarkerOptions().position(cliniclatLng)
                    .title(data.getAddress()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cliniclatLng));
            CameraUpdate location1 = CameraUpdateFactory.newLatLngZoom(
                    cliniclatLng, 15);
            googleMap.animateCamera(location1);
        }catch (Exception e){

        }


    }


}
