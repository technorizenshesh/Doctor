package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityOtpverficationBinding;
import com.technorizen.doctor.models.ModelLogin;
import com.technorizen.doctor.utils.Api;
import com.technorizen.doctor.utils.ApiFactory;
import com.technorizen.doctor.utils.AppConstant;
import com.technorizen.doctor.utils.ProjectUtil;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerficationActivity extends AppCompatActivity {

    Context mContext;
    ActivityOtpverficationBinding binding;
    String mobile;
    private String otp;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otpverfication);
        mContext = OTPVerficationActivity.this;
        session= SessionManager.get(this);
        init();
    }

    private void init() {
        mobile=session.getValue(SessionKey.mobile);
        binding.tvTxt.setText("We have send you on SMS on "+mobile+"\nwith 4 digit verification code.\nThis is Dummy Otp Verification and Otp is 9999");
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.llResend.setOnClickListener(v -> {
           resendotp();
        });

        binding.et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    binding.et2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        binding.et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    binding.et3.requestFocus();
                }
            }
        });

        binding.et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    binding.et4.requestFocus();
                }
            }
        });


        binding.btSubmit.setOnClickListener(v -> {
            otp = "";
            if(!binding.et1.getText().toString().trim().isEmpty()) {
                if(!binding.et2.getText().toString().trim().isEmpty()) {
                    if(!binding.et3.getText().toString().trim().isEmpty()) {
                        if(!binding.et4.getText().toString().trim().isEmpty()) {
                            otp = binding.et1.getText().toString().trim() +
                                    binding.et2.getText().toString().trim() +
                                    binding.et3.getText().toString().trim() +
                                    binding.et4.getText().toString().trim() ;
                            checkotp();
                        } else {
                            Toast.makeText(mContext, "Please enter complete otp", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please enter complete otp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please enter complete otp", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mContext, "Please enter complete otp", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void checkotp() {
        ProjectUtil.showProgressDialog(mContext,false,"Plase wait...");
        AndroidNetworking.post(BaseClass.get().checkOtp())
                .addBodyParameter("mobile",mobile)
                .addBodyParameter("otp",otp.trim())
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtil.pauseProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equalsIgnoreCase("1")){
                                session.setUserType("User");
                                startActivity(new Intent(mContext,AccountInfoActivity.class));
                                finish();
                            } else {
                                Toast.makeText(mContext, "You entered wrong Otp", Toast.LENGTH_SHORT).show();
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

    private void resendotp() {

        ProjectUtil.showProgressDialog(mContext,false,"Plase wait...");
        AndroidNetworking.post(BaseClass.get().resendOtp())
                .addBodyParameter("mobile",mobile)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtil.pauseProgressDialog();

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(mContext, "Otp sent successfully", Toast.LENGTH_SHORT).show();

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
