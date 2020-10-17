package com.technorizen.doctor.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.FragmentNewAppointmentBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAppointmentFragment extends Fragment {
    Context mContext;
    FragmentNewAppointmentBinding binding;
    public NewAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_appointment, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {

    }


}
