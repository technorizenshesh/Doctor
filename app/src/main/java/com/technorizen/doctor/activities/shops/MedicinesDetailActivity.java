package com.technorizen.doctor.activities.shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityMedicinesDetailBinding;
import com.technorizen.doctor.models.ModelProduct;
import com.utils.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class MedicinesDetailActivity extends AppCompatActivity {

    Context mContext;
    ActivityMedicinesDetailBinding binding;
    private ModelProduct data;
    private int Count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_medicines_detail);
        mContext = MedicinesDetailActivity.this;
        data =(ModelProduct)getIntent().getExtras().getSerializable("data");
        init();

    }

    private void init() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.btMyCart.setOnClickListener(v -> {
            AddToCart();
        });
        Picasso.get().load(data.getImage()).placeholder(R.drawable.medicines1).into(binding.itemImage);
        binding.tvTitle.setText(data.getName());
        binding.tvName.setText(data.getName());
        binding.tvDescription.setText(data.getDescription());
        binding.tvPrice.setText("$"+data.getPrice());
        binding.tvPlus.setOnClickListener(v->{
            if (Count<Integer.parseInt(data.getQuantity())){
                Count++;
                binding.tvQuantity.setText(""+Count);
            }else {
                Toast.makeText(mContext, "Available Quantity "+data.getQuantity(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvMinus.setOnClickListener(v->{
            if (Count>0){
                Count--;
                binding.tvQuantity.setText(""+Count);
            }
        });
    }
private void AddToCart(){
    HashMap<String,String>param=new HashMap<>();
        param.put("user_id", SessionManager.get(this).getUserID());
        param.put("product_id", data.getId());
        param.put("quantity", ""+Count);
        param.put("remark", "");
    ApiCallBuilder.build(this).setUrl(BaseClass.get().addToCart())
            .isShowProgressBar(true)
            .setParam(param).execute(new ApiCallBuilder.onResponse() {
        @Override
        public void Success(String response) {
            Log.e("AddToCart","===========>"+response);
            try {
                JSONObject object=new JSONObject(response);
                boolean status=object.getString("result").contains("successfully");
                if (status){
                    startActivity(new Intent(mContext,MyCartActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void Failed(String error) {
            Toast.makeText(mContext, ""+error, Toast.LENGTH_SHORT).show();
        }
    });

}

}
