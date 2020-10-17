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

import com.doctormodule.Activities.ConversionActivity;
import com.doctormodule.Adapters.AdapterRequest;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.Interfaces.onClickRequestListener;
import com.doctormodule.Models.ModelRequest;
import com.doctormodule.R;
import com.doctormodule.databinding.FragmentDoctorHomeBinding;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class FragmentRequest extends Fragment implements onClickRequestListener {
    private FragmentDoctorHomeBinding binding;
    private SessionManager session;
    private ArrayList<ModelRequest>arrayList=new ArrayList<>();
    private AdapterRequest adapter;
    private String status;

    public FragmentRequest(String status) {
        this.status=status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_doctor_home, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        session = SessionManager.get(getContext());
        getRequest();
        binding.swipeRefresh.setOnRefreshListener(this::getRequest);
        adapter=new AdapterRequest(getActivity(),arrayList).Callback(this);
        binding.recycle.setAdapter(adapter);
    }
    private void getRequest() {
        binding.swipeRefresh.setRefreshing(true);
        HashMap<String, String> param = new HashMap<>();
        param.put("doctor_id", session.getUserID());
        param.put("status", status);
        ApiCallBuilder.build(getContext()).setUrl(BaseClass.get().getAppointmentDoctor())
                .setParam(param)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("Response","====>"+response);
                        binding.swipeRefresh.setRefreshing(false);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            if (status.contains("1")) {
                                binding.tvNorecord.setVisibility(View.GONE);
                                arrayList.clear();
                                JSONArray result = object.getJSONArray("result");
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jsonObject = result.getJSONObject(i);
                                    JSONObject user_details=jsonObject.getJSONObject("user_details");
                                    ModelRequest request = new ModelRequest();
                                    request.setId(jsonObject.getString("id"));
                                    request.setUser_id(jsonObject.getString("user_id"));
                                    request.setTime(jsonObject.getString("time"));
                                    request.setDay(jsonObject.getString("date"));
                                    request.setStatus(jsonObject.getString("status"));
                                    request.setDate_time(jsonObject.getString("date_time"));
                                    request.setUser_name(user_details.getString("user_name"));
                                    request.setMobile(user_details.getString("mobile"));
                                    request.setImage(user_details.getString("image"));
                                    arrayList.add(request);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                binding.tvNorecord.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failed(String error) {

                    }
                });
    }

    @Override
    public void onAccept(ModelRequest request) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", request.getId());
        param.put("status","accept");
        ApiCallBuilder.build(getContext()).setUrl(BaseClass.get().updateAppointmentStatus())
                .isShowProgressBar(true)
                .setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    if (status){
                        getRequest();
                    }
                    Toast.makeText(getActivity(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
    }

    @Override
    public void onCancel(ModelRequest request) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", request.getId());
        param.put("status","cancel");
        ApiCallBuilder.build(getContext()).setUrl(BaseClass.get().updateAppointmentStatus())
                .isShowProgressBar(true)
                .setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    if (status){
                        getRequest();
                    }
                    Toast.makeText(getActivity(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
    }

    @Override
    public void onCall(ModelRequest request) {
        Tools.MakeCall(getContext(),request.getMobile());
    }
    @Override
    public void onChat(ModelRequest request) {
        startActivity(new Intent(getActivity(), ConversionActivity.class).putExtra("req_date",request));
    }

    @Override
    public void onComplete(ModelRequest request) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", request.getId());
        param.put("status","complete");
        ApiCallBuilder.build(getContext()).setUrl(BaseClass.get().updateAppointmentStatus())
                .isShowProgressBar(true)
                .setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    if (status){
                        getRequest();
                    }
                    Toast.makeText(getActivity(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
    }
}
