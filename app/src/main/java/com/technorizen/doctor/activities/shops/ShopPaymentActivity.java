package com.technorizen.doctor.activities.shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;

import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.HomeActivity;
import com.technorizen.doctor.databinding.ActivityShopPaymentBinding;

public class ShopPaymentActivity extends AppCompatActivity {

    Context mContext;
    ActivityShopPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shop_payment);
        mContext = ShopPaymentActivity.this;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        init();

    }

    private void init() {

        binding.cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(ShopPaymentActivity.this);

        binding.cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        binding.btMakePayment.setOnClickListener(v -> {
            startActivity(new Intent(mContext, HomeActivity.class));
            finish();
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

    }


}
