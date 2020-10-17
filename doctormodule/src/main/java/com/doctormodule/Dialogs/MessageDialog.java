package com.doctormodule.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctormodule.R;
import com.utils.Utils.Tools;


public class MessageDialog extends Dialog {
    private String title = "", message = "";
    private onCallback callback;
    private DialogType dialogType= DialogType.Message;
    private String positive_ban;

    public MessageDialog(Context context) {
        super(context);
    }

    public static MessageDialog get(Context context) {
        return new MessageDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.get().updateResources(getContext());
        setContentView(R.layout.layout_message_dialog);
        TextView tv_ok = findViewById(R.id.tv_ok);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        switch (dialogType){
            case Confirm:
                tv_cancel.setVisibility(View.VISIBLE);
                if (positive_ban==null) {
                    tv_ok.setText(getContext().getResources().getString(R.string.Delete));
                }else {
                    tv_ok.setText(positive_ban);
                }
                break;
            case Message:
                tv_cancel.setVisibility(View.GONE);
                break;
        }
        tv_ok.setOnClickListener(v -> {
            if (callback != null) {
                callback.onSuccess();
            }
            dismiss();
        });
        tv_cancel.setOnClickListener(v->dismiss());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_message = findViewById(R.id.tv_message);

        tv_title.setText(title);
        tv_message.setText(Html.fromHtml(message));

    }

    public MessageDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MessageDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageDialog callback(onCallback callback) {
        this.callback = callback;
        return this;
    }
    public MessageDialog callback(DialogType dialogType,onCallback callback) {
        this.callback = callback;
        this.dialogType=dialogType;
        return this;
    }public MessageDialog callback(String positive_ban,onCallback callback) {
        this.callback = callback;
        this.positive_ban=positive_ban;
        return this;
    }
    public MessageDialog setDialogType(DialogType dialogType) {
        this.dialogType=dialogType;
        return this;
    }

    public enum DialogType{
        Message,Confirm
}
    public interface onCallback {
        void onSuccess();
    }
}
