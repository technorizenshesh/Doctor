package com.technorizen.doctor.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.DoctorListActivity;
import com.technorizen.doctor.adapters.AdapterDoctorTalktime;
import com.technorizen.doctor.adapters.AdapterDoctors;
import com.technorizen.doctor.adapters.AdapterDoctorsList;
import com.technorizen.doctor.databinding.FragmentTalkDoctorBinding;
import com.technorizen.doctor.models.ModelDoctors;
import com.technorizen.doctor.models.ModelDoctorsList;
import com.technorizen.doctor.utils.FragmentCallback;
import com.technorizen.doctor.utils.ProjectUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TalkDoctorFragment extends Fragment implements AdapterDoctorTalktime.TalkDoctorCallback {

    Context mContext;
    FragmentTalkDoctorBinding binding;
    ArrayList<ModelDoctors.Result> doctorsList;
    ModelDoctorsList modelDoctorsList;
    AdapterDoctorTalktime adapterDoctors;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private Geocoder geocoder;
    LatLng currentLatLong;
    Dialog dialog;
    String latString,longString;
    boolean isDiabetes,isHypertension;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk_doctor, container, false);

        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), "AIzaSyBhAIStVSYCBtZXv7JaksD8F6kz3e9pYYw");
        }

        init();

        // Inflate the layout for this fragment
        return binding.getRoot();

    }

    private void init() {

        getAllDoctors();

        /* binding.ccGeneralDoctor.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorListActivity.class));
        }); */

        /* binding.ccPsychiatristDoctor.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorListActivity.class));
        });

        binding.ccTherapistDoctor.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DoctorListActivity.class));
        }); */

    }

    private void getAllDoctors() {
        ProjectUtil.showProgressDialog(mContext,true,"Please wait...");
        AndroidNetworking.get("http://mobileappdevelop.in/health/webservice/get_category")
                .build()
                .getAsObject(ModelDoctors.class, new ParsedRequestListener<ModelDoctors>() {
                    @Override
                    public void onResponse(ModelDoctors response) {
                        doctorsList = response.getResult();
                        ProjectUtil.pauseProgressDialog();

                        adapterDoctors = new AdapterDoctorTalktime(mContext,doctorsList,false,TalkDoctorFragment.this);
                        binding.rvDoctors.setLayoutManager(new LinearLayoutManager(mContext));
                        binding.rvDoctors.setAdapter(adapterDoctors);

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                    }

                });
    }

    @Override
    public void openDilaogDoctorCallback(String categoryId) {

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.talk_doctor_filter);

        TextView tvLocation = dialog.findViewById(R.id.tvLocation);
        TextView btSubmit = dialog.findViewById(R.id.btSubmit);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvLocation.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    getAllNearByDoctorsApi(categoryId,(currentLatLong.latitude)+"",(currentLatLong.longitude)+"");
                } else {
                    Toast.makeText(mContext, "Please select Location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG,Place.Field.ADDRESS);

                // Start the autocomplete intent
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(mContext);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }

        });

        dialog.show();

    }

    private void getAllNearByDoctorsApi(String categoryId,String lat,String lon) {

        ProjectUtil.showProgressDialog(mContext,true,"Please wait...");
        AndroidNetworking.post("http://mobileappdevelop.in/health/webservice/doctorList_by_category")
                .addBodyParameter("category_id",categoryId)
                .addBodyParameter("lat",lat)
                .addBodyParameter("lon",lon)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")) {

                                Log.e("responseresponse","response = " + response);

                                modelDoctorsList = new Gson().fromJson(response, ModelDoctorsList.class);
                                startActivity(new Intent(mContext,DoctorListActivity.class)
                                .putExtra("doctornearbylist",modelDoctorsList)
                                .putExtra("isnearby",true)
                                .putExtra("header","Near By Doctors")
                                );
                            } else {
                                ProjectUtil.pauseProgressDialog();
                                Toast.makeText(mContext, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtil.pauseProgressDialog();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                currentLatLong = place.getLatLng();
                try {

                    List<Address> addresses;
                    geocoder = new Geocoder(mContext, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String address2 = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();

                        Log.e("Address1: ", "" + address1);
                        Log.e("Address2: ", "" + address2);
                        Log.e("AddressCity: ", "" + city);
                        Log.e("AddressState: ", "" + state);
                        Log.e("AddressCountry: ", "" + country);
                        Log.e("AddressPostal: ", "" + postalCode);
                        Log.e("AddressLatitude: ", "" + place.getLatLng().latitude);
                        Log.e("AddressLongitude: ", "" + place.getLatLng().longitude);

                        TextView textView = dialog.findViewById(R.id.tvLocation);
                        textView.setText(place.getAddress());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //setMarker(latLng);
                }

                Log.i("placesss", "Place: " + place.getName() + ", " + place.getAddressComponents());
            }


        }

    }


}
