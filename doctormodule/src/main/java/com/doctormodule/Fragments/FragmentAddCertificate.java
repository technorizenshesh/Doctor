package com.doctormodule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentAdCertificateBinding;
import com.doctormodule.databinding.FragmentPersonalInfoBinding;
import com.filepicker.FilePicker;
import com.filepicker.ModelFiles;
import com.filepicker.onFilePickerListener;
import com.squareup.picasso.Picasso;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddCertificate extends Fragment {
    private FragmentAdCertificateBinding binding;
    private HashMap<String, String> param;

    public FragmentAddCertificate() {
        // Required empty public constructor
    }
    public FragmentAddCertificate setData(HashMap<String,String>param){
        this.param=param;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ad_certificate, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        binding.image.setOnClickListener(v->{
            FilePicker.get().Callback(new onFilePickerListener() {
                @Override
                public void Success(ModelFiles files) {
                    binding.image.setImageBitmap(files.getBitmap());
                    if (getActivity() instanceof FirstActivity) {
                        ((FirstActivity) getActivity()).Certificate = files.getBitmap();
                    }else {
                        ((DoctorHomeActivity) getActivity()).Certificate = files.getBitmap();
                    }
                }
                @Override
                public void Error(String error) {
                    Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                }
            }).show(getChildFragmentManager(),"");
        });
        binding.btNext.setOnClickListener(v->{
            if (getActivity() instanceof FirstActivity) {
                ((FirstActivity) getActivity()).FragmentTransaction(new FragmentAddProfile().setData(param));
            }else {
                ((DoctorHomeActivity) getActivity()).FragmentTransaction(new FragmentAddProfile().setData(param));
            }
        });
        setData();
    }
    private void setData() {
        SessionManager manager=SessionManager.get(getActivity());
        if (manager.IsUserLogedIn()){
            if (!manager.getValue(SessionKey.certificate).equals(""))
            Picasso.get().load(manager.getValue(SessionKey.certificate)).into(binding.image);
        }
    }
}
