package com.technorizen.doctor.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technorizen.doctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFilterFragment extends Fragment {

    public DoctorFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_filter, container, false);
    }


}
