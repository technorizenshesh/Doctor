package com.technorizen.doctor.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doctormodule.Interfaces.onClickRequestListener;
import com.doctormodule.Models.ModelRequest;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.ConversionActivity;
import com.technorizen.doctor.adapters.AdapterAppointment;
import com.technorizen.doctor.databinding.FragmentBookedAppointmentBinding;
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
public class BookedAppointmentFragment extends Fragment implements onClickRequestListener {

    Context mContext;
    FragmentBookedAppointmentBinding binding;
    private SessionManager session;
    private AdapterAppointment adapter;
    private ArrayList<ModelRequest> arrayList=new ArrayList<>();
    public BookedAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_booked_appointment, container, false);
        init();

        return binding.getRoot();

    }

    private void init() {
        session = SessionManager.get(getContext());
        binding.swipeRefresh.setOnRefreshListener(this::getAppointment);
        adapter=new AdapterAppointment(getActivity(),arrayList).Callback(this);
        binding.list.setAdapter(adapter);
        getAppointment();
    }
    private void getAppointment() {
        binding.swipeRefresh.setRefreshing(true);
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", session.getUserID());
        ApiCallBuilder.build(getContext()).setUrl(BaseClass.get().getAppointment())
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
                                    JSONObject user_details=jsonObject.getJSONObject("doctor_details");
                                    ModelRequest request = new ModelRequest();
                                    request.setId(jsonObject.getString("id"));
                                    request.setUser_id(jsonObject.getString("user_id"));
                                    request.setTime(jsonObject.getString("time"));
                                    request.setDay(jsonObject.getString("date"));
                                    request.setStatus(jsonObject.getString("status"));
                                    request.setDate_time(jsonObject.getString("date_time"));
                                    request.setUser_name(user_details.getString("full_name"));
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
                        getAppointment();
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
        startActivity(new Intent(getActivity(), ConversionActivity.class).putExtra("req_data",request));
    }

    @Override
    public void onComplete(ModelRequest request) {

    }
}
