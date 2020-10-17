package com.doctormodule.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentVerifyBinding;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class FragmentVerify extends Fragment {
    private FragmentVerifyBinding binding;
    private String mobile;
    private String otp;
    private SessionManager session;
    public FragmentVerify() {
    }
    public FragmentVerify setMobile(String mobile){
        this.mobile=mobile;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_verify, container, false);
        bindView();
        return binding.getRoot();
    }

    private void bindView() {
        session= SessionManager.get(getContext());
        binding.tvCenterTxt.setText("We have send you on SMS on "+mobile+"\nwith 4 digit verification code.\nThis is Dummy Otp Verification and Otp is 9999");
        binding.ivBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        binding.llResend.setOnClickListener(v -> {
            resendOtp();
        });
        binding.et1.addTextChangedListener(textWatcher);
        binding.et2.addTextChangedListener(textWatcher);
        binding.et3.addTextChangedListener(textWatcher);
        binding.et4.addTextChangedListener(textWatcher);
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
                            VerifyOTP();
                        } else {
                            Toast.makeText(getContext(), "Please enter complete otp", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please enter complete otp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter complete otp", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter complete otp", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void VerifyOTP() {
        HashMap<String,String> param=new HashMap<>();
        param.put("mobile",mobile);
        param.put("otp",otp);
        ApiCallBuilder.build(getActivity())
                .isShowProgressBar(true)
                .setParam(param)
                .setUrl(BaseClass.get().CheckOtp())
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("response","======>"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status=jsonObject.getString("status").contains("1");
                            if (status){
                                session.setUserType("Doctor");
                                if (session.getValue(SessionKey.full_name).equals("")){
                                ((FirstActivity)getActivity()).FragmentTransaction(new FragmentAccountInfo());
                            }else {
                                    session.setIsLogin(true);
                                    startActivity(new Intent(getActivity(), DoctorHomeActivity.class));
                                    getActivity().finish();
                                }
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
    private void resendOtp() {
        HashMap<String,String> param=new HashMap<>();
        param.put("mobile",mobile);
        ApiCallBuilder.build(getActivity())
                .isShowProgressBar(true)
                .setParam(param)
                .setUrl(BaseClass.get().resendOtp())
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status=jsonObject.getString("status").contains("1");
                            String message=jsonObject.getString("message");
                            Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void Failed(String error) {

                    }
                });
    }

    TextWatcher textWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (binding.et1.getText().toString().length() == 1) {
                binding.et2.requestFocus();
            }if (binding.et2.getText().toString().length() == 1) {
                binding.et3.requestFocus();
            }if (binding.et3.getText().toString().length() == 1) {
                binding.et4.requestFocus();
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
