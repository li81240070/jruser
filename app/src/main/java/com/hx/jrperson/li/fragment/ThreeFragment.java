package com.hx.jrperson.li.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.jrperson.R;

/**
 * Created by Administrator on 2016/8/5.
 */
public class ThreeFragment extends android.support.v4.app.Fragment{
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.threefragment,null);


        return view;
    }
}
