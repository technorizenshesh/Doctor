package com.technorizen.doctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityAppointmentBinding;
import com.technorizen.doctor.fragments.BookedAppointmentFragment;
import com.technorizen.doctor.fragments.NewAppointmentFragment;

public class AppointmentActivity extends AppCompatActivity {

    Context mContext;
    ActivityAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_appointment);
        mContext = AppointmentActivity.this;
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new BookedAppointmentFragment()).commit();
//        init();
    }

    private void init() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NewAppointmentFragment()).commit();
        TabLayout.Tab newAppoint = binding.tabLayout.newTab();
        newAppoint.setText("New Appointment");
        binding.tabLayout.addTab(newAppoint);
        TabLayout.Tab bookedAppoint = binding.tabLayout.newTab();
        bookedAppoint.setText("Booked Appointment");
        binding.tabLayout.addTab(bookedAppoint);


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){

                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NewAppointmentFragment()).commit();
                        break;

                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new BookedAppointmentFragment()).commit();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }


}
