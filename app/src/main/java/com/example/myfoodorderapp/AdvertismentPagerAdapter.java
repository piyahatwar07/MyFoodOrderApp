package com.example.myfoodorderapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdvertismentPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<AdvertismentModel> sliderImg;
    private ImageLoader imageLoader;

    Dialog mydialog;
    ImageView popimage;
    String s;
    public AdvertismentPagerAdapter(List sliderImg, Context context) {
        this.sliderImg = sliderImg;
        this.context = context;
    }
    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.advertisement_sliding, null);

        AdvertismentModel utils = sliderImg.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        imageLoader = LoadAdvertisment.getInstance(context).getImageLoader();
        imageLoader.get(utils.getSliderImageUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s =  sliderImg.get(position).getSliderImageUrl();
                advertisementpop();
            }
        });

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


    public void advertisementpop(){

        mydialog = new Dialog(context);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setContentView(R.layout.advertisement_popup);

        popimage = mydialog.findViewById(R.id.PopImage);

        Picasso
                .with(context)
                .load(s)
                // .placeholder(R.drawable.vegitarian) // can also be a drawable
                .fit() // will explain later
                .noFade()
                .into(popimage);

        popimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        mydialog.show();
    }

}
