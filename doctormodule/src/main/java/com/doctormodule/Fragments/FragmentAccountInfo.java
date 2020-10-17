package com.doctormodule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentAccountInfoBinding;
import com.doctormodule.databinding.FragmentMobileBinding;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAccountInfo extends Fragment {
    private FragmentAccountInfoBinding binding;

    public FragmentAccountInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account_info, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        binding.btNext.setOnClickListener(v->{
            HashMap<String,String>param=new HashMap<>();
            param.put("full_name",binding.etFullName.getText().toString());
            param.put("email",binding.etEmail.getText().toString());
            param.put("age",binding.spAge.getSelectedItem().toString());
            param.put("height",binding.spHeight.getSelectedItem().toString());
            if (getActivity() instanceof FirstActivity) {
                ((FirstActivity) getActivity()).FragmentTransaction(new FragmentPersonalInfo().setData(param));
            }else {
                ((DoctorHomeActivity) getActivity()).FragmentTransaction(new FragmentPersonalInfo().setData(param));
            }
        });
        setData();
    }

    private void setData() {
        SessionManager manager=SessionManager.get(getActivity());
        if (manager.IsUserLogedIn()){
            binding.etFullName.setText(manager.getValue(SessionKey.full_name));
            binding.etEmail.setText(manager.getValue(SessionKey.email));
            binding.spAge.setSelection(Tools.get().getSelectedPos(getResources().getStringArray(R.array.age),manager.getValue(SessionKey.age)));
            binding.spHeight.setSelection(Tools.get().getSelectedPos(getResources().getStringArray(R.array.height),manager.getValue(SessionKey.height)));
        }
    }


}
