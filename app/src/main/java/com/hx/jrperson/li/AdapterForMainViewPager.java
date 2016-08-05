package com.hx.jrperson.li;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/5.
 */
public class AdapterForMainViewPager extends PagerAdapter {
    private ArrayList imageViewList;


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public void setImageViewList(ArrayList imageViewList) {
        this.imageViewList = imageViewList;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//取出指定位置的图片ImageView
        ImageView imageView = (ImageView) imageViewList.get(position % imageViewList.size());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);



        try{
            container.addView(imageView);
        }catch (IllegalStateException e){
            container.removeView(imageView);
            container.addView(imageView);
        }



        return imageView;
    }

    //销毁指定位置的ImageView回收内存
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container.getChildAt(position%imageViewList.size()) == object){
           // container.removeViewAt(position % imageViewList.size());
        }
    }
}
