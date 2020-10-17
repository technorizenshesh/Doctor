package com.doctormodule.Applications;

import android.app.Application;
import android.os.CountDownTimer;

import com.doctormodule.Interfaces.onRefreshSchedule;


public class MyApplication extends Application {
    private static MyApplication sInstance;
    private CountDownTimer downTimer;
    private onRefreshSchedule schedule;

    @Override
    public void onCreate() {
        super.onCreate();
        downTimer=new CountDownTimer(1000,50000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (schedule!=null) {
                    schedule.run();
                }
            }
            @Override
            public void onFinish() {
                downTimer.start();
            }
        };
        downTimer.start();
    }
    public MyApplication update(onRefreshSchedule schedule){
        this.schedule=schedule;
        return this;
    }

    public static MyApplication get() {
        if (sInstance == null) {
            synchronized (MyApplication.class) {
                if (sInstance == null) {
                    sInstance = new MyApplication();
                }
            }
        }

        return sInstance;
    }
}
