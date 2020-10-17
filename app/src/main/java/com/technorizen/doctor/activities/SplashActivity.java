package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.technorizen.doctor.R;
import com.technorizen.doctor.utils.SharedPref;
import com.utils.Session.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    Context mContext;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = SplashActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        session=SessionManager.get(mContext);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("Session","===>"+session.CheckSession());
                if(session.CheckSession().equals("Not Login")){
                    startActivity(new Intent(SplashActivity.this,StartActivity.class));
                }else if(session.CheckSession().equals("User")){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                }else if(session.CheckSession().equals("Doctor")){
                    startActivity(new Intent(SplashActivity.this, DoctorHomeActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,StartActivity.class));
                }
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

}
