package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityTimeSlot2Binding;
import com.technorizen.doctor.models.ModelDoctorsList;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class TimeSlotActivity2 extends AppCompatActivity {
    Context mContext;
    ActivityTimeSlot2Binding binding;
    ModelDoctorsList.Result data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_time_slot2);
        mContext = TimeSlotActivity2.this;
        data = (ModelDoctorsList.Result) getIntent().getSerializableExtra("doctor_detail");
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        if(!data.getImage().endsWith("/")) {
            Picasso.get().load(data.getImage()).into(binding.doctorsProfile);
        }
        binding.tvName.setText(data.getUser_name());
        binding.tvQualification.setText(data.getQualification());
        binding.btBook.setOnClickListener(v -> {
            BookAppointment();
        });
        binding.tvDate.setOnClickListener(v-> Tools.DatePicker(this,binding.tvDate::setText));
        binding.tvTime.setOnClickListener(v-> Tools.TimePicker(this,binding.tvTime::setText,true,true));

    }

    private void BookAppointment() {
        HashMap<String,String>param=new HashMap<>();
        param.put("user_id", SessionManager.get(this).getUserID());
        param.put("doctor_id",data.getId());
        param.put("date",binding.tvDate.getText().toString());
        param.put("time",binding.tvTime.getText().toString());
        ApiCallBuilder.build(this).setUrl(BaseClass.get().bookAppointment())
                .isShowProgressBar(true)
                .setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    Toast.makeText(mContext, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (status){
                        startActivity(new Intent(mContext,AppointmentActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
    }


}
