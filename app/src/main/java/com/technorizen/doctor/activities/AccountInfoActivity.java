package com.technorizen.doctor.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.databinding.ActivityAccountInfoBinding;
import com.utils.Session.SessionKey;
import com.utils.Session.SessionManager;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import www.develpoeramit.mapicall.ApiCallBuilder;

public class AccountInfoActivity extends AppCompatActivity {
    Context mContext;
    ActivityAccountInfoBinding binding;
    String path=null;
    File file = null;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account_info);
        mContext= AccountInfoActivity.this;
         session = SessionManager.get(mContext);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!session.CheckSession().equals("Not Login")){
            getProfile();
        }
    }

    private void init() {
        binding.profileImg.setOnClickListener(v -> {
            requestPermission();
        });

        binding.btNext.setOnClickListener(v -> {
            if(!binding.name.getText().toString().trim().isEmpty()){
                if(!binding.email.getText().toString().trim().isEmpty()){
                    if(!binding.password.getText().toString().trim().isEmpty()){
                        if(!binding.age.getText().toString().trim().isEmpty()){
                            if(!binding.height.getText().toString().trim().isEmpty()){
                                if(!binding.weight.getText().toString().trim().isEmpty()){
                                    if(path != null) {
                                        updateApiCall();
                                    } else {
                                        Toast.makeText(mContext, "Please add Image", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, "Please enter weight", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Please enter height", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Please enter age", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please enter password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Please enter name", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void requestPermission() {

        Dexter.withActivity((Activity) mContext)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // showPictureDialog();
                            final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
                            dialog.setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {
                                    dialog.dismiss();
                                }
                            }).setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    if (r.getError() == null) {

                                        path = r.getPath();
                                        file = new File(path);

                                        Log.e("filefile","file.getName() = " + file.getName());

                                        Picasso.get().load(new File(path)).into(binding.profileImg);

                                    } else {
                                        // Handle possible errors
                                        // TODO: do what you have to do with r.getError();
                                        Toast.makeText(mContext, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }

                            }).show(AccountInfoActivity.this);

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            openSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(mContext, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    private void openSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void updateApiCall() {
        HashMap<String,String> params = new HashMap();
        params.put("user_id",SessionManager.get(mContext).getUserID());
        params.put("user_name",binding.name.getText().toString());
        params.put("email",binding.email.getText().toString());
        params.put("password",binding.password.getText().toString());
        params.put("age",binding.age.getText().toString());
        params.put("height",binding.height.getText().toString());
        params.put("weight",binding.weight.getText().toString());
        params.put("gender",binding.spGender.getSelectedItem().toString());
        ApiCallBuilder.build(this).setUrl(BaseClass.get().signUp()).setParam(params)
                .isShowProgressBar(true).setFile("image",path)
                .execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    if (status){
                        SessionManager.get(mContext).CreateSession(object.getString("result"));
                        startActivity(new Intent(mContext,HomeActivity.class));
                        finish();
                    }else {
                        Toast.makeText(mContext, ""+object.getString("result"), Toast.LENGTH_SHORT).show();
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
    private void getProfile() {
        HashMap<String,String> params = new HashMap();
        params.put("user_id",session.getUserID());
        ApiCallBuilder.build(this).setUrl(BaseClass.get().getProfile())
                .setParam(params)
                .isShowProgressBar(true)
                .execute(new ApiCallBuilder.onResponse() {
            @Override
            public void Success(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.getString("status").contains("1");
                    if (status){
                        session.CreateSession(object.getString("result"));
                       SetData();
                    }else {
                        Toast.makeText(mContext, ""+object.getString("result"), Toast.LENGTH_SHORT).show();
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

    private void SetData() {
        binding.name.setText(session.getValue(SessionKey.user_name));
        binding.email.setText(session.getValue(SessionKey.email));
        binding.age.setText(session.getValue(SessionKey.age));
        binding.spGender.setSelection(session.getValue(SessionKey.gender).equals("Male")?0:1);
        binding.height.setText(session.getValue(SessionKey.height));
        binding.weight.setText(session.getValue(SessionKey.weight));
        Picasso.get().load(session.getValue(SessionKey.image)).placeholder(R.drawable.user_profile).into(binding.profileImg);
    }
}
