package com.doctormodule.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Constant.BaseClass;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentMobileBinding;
import com.doctormodule.databinding.FragmentVerifyBinding;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class FragmentMobile extends Fragment {
    private FragmentMobileBinding binding;
    public FragmentMobile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mobile, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        binding.btSubmit.setOnClickListener(v->{
            if (binding.etMobile.getText().toString().isEmpty()){
                binding.etMobile.setError("Mobile Number Required");
                binding.etMobile.requestFocus();
                return;
            }
            ((FirstActivity)getActivity()).mobile=binding.etMobile.getText().toString();
            SendOTP();
        });
    }
    private void SendOTP() {
        HashMap<String,String> param=new HashMap<>();
        param.put("mobile",binding.etMobile.getText().toString());
        ApiCallBuilder.build(getActivity())
                .isShowProgressBar(true)
                .setParam(param)
                .setUrl(BaseClass.get().sendOTP())
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("response","===>"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status=jsonObject.getString("status").contains("1");
                            if (status){
                                SessionManager.get(getActivity()).CreateSession(jsonObject.getString("result"),false);
                                ((FirstActivity)getActivity()).FragmentTransaction(new FragmentVerify().setMobile(binding.etMobile.getText().toString()));
                            }else {
                                Toast.makeText(getActivity(), "" + jsonObject.getJSONObject("result"), Toast.LENGTH_SHORT).show();
                            }  } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void Failed(String error) {

                    }

                });
    }
}
