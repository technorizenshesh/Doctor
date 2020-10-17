package com.technorizen.doctor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.Dailog.MessageDialog;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.shops.MyCartActivity;
import com.technorizen.doctor.databinding.ActivityHomeBinding;
import com.technorizen.doctor.fragments.BookedAppointmentFragment;
import com.technorizen.doctor.fragments.FragmentChat;
import com.technorizen.doctor.fragments.FragmentHistory;
import com.technorizen.doctor.fragments.FragmentNotification;
import com.technorizen.doctor.fragments.FragmentWebView;
import com.technorizen.doctor.fragments.HomeFragment;
import com.technorizen.doctor.fragments.ShopsListFragment;
import com.technorizen.doctor.fragments.TalkDoctorFragment;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    Context mContext;
    ActivityHomeBinding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mContext = HomeActivity.this;
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        session = SessionManager.get(this);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        binding.navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        binding.ivMenu.setOnClickListener(v -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });
        bindView();
    }

    private void bindView() {
        Picasso.get().load(session.getValue(SessionKey.image)).placeholder(R.drawable.user_profile).into(binding.profileImg);
        binding.profileImg.setOnClickListener(v -> {
            startActivity(new Intent(this, AccountInfoActivity.class));
        });
        binding.ivNoti.setOnClickListener(v->{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentNotification()).commit();
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bottom_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.bottom_nav_talk_doctor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TalkDoctorFragment()).commit();
                break;
            case R.id.bottom_nav_shop:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShopsListFragment()).commit();
                break;
            case R.id.bottom_nav_account:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, AccountInfoActivity.class));
                break;
            case R.id.nav_home:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_app_history:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BookedAppointmentFragment()).commit();
                break;
            case R.id.nav_store_history:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_my_cart:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
                break;
            case R.id.nav_chat:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentChat()).commit();

                break;
            case R.id.nav_about:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                new FragmentWebView().setData("About Us", BaseClass.get().AboutUs()).show(getSupportFragmentManager(), "");
                break;
            case R.id.nav_privacy:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                new FragmentWebView().setData("Privacy & policy", BaseClass.get().PrivacyPolicy()).show(getSupportFragmentManager(), "");
                break;
            case R.id.nav_terms_n_condition:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                new FragmentWebView().setData("Turm & Conditions", BaseClass.get().TermsCondition()).show(getSupportFragmentManager(), "");
                break;
            case R.id.nav_logout:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                MessageDialog.get(HomeActivity.this).setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setDialogType(MessageDialog.DialogType.Confirm)
                        .callback("Logout", new MessageDialog.onCallback() {
                            @Override
                            public void onSuccess() {
                                session.Logout();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                finish();
                            }
                        }).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            MessageDialog.get(this).setTitle("Exit App").setDialogType(MessageDialog.DialogType.Confirm)
                    .setMessage("Are you want to exit App?").callback("Exit", new MessageDialog.onCallback() {
                @Override
                public void onSuccess() {
                    System.exit(0);
                }
            }).show();
        }

    }

}
