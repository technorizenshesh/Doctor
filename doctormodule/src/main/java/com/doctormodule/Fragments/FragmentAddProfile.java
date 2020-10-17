package com.doctormodule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentAdCertificateBinding;
import com.doctormodule.databinding.FragmentAddProfileBinding;
import com.filepicker.FilePicker;
import com.filepicker.ModelFiles;
import com.filepicker.onFilePickerListener;
import com.squareup.picasso.Picasso;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddProfile extends Fragment {
    private FragmentAddProfileBinding binding;
    private HashMap<String, String> param;

    public FragmentAddProfile() {
        // Required empty public constructor
    }
    public FragmentAddProfile setData(HashMap<String,String>param){
        this.param=param;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_profile, container, false);
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
                    ((FirstActivity)getActivity()).Profile=files.getBitmap();
                }else {
                        ((DoctorHomeActivity)getActivity()).Profile=files.getBitmap();
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
                ((FirstActivity) getActivity()).FragmentTransaction(new FragmentTiming().setData(param));
            }else {
                ((DoctorHomeActivity) getActivity()).FragmentTransaction(new FragmentTiming().setData(param));
            }
        });
        setData();
    }
    private void setData() {
        SessionManager manager=SessionManager.get(getActivity());
        if (manager.IsUserLogedIn()){
            if (!manager.getValue(SessionKey.image).equals(""))
                Picasso.get().load(manager.getValue(SessionKey.image)).into(binding.image);
        }
    }
}
