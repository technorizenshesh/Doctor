package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.technorizen.doctor.Dailog.BloodPresureReportDialog;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterHowYouFeel;
import com.technorizen.doctor.databinding.ActivityBloodPressureBinding;
import com.technorizen.doctor.models.ModelServices;
import com.utils.Utils.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BloodPressureActivity extends AppCompatActivity {
    Context mContext;
    ActivityBloodPressureBinding binding;
    ArrayList<String> feelList;
    ArrayList<String> bloodPressureData;
    public static String feelDataBloodPressure = null;
    private ModelServices.Result data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_blood_pressure);
        mContext = BloodPressureActivity.this;
        data=(ModelServices.Result)getIntent().getExtras().getSerializable("data");
        init();

    }

    private void init() {
        feelList = new ArrayList<>();
        feelList.add("happy");
        feelList.add("angry");
        feelList.add("sad");
        feelList.add("tired");
        feelList.add("exicted");
        feelList.add("stressed");
        feelList.add("relaxed");

        binding.rvHowFeel.setLayoutManager(new GridLayoutManager(mContext,3));
        AdapterHowYouFeel adapterHowYouFeel = new AdapterHowYouFeel(mContext,feelList,"Pressure");
        binding.rvHowFeel.setAdapter(adapterHowYouFeel);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btNext.setOnClickListener(v -> {
            if(!binding.etSystonic.getText().toString().trim().isEmpty()){
                if(!binding.etDiastolic.getText().toString().trim().isEmpty()){
                                Toast.makeText(mContext, "Your report submitted", Toast.LENGTH_SHORT).show();
                                int sys=Integer.parseInt(binding.etSystonic.getText().toString());
                                int dia=Integer.parseInt(binding.etDiastolic.getText().toString());
                                new BloodPresureReportDialog(this).setData(sys,dia).show();

                }else{
                    Toast.makeText(mContext, "Please fill all the field", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(mContext, "Please fill all the field", Toast.LENGTH_SHORT).show();
            }


        });

    }

}
