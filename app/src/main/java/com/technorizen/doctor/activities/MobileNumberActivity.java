package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.MainActivity;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityMobileNumberBinding;
import com.technorizen.doctor.models.ModelLogin;
import com.technorizen.doctor.utils.ProjectUtil;
import com.technorizen.doctor.utils.SharedPref;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;


public class MobileNumberActivity extends AppCompatActivity {

    Context mContext;
    ActivityMobileNumberBinding binding;
    SharedPref sharedPref;
    ModelLogin modelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mobile_number);
        mContext = MobileNumberActivity.this;
        sharedPref = SharedPref.getInstance(mContext);
        init();
    }

    private void init() {

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.btSubmit.setOnClickListener(v -> {
            if(!binding.etMobile.getText().toString().trim().isEmpty()) {
                if(isValidMobile(binding.etMobile.getText().toString().trim())) {
                    sendOtpCall();
                } else {
                    Toast.makeText(mContext, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void sendOtpCall() {
        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.post(BaseClass.get().getSendOTP())
                .addBodyParameter("mobile",binding.etMobile.getText().toString())
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","response = "+response);
                        try {
                            ProjectUtil.pauseProgressDialog();
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("1")){
                                SessionManager.get(mContext).CreateSession(jsonObject.getString("result"),false);
                                startActivity(new Intent(mContext,OTPVerficationActivity.class));
                                finish();
                                Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(mContext, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                        Log.e("response","anError = "+anError.getErrorDetail());
                    }
                });

    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


}
