package com.technorizen.doctor.activities.shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.adapters.AdapterMyCart;
import com.technorizen.doctor.databinding.ActivityMyCartBinding;
import com.technorizen.doctor.models.ModelProduct;
import com.utils.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class MyCartActivity extends AppCompatActivity {

    Context mContext;
    ActivityMyCartBinding binding;
    AdapterMyCart adapterMyCart;
    ArrayList<ModelProduct>products=new ArrayList<>();
    private JSONArray orders;
    private int total=0,GrantTotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_cart);
        mContext  =MyCartActivity.this;
        init();
    }

    private void init() {
        adapterMyCart = new AdapterMyCart(mContext,products);
        binding.rvCartitems.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvCartitems.setAdapter(adapterMyCart);
        binding.btnProceedtopay.setOnClickListener(v -> {
            HashMap<String,String>param=new HashMap<>();
            param.put("user_id",SessionManager.get(this).getUserID());
            param.put("delivery_fee","5");
            param.put("amount",""+total);
            param.put("grant_amount",""+GrantTotal);
            param.put("order_item",orders.toString());
            startActivity(new Intent(mContext,SetDeliveryLocationActivity.class).putExtra("data",param));
        });
        getCartItem();
    }

    private void getCartItem() {
        HashMap<String,String>param=new HashMap<>();
        param.put("user_id", SessionManager.get(this).getUserID());
        ApiCallBuilder.build(this).setUrl(BaseClass.get().cartList())
                .isShowProgressBar(true).setParam(param).execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    products.clear();
                    if (status){
                        orders=new JSONArray();
                        JSONArray array=object.getJSONArray("result");
                        total=object.getInt("total");
                        GrantTotal=total+5;
                        binding.itemsTotal.setText("$"+total);
                        binding.itemPlusDevCharges.setText("$"+GrantTotal);
                        for (int i=0;i<array.length();i++){
                            JSONObject jsonObject=array.getJSONObject(i);
                            JSONObject obj_order=new JSONObject();
                            obj_order.put("item_id",jsonObject.getString("product_id"));
                            obj_order.put("qty",jsonObject.getString("quantity"));
                            obj_order.put("price",jsonObject.getString("product_price"));
                            ModelProduct  product=new ModelProduct();
                            product.setId(jsonObject.getString("id"));
                            product.setName(jsonObject.getString("product_name"));
                            product.setQuantity(jsonObject.getString("quantity"));
                            product.setPrice(jsonObject.getString("product_price"));
                            product.setImage(jsonObject.getString("product_image"));
                            products.add(product);
                            orders.put(obj_order);
                        }
                        adapterMyCart.notifyDataSetChanged();
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
