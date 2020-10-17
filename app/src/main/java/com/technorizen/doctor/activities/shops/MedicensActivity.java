package com.technorizen.doctor.activities.shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.Interfaces.onClickProductListener;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterMedicines;
import com.technorizen.doctor.databinding.ActivityMedicensBinding;
import com.technorizen.doctor.models.ModelProduct;
import com.technorizen.doctor.models.ModelShop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class MedicensActivity extends AppCompatActivity implements onClickProductListener {

    Context mContext;
    ActivityMedicensBinding binding;
    AdapterMedicines adapterMedicines;
    ArrayList<ModelProduct> medicinedLsit=new ArrayList<>();
    private ModelShop data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_medicens);
        mContext = MedicensActivity.this;
        data=(ModelShop)getIntent().getExtras().getSerializable("data");
        init();
    }

    private void init() {
        adapterMedicines = new AdapterMedicines(mContext,medicinedLsit).callBack(this);
        binding.rvMedicines.setAdapter(adapterMedicines);
        binding.name.setText(data.getName());
        binding.address.setText(data.getAddress());
        Picasso.get().load(data.getImage()).placeholder(R.drawable.medical_store1).into(binding.image);
        getProduct();
        binding.swipeRefresh.setOnRefreshListener(this::getProduct);
    }
    private void getProduct(){
        binding.swipeRefresh.setRefreshing(true);
        HashMap<String,String> param=new HashMap<>();
        param.put("shop_id",data.getId());
        ApiCallBuilder.build(this)
                .setUrl(BaseClass.get().getProduct()).setParam(param)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        binding.swipeRefresh.setRefreshing(false);
                        try {
                            JSONObject object=new JSONObject(response);
                            boolean status=object.getString("status").contains("1");
                            if (status){
                                medicinedLsit.clear();
                                JSONArray array=object.getJSONArray("result");
                                for (int i=0;i<array.length();i++){
                                    JSONObject jsonObject=array.getJSONObject(i);
                                    ModelProduct shop=new ModelProduct();
                                    shop.setId(jsonObject.getString("id"));
                                    shop.setName(jsonObject.getString("name"));
                                    shop.setDescription(jsonObject.getString("description"));
                                    shop.setImage(jsonObject.getString("image"));
                                    shop.setPrice(jsonObject.getString("price"));
                                    shop.setQuantity(jsonObject.getString("quantity"));
                                    medicinedLsit.add(shop);
                                }
                                adapterMedicines.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failed(String error) {
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onClickProduct(ModelProduct product) {
        startActivity(new Intent(mContext, MedicinesDetailActivity.class).putExtra("data",product));
    }
}
