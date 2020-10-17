package com.technorizen.doctor.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.technorizen.doctor.Constant.BaseClass;
import com.technorizen.doctor.R;
import com.technorizen.doctor.activities.SeeMoreDoctorsActivity;
import com.technorizen.doctor.activities.SeeMoreServicesActivity;
import com.technorizen.doctor.adapters.AdapterDoctors;
import com.technorizen.doctor.adapters.AdapterServices;
import com.technorizen.doctor.adapters.ViewPagerAdapter;
import com.technorizen.doctor.models.ModelBanner;
import com.technorizen.doctor.models.ModelDoctors;
import com.technorizen.doctor.models.ModelServices;
import com.technorizen.doctor.utils.ProjectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.develpoeramit.mapicall.ApiCallBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ViewPager viewPager;
    int dotscount;
    TextView tvSeeMoreServices,tvSeeMoreDoctors;
    ImageView[] dots;
    RecyclerView rvServices,rvDoctors;
    Context mContext;
    LinearLayout SliderDots;
    AdapterServices adapterServices;
    AdapterDoctors adapterDoctors;
    ArrayList<ModelDoctors.Result> doctorList = new ArrayList<>();
    ArrayList<ModelServices.Result> serviceList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<ModelBanner>banners=new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void getAllServices() {
            ProjectUtil.showProgressDialog(mContext,true,"Please wait...");
            AndroidNetworking.get(BaseClass.get().getServices())
                    .build()
                    .getAsObject(ModelServices.class, new ParsedRequestListener<ModelServices>() {
                        @Override
                        public void onResponse(ModelServices response) {
                            ProjectUtil.pauseProgressDialog();
                            serviceList = response.getResult();
                            tvSeeMoreServices.setText("See More (" + serviceList.size() + ")");
                            adapterServices = new AdapterServices(mContext,serviceList,true);
                            rvServices.setLayoutManager(new GridLayoutManager(mContext,3));
                            rvServices.setAdapter(adapterServices);

                        }

                        @Override
                        public void onError(ANError anError) {
                            ProjectUtil.pauseProgressDialog();
                        }

                    });

        }

    private void getAllDoctors() {
        AndroidNetworking.get(BaseClass.get().getCategory())
                .build()
                .getAsObject(ModelDoctors.class, new ParsedRequestListener<ModelDoctors>() {
                    @Override
                    public void onResponse(ModelDoctors response) {
                        doctorList = response.getResult();

                        tvSeeMoreDoctors.setText("See More (" + doctorList.size() + ")");

                        adapterDoctors = new AdapterDoctors(mContext,doctorList,true);
                        rvDoctors.setLayoutManager(new GridLayoutManager(mContext,3));
                        rvDoctors.setAdapter(adapterDoctors);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    private void getBanner() {
        ApiCallBuilder.build(getActivity())
                .setUrl(BaseClass.get().getBanner())
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            boolean status=object.getString("status").contains("1");
                            if (status){
                                banners.clear();
                                JSONArray array=object.getJSONArray("result");
                                for (int i=0;i<array.length();i++){
                                    JSONObject jsonObject=array.getJSONObject(i);
                                    ModelBanner banner=new ModelBanner();
                                    banner.setId(jsonObject.getString("id"));
                                    banner.setTitle(jsonObject.getString("title"));
                                    banner.setImage(jsonObject.getString("image"));
                                    banner.setDescription(jsonObject.getString("description"));
                                    banners.add(banner);
                                }
                                viewPagerAdapter.notifyDataSetChanged();
                                dotscount = viewPagerAdapter.getCount();
                                dots = new ImageView[dotscount];
                                for(int i = 0; i < dotscount; i++) {
                                    dots[i] = new ImageView(mContext);
                                    dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.non_active_dot));
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(8, 0, 8, 0);
                                    SliderDots.addView(dots[i], params);
                                }

                                dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

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

    private void init(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        SliderDots = view.findViewById(R.id.SliderDots);
        rvServices = view.findViewById(R.id.rvServices);
        rvDoctors = view.findViewById(R.id.rvDoctors);
        tvSeeMoreServices = view.findViewById(R.id.tvSeeMoreServices);
        tvSeeMoreDoctors = view.findViewById(R.id.tvSeeMoreDoctors);

        getAllServices();
        getAllDoctors();
        getBanner();

        tvSeeMoreServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SeeMoreServicesActivity.class));
            }
        });


        tvSeeMoreDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SeeMoreDoctorsActivity.class));
            }
        });

        viewPagerAdapter = new ViewPagerAdapter(mContext,banners);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }


}
