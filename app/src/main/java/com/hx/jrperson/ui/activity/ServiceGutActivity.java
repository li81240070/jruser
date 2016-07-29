package com.hx.jrperson.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hx.jrperson.R;
import com.hx.jrperson.bean.entity.ServiceThreeEntity;
import com.hx.jrperson.bean.entity.ServicesParentEntity;
import com.hx.jrperson.consts.API;
import com.hx.jrperson.consts.Consts;
import com.hx.jrperson.controller.adapter.ServiceParentsAdapter;
import com.hx.jrperson.utils.JrUtils;
import com.hx.jrperson.utils.httpmanager.NetLoader;
import com.hx.jrperson.views.WaittingDiaolog;
import com.hx.jrperson.views.baseView.BaseActivity;
import com.hx.jrperson.views.widget.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击气泡跳转到本页面
 * 本页面是具体的维修项目列表页面
 * by ge
 **/
public class ServiceGutActivity extends BaseActivity implements ServiceParentsAdapter.OnClickServiceParentsLisener {
    private RecyclerView serviceGutLV;
    private GridLayoutManager manager;
    private ServiceParentsAdapter adapter;
    private String parentCode;
    private String mainCode;
    private WaittingDiaolog diaolog;
    private List<ServicesParentEntity.DataMapBean.ServicesBean> list = new ArrayList<>();
    public static ServiceGutActivity inance = null;
    private List<ServiceThreeEntity.DataMapBean.ServicesBean> serviceList = new ArrayList<>();
    private Handler handler;
    ////////////////////////////////////
    private ImageView backButtonForListview;
    private TextView textviewForListview;
    private String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inance = this;
        setContentView(R.layout.activity_service_gut);
        showTitle();
        initView();
        initData();
        setListener();
        ///////////////////////////////////////////////
        backButtonForListview= (ImageView) findViewById(R.id.backButtonForListview);

        backButtonForListview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceGutActivity.this.finish();
            }
        });
        ///////////////////////////////////////////
    }

    //进入页面设置
    private void showTitle() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            parentCode = intent.getStringExtra("parentCode");
            mainCode = intent.getStringExtra("parentCode");
            showToolBar(title, true, this, false);
        } else {

        }
    }

    @Override
    protected void initView() {
        serviceGutLV = (RecyclerView) findViewById(R.id.serviceGutLV);
        textviewForListview= (TextView) findViewById(R.id.textviewForListview);
    }

    @Override
    protected void initData() {
        handler = new Handler();
        diaolog = new WaittingDiaolog(ServiceGutActivity.this);
        diaolog.show();
        adapter = new ServiceParentsAdapter(ServiceGutActivity.this);
        serviceGutLV.setAdapter(adapter);
        manager = new GridLayoutManager(this, 1);
        serviceGutLV.setLayoutManager(manager);
        serviceGutLV.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        String url = API.SUBSERCIVE;
        Map<String, String> map = new HashMap<>();
        map.put(Consts.SUBSERVICES, parentCode);
        url = JrUtils.appendParams(url, map);
        NetLoader.getInstance(ServiceGutActivity.this).loadGetData(ServiceGutActivity.this, url, new NetLoader.NetResponseListener() {
            @Override
            public void success(String resultString, int code) {
                Log.i("geanwen找timeout:resultString", code + "");
                diaolog.dismiss();
                if (code != 401) {
                    if (code == 200){
                        Gson gson = new Gson();
                        ServicesParentEntity entity = gson.fromJson(resultString, ServicesParentEntity.class);
                        if (entity != null) {
                            list = entity.getDataMap().getServices();
                            adapter.addData(list);
                        }
                    }
                }else if (code == 401){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ServiceGutActivity.this, "此账号已在别处登录", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void fail(String failString, Exception e) {
                Log.i("geanwen找timeout:failString", e.toString());
                diaolog.dismiss();
            }
        });
        ////////////////////////////
        textviewForListview.setText(title);
        //////////////////////////////////////
    }


    @Override
    protected void setListener() {
        adapter.setOnClickServiceParentsLisener(this);
    }

    @Override
    public void OnClickServiceParents(View v, final int Position) {
        parentCode = list.get(Position).getSrv_code() + "";
        final WaittingDiaolog waittingDiaolog = new WaittingDiaolog(this);
        waittingDiaolog.show();
        String url = API.SUBSERCIVE;
        Map<String, String> map = new HashMap<>();
        map.put(Consts.SUBSERVICES, list.get(Position).getSrv_code() + "");
        url = JrUtils.appendParams(url, map);
        NetLoader.getInstance(this).loadGetData(ServiceGutActivity.this, url, new NetLoader.NetResponseListener() {
            @Override
            public void success(String resultString, int code) {
                waittingDiaolog.dismiss();
                if (code == 200) {
                    Gson gson = new Gson();
                    ServiceThreeEntity entity = gson.fromJson(resultString, ServiceThreeEntity.class);
                    serviceList = entity.getDataMap().getServices();
                    Intent intent = new Intent(ServiceGutActivity.this, IssueOrdorGutActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("serviceParent", list.get(Position));
                    bundle.putString("parent_code", parentCode);
                    bundle.putString("mainCode", mainCode);
                    bundle.putSerializable("serviceList", (Serializable) serviceList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if (code == 401){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ServiceGutActivity.this, "此账号已在别处登录", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void fail(String failString, Exception e) {
                waittingDiaolog.dismiss();
            }
        });

    }

}
