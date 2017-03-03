package com.njupt.school.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.njupt.R;

/**
 * Created by YYT on 2015/12/14.
 */
public class HomeFragment extends Fragment{
    private MapView mapView;
    private View mapLayout;
    //初始化百度地图
    private BaiduMap mBaiduMap;

    //定位相关
    private LocationClient locationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        mapLayout = inflater.inflate(R.layout.custom_tab_home,container,false);
        initView();
        //初始化定位
        initLocation();
        return mapLayout;
    }

    private void initLocation() {
        locationClient = new LocationClient(getActivity());
        mLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(mLocationListener);
        //设置定位相关
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
    }

    private void initView() {
        mapView = (MapView) mapLayout.findViewById(R.id.mapView);
        mBaiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开启定位图层，开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted())
        locationClient.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        locationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    private class MyLocationListener implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation location) {
        // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
        // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            if (isFirstIn)
            {
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn=false;
            }
        }
    }
}
