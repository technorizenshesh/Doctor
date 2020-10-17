package com.technorizen.doctor.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.FragmentNewAppointmentBinding;
import com.technorizen.doctor.databinding.FragmentNotificationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotification extends Fragment {
    Context mContext;
    FragmentNotificationBinding binding;
    public FragmentNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notification, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {

    }


}
