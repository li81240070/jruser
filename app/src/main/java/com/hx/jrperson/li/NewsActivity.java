package com.hx.jrperson.li;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.hx.jrperson.R;
import com.hx.jrperson.li.fragment.FourFragment;
import com.hx.jrperson.li.fragment.OneFragment;
import com.hx.jrperson.li.fragment.ThreeFragment;
import com.hx.jrperson.li.fragment.twofragment;
import com.hx.jrperson.ui.activity.MainActivity;
import com.hx.jrperson.views.baseView.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/5.
 */
public class NewsActivity extends BaseActivity {
    private ViewPager newsViewPager;
    private ArrayList data;
    private AdapterForNews adapter;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences getSps = getSharedPreferences("ok", MODE_PRIVATE);
        String name0 = getSps.getString("isfirst", "默认");
        if (!name0.equals("默认")){
            Intent intent = new Intent(NewsActivity.this,MainActivity.class);
            startActivity(intent);
            NewsActivity.this.finish();
        }








        setContentView(R.layout.newsviewpagerpage);
        //设置本地轮播图相关逻辑
        newsViewPager = (ViewPager) findViewById(R.id.newsViewPager);
        adapter=new AdapterForNews(getSupportFragmentManager());
        data = new ArrayList();
        data.add(new OneFragment());
        data.add(new twofragment());
        data.add(new ThreeFragment());
        data.add(new FourFragment());

        adapter.setFragments(data);
    newsViewPager.setAdapter(adapter);

    }
}