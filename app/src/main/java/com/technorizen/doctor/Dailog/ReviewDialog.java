package com.technorizen.doctor.Dailog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.technorizen.doctor.R;
import com.utils.Utils.Tools;


public class ReviewDialog extends Dialog {
    private onCallback callback;

    public ReviewDialog(Context context) {
        super(context);
    }

    public static ReviewDialog get(Context context) {
        return new ReviewDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.get().updateResources(getContext());
        setContentView(R.layout.layout_review_dialog);
        TextView tv_ok = findViewById(R.id.tv_ok);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        RatingBar rating = findViewById(R.id.rating);
        EditText et_message = findViewById(R.id.et_message);
        tv_ok.setOnClickListener(v -> {
            if (!et_message.getText().toString().isEmpty()) {
                callback.onSuccess(rating.getRating(),et_message.getText().toString());
                dismiss();
            }else {
                et_message.setError("Required");
                et_message.requestFocus();
            }
        });
        tv_cancel.setOnClickListener(v->dismiss());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    }

    public ReviewDialog callback(onCallback callback) {
        this.callback = callback;
        return this;
    }
       public interface onCallback {
        void onSuccess(float rating,String comment);
    }
}
