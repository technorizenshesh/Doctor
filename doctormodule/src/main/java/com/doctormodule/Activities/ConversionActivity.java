package com.doctormodule.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.doctormodule.Adapters.AdapterConversion;
import com.doctormodule.Applications.MyApplication;
import com.doctormodule.Constant.BaseClass;
import com.doctormodule.Models.ModelChat;
import com.doctormodule.Models.ModelConversion;
import com.doctormodule.Models.ModelRequest;
import com.doctormodule.R;
import com.doctormodule.databinding.ActivityConversionBinding;
import com.squareup.picasso.Picasso;
import com.utils.Session.SessionManager;
import com.utils.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class ConversionActivity extends AppCompatActivity {
    private ArrayList<ModelConversion> arrayList = new ArrayList<>();
    private AdapterConversion adapter;
    private ActivityConversionBinding binding;
    private SessionManager session;
    private String ID;
    private ModelChat data;
    private ModelRequest req_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_conversion);
        if (getIntent().getSerializableExtra("req_data")!=null){
            req_data=(ModelRequest)getIntent().getSerializableExtra("req_data");
            ID=req_data.getUser_id();
        }else {
            data=(ModelChat)getIntent().getSerializableExtra("data");
            ID=data.getId();
        }
        BindView();
        FetchChat();
    }

    private void BindView() {
        session = SessionManager.get(this);
        adapter = new AdapterConversion(this, arrayList);
        binding.list.setAdapter(adapter);
        if (data!=null) {
            binding.tvTitle.setText(data.getName());
            Picasso.get().load(data.getImage()).placeholder(R.drawable.default_user).into(binding.profile);
        }else {
            binding.tvTitle.setText(req_data.getUser_name());
            Picasso.get().load(req_data.getImage()).placeholder(R.drawable.default_user).into(binding.profile);
        }
        binding.swiperefress.setOnRefreshListener(this::FetchChat);
        MyApplication.get().update(this::FetchChat);
        binding.imgPost.setOnClickListener(this::PostChat);
        binding.imgVideoCall.setOnClickListener(v->{
            if (data!=null) {
                startActivity(new Intent(this, VideoChatViewActivity.class).putExtra("data", data));
            }else {
                startActivity(new Intent(this, VideoChatViewActivity.class).putExtra("req_data", req_data));

            }
        });

    }
    private void FetchChat() {
        HashMap<String, String> param=new HashMap<>();
        param.put("sender_id",ID);
        param.put("receiver_id",session.getUserID());
        ApiCallBuilder.build(this).setUrl(BaseClass.get().getChat()).setParam(param)
                .execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                Log.e("Response", "==========>" + response);
                binding.swiperefress.setRefreshing(false);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    arrayList.clear();
                    if (status.contains("1")) {
                        JSONArray array = object.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            JSONObject sender_detail = jsonObject.getJSONObject("sender_detail");
                            ModelConversion user = new ModelConversion();
                            user.setId(jsonObject.getString("id"));
                            user.setChat_message(jsonObject.getString("chat_message"));
                            user.setChat_image(jsonObject.getString("chat_image"));
                            user.setDate(jsonObject.getString("date"));
                            user.setStatus(jsonObject.getString("status"));
                            user.setUser_id(sender_detail.getString("id"));
                            user.setUser_name(sender_detail.getString("username"));
                            user.setSender_image(sender_detail.getString("sender_image"));
                            user.setMyMessage(sender_detail.getString("id").contains(session.getUserID()));
                            arrayList.add(user);
                        }
                        binding.list.setSelection(arrayList.size()-1);
                        adapter.notifyDataSetChanged();
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
    private void PostChat(View view) {
        Tools.HideKeyboard(this, view);
        if (!binding.etComments.getText().toString().isEmpty()) {
            binding.swiperefress.setRefreshing(true);
            ApiCallBuilder.build(this).setUrl(BaseClass.get().insertChat())
                    .setParam(setParam())
                    .execute(new ApiCallBuilder.onResponse() {
                        @Override
                        public void Success(String response) {
                            Log.e("Response", "===========>" + response);
                            binding.swiperefress.setRefreshing(false);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("result");
                                if (status.contains("successful")) {
                                    binding.etComments.setText("");
                                    FetchChat();
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
    }
    private HashMap<String, String> setParam() {
        HashMap<String, String> param = new HashMap<>();
        param.put("sender_id", session.getUserID());
        param.put("receiver_id", ID);
        param.put("chat_message", binding.etComments.getText().toString());
        return param;
    }
}
