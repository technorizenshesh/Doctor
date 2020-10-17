package com.doctormodule.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.doctormodule.Constant.BaseClass;
import com.doctormodule.Dialogs.MessageDialog;
import com.doctormodule.FirstActivity;
import com.doctormodule.Fragments.FragmentAccountInfo;
import com.doctormodule.Fragments.FragmentChat;
import com.doctormodule.Fragments.FragmentHome;
import com.doctormodule.Fragments.FragmentRequest;
import com.doctormodule.Fragments.FragmentWebView;
import com.doctormodule.R;
import com.doctormodule.databinding.ActivityDoctorHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class DoctorHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private ActivityDoctorHomeBinding binding;
    private SessionManager session;
    public Bitmap Certificate,Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_doctor_home);
        init();
    }
    private void init() {
        session = SessionManager.get(this);
        Log.e("DoctorDetails","=======>"+session.getAllDetails());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        binding.navigationView.setNavigationItemSelectedListener(this);
        binding.ivMenu.setOnClickListener(v -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });
        bindView();
        if (!session.getValue(SessionKey.full_name).equals("")){
            FragmentTransaction(new FragmentAccountInfo());
        }else {
            FragmentTransaction(new FragmentHome());
        }
    }
    private void bindView() {
        if (!session.getValue(SessionKey.image).isEmpty()) {
            Picasso.get().load(session.getValue(SessionKey.image)).placeholder(R.drawable.user_profile).into(binding.profileImg);
        }
        binding.profileImg.setOnClickListener(v -> {
//            startActivity(new Intent(this, AccountInfoActivity.class));
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        if (id==R.id.nav_logout){
            MessageDialog.get(DoctorHomeActivity.this).setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setDialogType(MessageDialog.DialogType.Confirm)
                    .callback("Logout", new MessageDialog.onCallback() {
                        @Override
                        public void onSuccess() {
                            session.Logout();
                            startActivity(new Intent(DoctorHomeActivity.this, FirstActivity.class));
                            finish();
                        }
                    }).show();
        }else if (id==R.id.nav_about){
            new FragmentWebView().setData("About Us", BaseClass.get().AboutUs()).show(getSupportFragmentManager(), "");
        }else if (id==R.id.nav_privacy){
            new FragmentWebView().setData("Privacy & policy", BaseClass.get().PrivacyPolicy()).show(getSupportFragmentManager(), "");
        }else if (id==R.id.nav_terms_n_condition){
            new FragmentWebView().setData("Terms & Conditions", BaseClass.get().TermsCondition()).show(getSupportFragmentManager(), "");
        }else if (id==R.id.nav_chat){
            FragmentTransaction(new FragmentChat());
        }else if (id==R.id.nav_home){
            FragmentTransaction(new FragmentHome());
        }else if (id==R.id.nav_request){
            FragmentTransaction(new FragmentRequest("accept"));
        }else if (id==R.id.nav_app_history){
            FragmentTransaction(new FragmentRequest("all"));
        }else if (id==R.id.nav_profile){
            FragmentTransaction(new FragmentAccountInfo());
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
    public void FragmentTransaction(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    private void getProfile() {
        HashMap<String,String> params = new HashMap();
        params.put("doctor_id",session.getUserID());
        ApiCallBuilder.build(this).setUrl(BaseClass.get().getDoctorProfile())
                .setParam(params)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            boolean status=object.getString("status").contains("1");
                            if (status){
                                session.CreateSession(object.getString("result"));
                            }else {
                                Toast.makeText(DoctorHomeActivity.this, ""+object.getString("result"), Toast.LENGTH_SHORT).show();
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
