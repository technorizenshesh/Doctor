package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityLoginBinding;
import com.technorizen.doctor.models.ModelLogin;
import com.technorizen.doctor.utils.ProjectUtil;
import com.technorizen.doctor.utils.SharedPref;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Context mContext;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        mContext = LoginActivity.this;
        init();

    }

    private void init() {

        binding.btLogin.setOnClickListener(v -> {
            if(!binding.email.getText().toString().trim().isEmpty()){
                if(!binding.password.getText().toString().trim().isEmpty()){
                    loginApi();
                }else{
                    Toast.makeText(mContext, "Please enter password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(mContext, "Please enter name", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void loginApi(){
        ProjectUtil.showProgressDialog(mContext,false,"Please wait...");
        AndroidNetworking.post(BaseClass.get().Login())
                .addBodyParameter("email",binding.email.getText().toString())
                .addBodyParameter("password",binding.password.getText().toString())
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtil.pauseProgressDialog();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            SessionManager session=SessionManager.get(mContext);
                            if(jsonObject.getString("status").equals("1")){
                                session.CreateSession(jsonObject.getString("result"));
                                session.setUserType("User");
                                startActivity(new Intent(mContext,HomeActivity.class));
                                finish();
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
                    }

                });
    }


}
