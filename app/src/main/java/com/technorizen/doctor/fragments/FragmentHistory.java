package com.technorizen.doctor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.MyFragmentPagerAdapter;
import com.technorizen.doctor.databinding.FragmentHistoryBinding;
import com.technorizen.doctor.models.ModelFragmentPager;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistory extends Fragment {

    private FragmentHistoryBinding binding;
    private MyFragmentPagerAdapter adapter;

    public FragmentHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        List<ModelFragmentPager> layout=new ArrayList<>();

        layout.add(new ModelFragmentPager("New Appointment",new NewAppointmentFragment()));
        layout.add(new ModelFragmentPager("Booked Appointment",new BookedAppointmentFragment()));

        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),layout);
        binding.mViewPager.setAdapter(adapter);
        binding.tab.setupWithViewPager(binding.mViewPager);
    }

}
