package com.hx.jrperson.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hx.jrperson.R;
import com.hx.jrperson.controller.adapter.ChangeAddressAdapter;
import com.hx.jrperson.bean.entity.AddressEntity;
import com.hx.jrperson.views.baseView.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市界面
 * by ge
 * **/
public class ChangeCityActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView changeCityLV;
    private List<AddressEntity.DataMapBean.PostCodesBean.SubBean> cityList = new ArrayList<>();
    private ChangeAddressAdapter adapter;
    private String title, cityName, prioce;
    public static ChangeCityActivity intance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intance = this;
        setContentView(R.layout.activity_change_city);
        showTitle();
        initView();
        initData();
        setListener();
    }

    //根据点击的省 展示市级页面的标题
    private void showTitle() {
        Bundle bundle = this.getIntent().getExtras();
        cityList = (List<AddressEntity.DataMapBean.PostCodesBean.SubBean>) bundle.getSerializable("cityList");
        title = bundle.getString("proName");
        prioce = title;
        if (title != null){
            showToolBar(title, true, this, false);
        }
    }

    @Override
    protected void initView() {
        changeCityLV = (ListView) findViewById(R.id.changeCityLV);
    }

    @Override
    protected void initData() {
        adapter = new ChangeAddressAdapter(this);
        changeCityLV.setAdapter(adapter);
        adapter.addDataCity(cityList, 2);
        changeCityLV.setDividerHeight(0);
        changeCityLV.setDivider(null);
    }

    @Override
    protected void setListener() {
        changeCityLV.setOnItemClickListener(this);
    }

    //城市列表行布局点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<AddressEntity.DataMapBean.PostCodesBean.SubBean.SubTwoBean> alearEntities = cityList.get(position).getSub();
        title = title + cityList.get(position).getName();
        cityName = cityList.get(position).getName();
        Intent intent = new Intent(this, ChangeAlearActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("alearList", (Serializable) alearEntities);
        bundle.putString("alearName", title);
        bundle.putString("cityName", cityName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        title = prioce;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
