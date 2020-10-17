package com.technorizen.doctor.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.Interfaces.onClickShopListener;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.shops.MedicensActivity;
import com.technorizen.doctor.adapters.AdapterShops;
import com.technorizen.doctor.databinding.FragmentShopsListBinding;
import com.technorizen.doctor.models.ModelShop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopsListFragment extends Fragment implements onClickShopListener {

    Context mContext;
    FragmentShopsListBinding binding;
    ArrayList<ModelShop> shopsList=new ArrayList<>();
    AdapterShops adapterShops;

    public ShopsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shops_list, container, false);

        init();
        return binding.getRoot();
    }

    private void init() {
        adapterShops = new AdapterShops(mContext, shopsList).callBack(this);
        binding.rvShops.setAdapter(adapterShops);
        binding.swipeRefresh.setOnRefreshListener(this::getShop);
        getShop();
    }
    private void getShop(){
        binding.swipeRefresh.setRefreshing(true);
        HashMap<String,String>param=new HashMap<>();
        param.put("","");
        ApiCallBuilder.build(getActivity())
                .setUrl(BaseClass.get().getShop()).setParam(param)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        binding.swipeRefresh.setRefreshing(false);
                        try {
                            JSONObject object=new JSONObject(response);
                            boolean status=object.getString("status").contains("1");
                            shopsList.clear();
                            if (status){
                                JSONArray array=object.getJSONArray("result");
                                for (int i=0;i<array.length();i++){
                                    JSONObject jsonObject=array.getJSONObject(i);
                                    ModelShop shop=new ModelShop();
                                    shop.setId(jsonObject.getString("id"));
                                    shop.setName(jsonObject.getString("shop_name"));
                                    shop.setAddress(jsonObject.getString("address"));
                                    shop.setLat(jsonObject.getString("lat"));
                                    shop.setLon(jsonObject.getString("lng"));
                                    shop.setImage(jsonObject.getString("image"));
                                    shopsList.add(shop);
                                }
                                adapterShops.notifyDataSetChanged();
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
    public void onClickShop(ModelShop shop) {
        mContext.startActivity(new Intent(mContext, MedicensActivity.class).putExtra("data",shop));
    }
}
