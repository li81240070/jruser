package com.hx.jrperson.codebylixishun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.hx.jrperson.R;
import com.hx.jrperson.bean.entity.GainMessageEntity;
import com.hx.jrperson.bean.entity.OrderEntity;
import com.hx.jrperson.bean.entity.PersonalInforEntity;
import com.hx.jrperson.codebylixishun.adapter.AdapterForHomePage;
import com.hx.jrperson.codebylixishun.bean.HomePageBean;
import com.hx.jrperson.consts.Consts;
import com.hx.jrperson.controller.JrController;
import com.hx.jrperson.ui.activity.InforGutActivity;
import com.hx.jrperson.ui.activity.LoginActivity;
import com.hx.jrperson.ui.activity.MainActivity;
import com.hx.jrperson.utils.JrUtils;
import com.hx.jrperson.utils.common.util.PreferencesUtils;
import com.hx.jrperson.views.WaittingDiaolog;
import com.hx.jrperson.views.baseView.BaseActivity;
import com.hx.jrperson.views.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/26.
 * The homepage information by lixishun
 * You can found the firstpage when you enter this app,and you will find all entrance in this page
 * such as decorate for my home,water maintence,electricity maintence and more.
 */
public class HomePage extends BaseActivity{
    private RecyclerView recyclerView;
    private AdapterForHomePage adapter;
    private ArrayList<HomePageBean> data;
    //old version variable
    //抽屉中各个行布局
    private LinearLayout item_personal_order, item_personal_liucheng,
            item_personal_biaozhun, item_personal_set, item_personal_about_us, item_out_login;
    private ImageView navifation_myIV, navifation_messageIV;//toolbar两侧按钮
    private ImageView rush_to_dealIV, start_ball_enterIV, takenPhoneIV;//抢险抢修, 小球进入动画按钮, 接单匠人信息打电话按钮
    private ImageView waterIv, eleIv, houseKeepingIv, homeTrimIv, safeIv, reMoveIv, setupIv, upgradleIv;//八个小球
    private boolean isStart = false;//判断小球是否进入屏幕
    private DrawerLayout mDrawerLayout;//抽屉
    private NavigationView mNavigationView;
    private CircleImageView head_imgIV, workerHeadIV;//头像, 接单匠人师傅的头像
    private TextView nick_nameTV, signatrueTV;//昵称, 签名
    private boolean isLogin = false;//是否登陆
    private MapView mapView;//地图
    private LocationClient mLocationClient;//定位相关
    private BaiduMap mbaiduMap;
    private MainActivity.MyLocationListener myLocationListener;
    private boolean isFristIn = true;//是否是第一次定位
    private double mLatitude, mLongtitude;//经纬度
    private BitmapDescriptor mMarker;//覆盖物相关
    private PersonalInforEntity entity;//个人信息
    private Toast toast;
    private int wid, hei;//屏幕宽高
    private RelativeLayout isGoneRl, personal_headRL, isNotOrdorRl;//显示3秒隐藏的布局 相关
    private Handler handler;
    private WaittingDiaolog outLoginDialog;//玩命加载中的dialog
    private ImageView backMainIV;//回到地图
    private boolean isShowing;//是否在当前页面
    private OrderEntity.DataMapBean dataMapBean;//订单被抢时弹出的dialog需要的数据
    private String myAddress = "";//定位后根据经纬度反检索自己的位置 存入缓存 发布订单页面和抢险抢修会用到
    private ImageButton backMyLocationIB;//回到我的位置
    private boolean fristLocation = true;
    public static HomePage insance = null;
    private boolean lookWorkerLocation = false;
    private LinearLayout head_negivityRL, firstPart_RL;//个人信息总布局
    private MainActivity.LocationReceiver receiver;//接收服务传过来的匠人位置
    private String city;//当前城市
    private boolean isStartService = true;//是否开启后台服务
    private int isBallClick = 1;//记录小球点击的次数 防止多次点击 项目列表页面会多次弹出
    private GainMessageEntity gainEntity;
    private String clickTimes;
    //////////////////////////////////////
    //my more advertising Imageview
    private ImageView moreInHomePage;
    private ImageView myHomePage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_li);
        moreInHomePage= (ImageView) findViewById(R.id.moreInHomePage);
        myHomePage= (ImageView) findViewById(R.id.myHomePage);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerInHomePage);
        adapter=new AdapterForHomePage(this);

        data=new ArrayList<>();
        data.add(new HomePageBean(R.mipmap.myhome,"我家装修"));
        data.add(new HomePageBean(R.mipmap.water,"水维修"));
        data.add(new HomePageBean(R.mipmap.electricity,"电维修"));
        data.add(new HomePageBean(R.mipmap.monitoring,"装修监控"));
        data.add(new HomePageBean(R.mipmap.maintenance,"居家小修"));
        data.add(new HomePageBean(R.mipmap.installation,"居家安装"));
        data.add(new HomePageBean(R.mipmap.van,"货车力工"));
        data.add(new HomePageBean(R.mipmap.appliance,"家电清洗"));
        data.add(new HomePageBean(R.mipmap.service,"联系客服"));
        adapter.setData(data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        moreInHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toolbar右侧信息按钮
                    PreferencesUtils.putString(HomePage.this, Consts.TIME, clickTimes);//存入点击的时间，以便下次传时间到服务器，获取未查看的广告信息
                    Intent intent = new Intent(HomePage.this, InforGutActivity.class);
                    startActivity(intent);
            }
        });
        myHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    mDrawerLayout.openDrawer(mNavigationView);//弹出抽屉
                } else {
                    closeDrawerLayout();
                }
            }
        });
    }
    //未登陆时跳转到登陆页面
    private void closeDrawerLayout() {
        Intent out_intent = new Intent(HomePage.this, LoginActivity.class);
        startActivityForResult(out_intent, 3);
        mDrawerLayout.closeDrawers();//关闭抽屉
    }
}
