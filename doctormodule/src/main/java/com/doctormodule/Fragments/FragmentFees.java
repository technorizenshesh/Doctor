package com.doctormodule.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentFeesBinding;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFees extends Fragment {
    private FragmentFeesBinding binding;
    private HashMap<String, String> param;

    public FragmentFees() {
    }
    public FragmentFees setData(HashMap<String,String>param){
        this.param=param;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fees, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        SessionManager manager=SessionManager.get(getContext());
        Log.e("DoctorID","=====>"+manager.getUserID());
        binding.btNext.setOnClickListener(v->{
          param.put("hourly_fees",binding.etHourlyFees.getText().toString());
          param.put("clinic_fees",binding.etClinicFee.getText().toString());
          param.put("doctor_id", manager.getUserID());
          Continue();
        });
        setData();
    }
    private void setData() {
        SessionManager manager=SessionManager.get(getActivity());
        if (manager.IsUserLogedIn()){
            binding.etHourlyFees.setText(manager.getValue(SessionKey.hourly_fees));
            binding.etClinicFee.setText(manager.getValue(SessionKey.clinic_fees));
        }
    }
    private void Continue() {
        File Profile,certi;
        if (getActivity() instanceof FirstActivity){
            certi= Tools.get().getFile(getActivity(),((FirstActivity)getActivity()).Certificate);
            Profile= Tools.get().getFile(getActivity(),((FirstActivity)getActivity()).Profile);
        }else {
            certi= Tools.get().getFile(getActivity(),((DoctorHomeActivity)getActivity()).Certificate);
            Profile= Tools.get().getFile(getActivity(),((DoctorHomeActivity)getActivity()).Profile);
        }
        ApiCallBuilder.build(getActivity()).setUrl(BaseClass.get().docterSignup())
                .setParam(param)
                .setFile("certificate",certi)
                .setFile("image",Profile)
                .isShowProgressBar(true)
                .execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                Log.e("DoctorSignUp","=========>"+response);
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    if (status){
                        SessionManager.get(getActivity()).setIsLogin(true);
                        startActivity(new Intent(getActivity(), DoctorHomeActivity.class));
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(), ""+object.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
