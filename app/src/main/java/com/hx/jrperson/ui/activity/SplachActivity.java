package com.hx.jrperson.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hx.jrperson.R;
import com.hx.jrperson.codebylixishun.activity.HomePage;
import com.hx.jrperson.views.baseView.BaseActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 闪屏页面
 * by ge
 * **/
public class SplachActivity extends BaseActivity {

    private ImageView splachImg;
    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplachActivity.this, HomePage.class);
            startActivity(intent);
            SplachActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        splachImg = (ImageView) findViewById(R.id.splachImg);
        handler = new Handler();
        handler.postDelayed(runnable, 3000);
        splachImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplachActivity.this,MainActivity.class);
                startActivity(intent);
                handler.removeCallbacks(runnable);
                SplachActivity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

}
