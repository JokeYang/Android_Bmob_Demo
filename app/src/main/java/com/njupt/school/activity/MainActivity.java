package com.njupt.school.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.njupt.R;
import com.njupt.school.Fragment.FindFragment;
import com.njupt.school.Fragment.HomeFragment;
import com.njupt.school.Fragment.MoreFragment;


/**
 * Created by YYT on 2015/12/3.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{
    private Fragment mFragmentHome;
    private Fragment mFragmentFind;
    private Fragment mFragmentMore;
    private LinearLayout mTabHome;
    private LinearLayout mTabFind;
    private LinearLayout mTabMore;

    private ListView mLvLeftMenu;
    private DrawerLayout mDrawerLayout;

    private ImageButton mImgHome;
    private ImageButton mImgFind;
    private ImageButton mImgMore;

    private TextView mTxtHome;
    private TextView mTxtFind;
    private TextView mTxtMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_navigationview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tl_custom);
        setSupportActionBar(toolbar);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        mTabHome.setOnClickListener(this);
        mTabFind.setOnClickListener(this);
        mTabMore.setOnClickListener(this);
    }

    private void initView() {

        mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
        mTabFind = (LinearLayout) findViewById(R.id.id_tab_find);
        mTabMore = (LinearLayout) findViewById(R.id.id_tab_more);

        mImgHome = (ImageButton) findViewById(R.id.id_img_home);
        mImgFind = (ImageButton) findViewById(R.id.id_img_find);
        mImgMore = (ImageButton) findViewById(R.id.id_img_more);

        mTxtHome = (TextView) findViewById(R.id.id_text_home);
        mTxtFind = (TextView) findViewById(R.id.id_text_find);
        mTxtMore = (TextView) findViewById(R.id.id_text_more);

        mLvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }
    @Override
    public void onClick(View v) {
        resetView();
        switch (v.getId())
        {
            case R.id.id_tab_home:
                setSelect(0);
                break;
            case R.id.id_tab_find:
                setSelect(1);
                break;
            case R.id.id_tab_more:
                setSelect(2);
                break;
            default:
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i)
        {
            case 0:
                if (mFragmentHome==null)
                {
                    mFragmentHome = new HomeFragment();
                    transaction.add(R.id.id_frame_content,mFragmentHome);
                }else {
                    transaction.show(mFragmentHome);
                }
                mImgHome.setImageResource(R.drawable.home_pressed_32);
                mTxtHome.setTextColor(this.getResources().getColor(R.color.custom_after));
                break;
            case 1:
                if (mFragmentFind==null)
                {
                    mFragmentFind = new FindFragment();
                    transaction.add(R.id.id_frame_content,mFragmentFind);
                }else {
                    transaction.show(mFragmentFind);
                }
                mImgFind.setImageResource(R.drawable.find_pressed_32);
                mTxtFind.setTextColor(this.getResources().getColor(R.color.custom_after));
                break;
            case 2:
                if (mFragmentMore==null)
                {
                    mFragmentMore = new MoreFragment();
                    transaction.add(R.id.id_frame_content,mFragmentMore);
                }else {
                    transaction.show(mFragmentMore);
                }
                mImgMore.setImageResource(R.drawable.more_pressed_32);
                mTxtMore.setTextColor(this.getResources().getColor(R.color.custom_after));
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mFragmentHome!=null)
        {
            transaction.hide(mFragmentHome);
        }
        if (mFragmentFind!=null)
        {
            transaction.hide(mFragmentHome);
        }if (mFragmentMore!=null)
        {
            transaction.hide(mFragmentHome);
        }
    }

    private void resetView() {
        mImgHome.setImageResource(R.drawable.home_normal_32);
        mImgFind.setImageResource(R.drawable.find_normal_32);
        mImgMore.setImageResource(R.drawable.more_normal_32);

        mTxtHome.setTextColor(this.getResources().getColor(R.color.custom_before));
        mTxtFind.setTextColor(this.getResources().getColor(R.color.custom_before));
        mTxtMore.setTextColor(this.getResources().getColor(R.color.custom_before));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id==R.id.id_account)
        {

        }else if (id==R.id.id_favourite)
        {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
