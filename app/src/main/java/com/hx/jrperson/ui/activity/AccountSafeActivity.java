package com.hx.jrperson.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hx.jrperson.R;
import com.hx.jrperson.views.baseView.BaseActivity;

/**
 * 账户与安全界面
 * by ge
 * **/
public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout alterPswRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        showToolBar("账户与安全", true, this, false);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void initView() {
        alterPswRL = (RelativeLayout) findViewById(R.id.alterPswRL);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {
        alterPswRL.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alterPswRL://修改密码
                Intent intent = new Intent(this, NewPwdActivity.class);
                intent.putExtra("newPwd", "1");
                startActivity(intent);
                break;
        }
    }

}
