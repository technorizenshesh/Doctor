package com.technorizen.doctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.technorizen.doctor.R;
import com.technorizen.doctor.models.ModelBanner;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ModelBanner>banners;

    public ViewPagerAdapter(Context context, ArrayList<ModelBanner> banners) {
        this.context = context;
        this.banners = banners;
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_viewpager_layout, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView tv_title =  view.findViewById(R.id.tv_title);
        TextView tv_descrip =  view.findViewById(R.id.tv_descrip);
        Picasso.get().load(banners.get(position).getImage()).placeholder(R.drawable.logo).into(imageView);
        tv_descrip.setText(banners.get(position).getDescription());
        tv_title.setText(banners.get(position).getTitle());
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }


}
