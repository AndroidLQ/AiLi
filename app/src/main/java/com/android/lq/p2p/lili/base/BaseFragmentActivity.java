package com.android.lq.p2p.lili.base;

/**
 * Created by a on 2016/12/23.
 */

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.listener.OnLoadData;
import com.android.lq.p2p.lili.util.Util;
import com.android.lq.p2p.lili.view.MyRadioGroup;

import java.util.List;

public abstract class BaseFragmentActivity extends FragmentActivity {

    /** statusbar背景颜色是否为轻颜色 */
    protected boolean statusBarLight = true;

    protected View statusbarView = null;

    protected RelativeLayout coverViewLayout = null;
    protected RelativeLayout headerViewLayout;

    private int current_tab = 0;
    private int lastTime_tab = 0;

    protected List<Fragment> views;

    private OnLoadData loadData = null;

    protected MyRadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        Util.setStatusBar(this, statusBarLight);
        rg = (MyRadioGroup) findViewById(R.id.main_bottom_vg);
        statusbarView = findViewById(R.id.baseStatusBarView);
        if (Build.VERSION.SDK_INT >= 21)
        {
            statusbarView.setVisibility(View.VISIBLE);
            int height = Util.getStatusBarHight(this) == 0 ? 50 : Util
                    .getStatusBarHight(this);
            statusbarView.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, height));
        }
        else
        {
            statusbarView.setVisibility(View.GONE);
        }

        coverViewLayout = (RelativeLayout) findViewById(R.id.night_cover);
        headerViewLayout = (RelativeLayout) findViewById(R.id.tab_headerView);
        stateCheckAndInitViews(savedInstanceState);

        rg.setOnCheckedChangeListener(checkL);
    }

    private void stateCheckAndInitViews(Bundle savedInstanceState)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fts = fm.beginTransaction();
        fts.setCustomAnimations(R.anim.main_fragment_anim,
                R.anim.main_fragment_anim_out);
        initViews();
        if (true/*savedInstanceState == null*/)
        {// 如果是第一次进入
            for (int i = 0; i < views.size(); i++)
            {
                fts.add(R.id.main_container, views.get(i), current_tab + "");
                if (i == current_tab)
                {
                    fts.show(views.get(i));
                }
                else
                {
                    fts.hide(views.get(i));
                }
            }
            fts.commit();
        }
        //        else
        //        {// 如果是因为资源回收而引起的重载
        //            Log.e("test", "2222222222222222222");
        //            for (int i = 0; i < getItemCount(); i++)
        //            {
        //                Fragment f = fm.findFragmentByTag(i + "");
        //                if (f != null)
        //                {
        //                    Log.e("test", "i="+i+",!=null");
        //                    if (i == current_tab)
        //                    {
        //                        fts.show(f);
        //                    }
        //                    else
        //                    {
        //                        fts.hide(f);
        //                    }
        //                }
        //                else
        //                {
        //                    Log.e("test", "i="+i+",==null");
        //
        //                }
        //            }
        //            fts.commit();
        //        }
    }

    protected void initViews()
    {
        views = getItems();
        if (views != null)
        {
            statrtLoadData(views.get(current_tab));
        }
    }

    public abstract List<Fragment> getItems();
    public abstract int getItemCount();

    public void setCurrent_tab(int index)
    {
        this.current_tab = index;
        switch (current_tab)
        {
            case 0:
                rg.check(R.id.main_bottom_rb_0);
                break;
            case 1:
                rg.check(R.id.main_bottom_rb_1);
                break;
            case 2:
                rg.check(R.id.main_bottom_rb_2);
                break;
            case 3:
                rg.check(R.id.main_bottom_rb_3);
                break;
            default:
                break;
        }
        if(views==null)
        {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(current_tab - lastTime_tab < 0){
            ft.setCustomAnimations(R.anim.back_left_in,
                    R.anim.back_right_out);
        }else {
            ft.setCustomAnimations(R.anim.back_right_in,
                    R.anim.back_left_out);
        }

        for (int i = 0; i < views.size(); i++)
        {
            if (i == current_tab)
            {
                ft.show(views.get(i));
            }
            else
            {
                ft.hide(views.get(i));
            }
        }

        ft.commit();
        views.get(current_tab).setUserVisibleHint(true);

        this.lastTime_tab = current_tab;
        statrtLoadData(views.get(current_tab));
    }


//    public void switchContent(Fragment from, Fragment to,String tag) {
//        if (mFragment != to) {
//            mFragment = to;
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            if (!to.isAdded()) { // 先判断是否被add过
//                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.back_left_in,
//                        R.anim.back_right_out);
//                transaction.hide(from).add(R.id.realtabcontentouter, to,tag).addToBackStack(null).commit(); // 隐藏当前的fragment，add下一个到Activity中
//            } else {
//                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.back_left_in,
//                        R.anim.back_right_out);
//                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//            }
//        }
//    }

    private RadioGroup.OnCheckedChangeListener checkL = new RadioGroup.OnCheckedChangeListener()
    {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            rg.setOnCheckedChangeListener(null);
            switch (checkedId)
            {
                case R.id.main_bottom_rb_0:
                    setCurrent_tab(0);
                    changeSearchHint();
                    break;
                case R.id.main_bottom_rb_1:
                    setCurrent_tab(1);
                    changeSearchHint();
                    break;
                case R.id.main_bottom_rb_2:
                    setCurrent_tab(2);
                    break;
                case R.id.main_bottom_rb_3:
                    setCurrent_tab(3);
                    break;
                default:
                    break;
            }
            changeSearchHint();
            rg.setOnCheckedChangeListener(checkL);
        }
    };

    /**
     * fragment开始加载数据
     * @param fragment
     */
    protected void statrtLoadData(Fragment fragment)
    {
        try
        {
            loadData = (OnLoadData) fragment;
            loadData.onLoadData();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(fragment.toString()
                    + " must implement loadData");
        }
    }

    protected void changeSearchHint()
    {
    }

}
