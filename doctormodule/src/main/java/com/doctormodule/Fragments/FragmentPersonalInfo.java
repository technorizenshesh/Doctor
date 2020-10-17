package com.doctormodule.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.doctormodule.Activities.DoctorHomeActivity;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.FirstActivity;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentAccountInfoBinding;
import com.doctormodule.databinding.FragmentPersonalInfoBinding;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPersonalInfo extends Fragment {
    private FragmentPersonalInfoBinding binding;
    private HashMap<String, String> param;
    private ArrayList<String>specializationName=new ArrayList<>();
    private ArrayList<String>specializationID=new ArrayList<>();
    private ArrayList<String>QualificationName=new ArrayList<>();
    private ArrayList<String>QualificationID=new ArrayList<>();
    private ArrayAdapter<String> specAdapter;
    private ArrayAdapter<String> qualiAdapter;

    public FragmentPersonalInfo() {
        // Required empty public constructor
    }
    public FragmentPersonalInfo setData(HashMap<String,String>param){
        this.param=param;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_personal_info, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        specAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,specializationName);
        qualiAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,QualificationName);
        binding.spSpecialization.setAdapter(specAdapter);
        binding.spColification.setAdapter(qualiAdapter);
        getSpecialization();
        getQualification();
        binding.btNext.setOnClickListener(v->{
            param.put("specialization",specializationID.get(binding.spSpecialization.getSelectedItemPosition()));
            param.put("qualification",QualificationID.get(binding.spColification.getSelectedItemPosition()));
            param.put("experiance",binding.spExp.getSelectedItem().toString());
            param.put("address",binding.etAddress.getText().toString());
            if (getActivity() instanceof FirstActivity) {
                ((FirstActivity) getActivity()).FragmentTransaction(new FragmentAddCertificate().setData(param));
            }else {
                ((DoctorHomeActivity) getActivity()).FragmentTransaction(new FragmentAddCertificate().setData(param));
            }
        });
    }
private void getSpecialization(){
        HashMap<String,String>param=new HashMap<>();
        param.put("","");
        ApiCallBuilder.build(getActivity()).setUrl(BaseClass.get().categoryList())
                .isShowProgressBar(true).setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray result=object.getJSONArray("result");
                    for (int i=0;i<result.length();i++){
                        JSONObject jsonObject=result.getJSONObject(i);
                        specializationID.add(jsonObject.getString("id"));
                        specializationName.add(jsonObject.getString("category_name"));
                    }
                    specAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
}
private void getQualification(){
        HashMap<String,String>param=new HashMap<>();
        param.put("","");
        ApiCallBuilder.build(getActivity()).setUrl(BaseClass.get().degreeList())
                .isShowProgressBar(true).setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray result=object.getJSONArray("result");
                    for (int i=0;i<result.length();i++){
                        JSONObject jsonObject=result.getJSONObject(i);
                        QualificationID.add(jsonObject.getString("id"));
                        QualificationName.add(jsonObject.getString("degree"));
                    }
                    qualiAdapter.notifyDataSetChanged();
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
}
    private void setData() {
        SessionManager manager=SessionManager.get(getActivity());
        if (manager.IsUserLogedIn()){
            binding.etAddress.setText(manager.getValue(SessionKey.address));
            binding.spExp.setSelection(Tools.get().getSelectedPos(getResources().getStringArray(R.array.experience),manager.getValue(SessionKey.experiance)));
            binding.spSpecialization.setSelection(Tools.get().getSelectedPos(specializationID,manager.getValue(SessionKey.specialization)));
            binding.spColification.setSelection(Tools.get().getSelectedPos(QualificationID,manager.getValue(SessionKey.qualification)));
        }
    }
}
