package com.doctormodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.doctormodule.Fragments.FragmentMobile;
import com.doctormodule.databinding.ActivityFirstBinding;

import java.io.File;

public class FirstActivity extends AppCompatActivity {
    private ActivityFirstBinding binding;
    public Bitmap Certificate=null,Profile=null;
    public String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_first);
        bindView();
    }

    private void bindView() {
        binding.llmobile.setOnClickListener(v->FragmentTransaction(new FragmentMobile()));
    }

    public void FragmentTransaction(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
