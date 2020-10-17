package com.technorizen.doctor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doctormodule.Applications.MyApplication;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.Interfaces.onClickChatListener;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.ConversionActivity;
import com.technorizen.doctor.adapters.AdapterChat;
import com.technorizen.doctor.databinding.FragmentChatBinding;
import com.technorizen.doctor.models.ModelChat;
import com.utils.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment implements onClickChatListener {
    private SessionManager session;
    private AdapterChat adapter;
    private ArrayList<ModelChat> arrayList=new ArrayList<>();
    private FragmentChatBinding binding;

    public FragmentChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_chat, container, false);
        findView();
        return binding.getRoot();
    }
    private void findView() {
        session = SessionManager.get(getContext());
        adapter = new AdapterChat(getActivity(), arrayList).setOnClick(this);
        binding.list.setAdapter(adapter);
        FetchConversion();
        binding.swiperefress.setRefreshing(true);
        MyApplication.get().update(this::FetchConversion);
    }

    private void FetchConversion() {
        HashMap<String, String> param = new HashMap<>();
        param.put("receiver_id", session.getUserID());
        ApiCallBuilder.build(getContext()).setUrl(BaseClass.get().getConversion()).setParam(param)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("Response","====>"+response);
                        binding.swiperefress.setRefreshing(false);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            if (status.contains("1")) {
                                binding.tvNorecord.setVisibility(View.GONE);
                                arrayList.clear();
                                JSONArray result = object.getJSONArray("result");
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jsonObject = result.getJSONObject(i);
                                    ModelChat chat = new ModelChat();
                                    chat.setId(jsonObject.getString("id"));
                                    chat.setName(jsonObject.getString("username"));
                                    chat.setImage(jsonObject.getString("image"));
                                    chat.setNo_of_message(jsonObject.getString("no_of_message"));
                                    chat.setLast_message(jsonObject.getString("last_message"));
                                    chat.setLast_image(jsonObject.getString("last_image"));
                                    chat.setTime_ago(jsonObject.getString("time_ago"));
                                    arrayList.add(chat);
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
    public void ViewChat(int pos) {
        startActivity(new Intent(getContext(), ConversionActivity.class).putExtra("data", arrayList.get(pos)));
    }

    @Override
    public void DeleteConversation(int pos) {

    }
}
