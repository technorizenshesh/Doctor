package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.technorizen.doctor.Dailog.BloodSugarReportDialog;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterHowYouFeel;
import com.technorizen.doctor.databinding.ActivityBloodSugarBinding;
import com.technorizen.doctor.models.ModelServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BloodSugarActivity extends AppCompatActivity {
    private static final String TAG = "BloodSugarActivity";
    Context mContext;
    ActivityBloodSugarBinding binding;
    ArrayList<String> accountdata;
    ArrayList<String> feelList;
    public static String feelDataSugar = "";
    private ModelServices.Result data;
    private int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE=1;
    private FitnessOptions fitnessOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blood_sugar);
        mContext = BloodSugarActivity.this;
        data = (ModelServices.Result) getIntent().getExtras().getSerializable("data");
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

        binding.rvHowFeelSugar.setLayoutManager(new GridLayoutManager(mContext, 3));
        AdapterHowYouFeel adapterHowYouFeel = new AdapterHowYouFeel(mContext, feelList, "Sugar");
        binding.rvHowFeelSugar.setAdapter(adapterHowYouFeel);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btNext.setOnClickListener(v -> {
            if (!binding.sugarReading.getText().toString().trim().isEmpty()) {
                Toast.makeText(mContext, "Your report submitted", Toast.LENGTH_SHORT).show();
                new BloodSugarReportDialog(this).show();
            } else {
                Toast.makeText(mContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }


        });
    }
    private void initGoogleFit(){
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();
        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);
            if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                        this, // your activity
                        GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                        account,
                        fitnessOptions);
            } else {
                accessGoogleFit();
            }
        }


    private void accessGoogleFit() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);
        long startTime = cal.getTimeInMillis();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build();

        GoogleSignInAccount account = GoogleSignIn
                .getAccountForExtension(this, fitnessOptions);

        Fitness.getHistoryClient(this, account)
                .readData(readRequest)
                .addOnSuccessListener(response -> {
                   System.out.println(response);
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "OnFailure()", e);
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit();
            }
    }
}
