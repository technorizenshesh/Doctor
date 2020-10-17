package com.doctormodule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentTimingBinding;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTiming extends Fragment {
    private FragmentTimingBinding binding;
    private HashMap<String, String> param;

    public FragmentTiming() {
        // Required empty public constructor
    }
    public FragmentTiming setData(HashMap<String,String>param){
        this.param=param;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_timing, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
      binding.tvOpningTime.setOnClickListener(v-> Tools.TimePicker(getContext(),binding.tvOpningTime::setText,false,false));
      binding.tvCloseTime.setOnClickListener(v-> Tools.TimePicker(getContext(),binding.tvCloseTime::setText,false,false));
        binding.btNext.setOnClickListener(v->{
            param.put("open_time",binding.tvOpningTime.getText().toString());
            param.put("close_time",binding.tvCloseTime.getText().toString());
            param.put("weekly_off",binding.spExp.getSelectedItem().toString());
            if (getActivity() instanceof FirstActivity) {
                ((FirstActivity) getActivity()).FragmentTransaction(new FragmentFees().setData(param));
            }else {
                ((DoctorHomeActivity) getActivity()).FragmentTransaction(new FragmentFees().setData(param));
            }
        });
        setData();
    }
    private void setData() {
        SessionManager manager=SessionManager.get(getActivity());
        if (manager.IsUserLogedIn()){
            binding.tvOpningTime.setText(manager.getValue(SessionKey.open_time));
            binding.tvCloseTime.setText(manager.getValue(SessionKey.close_time));
            binding.spExp.setSelection(Tools.get().getSelectedPos(getResources().getStringArray(R.array.weekly_off),manager.getValue(SessionKey.weekly_off)));
        }
    }
}
