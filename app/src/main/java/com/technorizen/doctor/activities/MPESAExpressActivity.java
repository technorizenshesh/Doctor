package com.technorizen.doctor.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityMpesaexpressBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.develpoeramit.mapicall.ApiCallBuilder;

public class MPESAExpressActivity extends AppCompatActivity {
    Daraja daraja;
    String phoneNumber;
    private ActivityMpesaexpressBinding binding;
    private HashMap<String, String> param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_mpesaexpress);
        param=(HashMap<String,String>)getIntent().getExtras().getSerializable("data");
        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("Uku3wUhDw9z0Otdk2hUAbGZck8ZGILyh", "JDjpQBm5HpYwk38b", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(MPESAExpressActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(MPESAExpressActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(MPESAExpressActivity.this.getClass().getSimpleName(), error);
            }
        });

        //TODO :: THIS IS A SIMPLE WAY TO DO ALL THINGS AT ONCE!!! DON'T DO THIS :)
        binding.sendButton.setOnClickListener(v -> {

            //Get Phone Number from User Input
            phoneNumber = binding.editTextPhoneNumber.getText().toString().trim();

            if (TextUtils.isEmpty(phoneNumber)) {
                binding.editTextPhoneNumber.setError("Please Provide a Phone Number");
                return;
            }

            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
            LNMExpress lnmExpress = new LNMExpress(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                    TransactionType.CustomerBuyGoodsOnline, // TransactionType.CustomerPayBillOnline  <- Apply any of these two
                    "10",
                    "254708374149",
                    "174379",
                    phoneNumber,
                    "http://mycallbackurl.com/checkout.php",
                    "001ABC",
                    "Goods Payment"
            );

            //This is the
            daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>() {
                        @Override
                        public void onResult(@NonNull LNMResult lnmResult) {
                            Log.i(MPESAExpressActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                            onPaymentDone();
                        }

                        @Override
                        public void onError(String error) {
                            binding.editTextPhoneNumber.setError("Invalid PhoneNumber");
                            binding.editTextPhoneNumber.requestFocus();
                            Log.e("MPESA ERROR", "=====>"+error);
                        }
                    }
            );
        });
    }
    void onPaymentDone(){
        ApiCallBuilder.build(this).setUrl(BaseClass.get().addPlaceorder())
                .setParam(param).isShowProgressBar(true)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("addPlaceorder","======>"+response);
                        try {
                            JSONObject object=new JSONObject(response);
                            boolean status =object.getString("status").contains("1");
                            if (status){
                                startActivity(new Intent(MPESAExpressActivity.this, HomeActivity.class));
                                finish();
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
