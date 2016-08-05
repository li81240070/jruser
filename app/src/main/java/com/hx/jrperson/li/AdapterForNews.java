package com.hx.jrperson.li;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.jrperson.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/5.
 */
public class AdapterForNews extends FragmentPagerAdapter {
    private ArrayList fragments;

    public void setFragments(ArrayList fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public AdapterForNews(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? (Fragment) fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
