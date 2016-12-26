package com.android.lq.p2p.lili.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.BaseFragmentActivity;
import com.android.lq.p2p.lili.fragment.HomeFragment;
import com.android.lq.p2p.lili.fragment.InvestFragment;
import com.android.lq.p2p.lili.fragment.NewFragment;
import com.android.lq.p2p.lili.fragment.PersonalCenterFragment;
import com.android.lq.p2p.lili.listener.OnDoubleClickListener;
import com.android.lq.p2p.lili.util.Util;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseFragmentActivity implements OnDoubleClickListener{

    private static final String TAG = "MainNewActivity";

    /** 首页 */
    public HomeFragment homeFragment;

    /** 理财 */
    private InvestFragment investFragment;

    /** 资讯 */
    public NewFragment newFragment;

    /** 个人 */
    private PersonalCenterFragment personalCenterFragment;
    // private LenjoyFragment eventFragment;
    private View newMsgIcon;

    // 当前跳转的页面
    private int current_falg = 0;

    private int size = 0;

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity,HomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_falg = getIntent().getIntExtra("MAIN_FALG", 0);
        if (current_falg != 0)
        {
            setCurrent_tab(current_falg);
        }

        newMsgIcon = findViewById(R.id.main_new_message_icon);
        // 打开GPU加速
        if (Build.VERSION.SDK_INT > 11)
        {
            getWindow().setFlags(0x1000000, 0x1000000);
        }

//        addHeadView(mainTitleView);
        rg.setOnDoubleClickListener(this);
    }

    /**
     * 添加headerView
     *
     * @param view
     */
    protected void addHeadView(View view)
    {
        headerViewLayout.addView(view);
    }


    @Override
    public List<Fragment> getItems() {
        List<Fragment> list = new ArrayList<>();
        homeFragment = new HomeFragment();
        investFragment = new InvestFragment();
        newFragment = new NewFragment();
        personalCenterFragment = new PersonalCenterFragment();
        list.add(homeFragment);
        list.add(investFragment);
        list.add(newFragment);
        list.add(personalCenterFragment);
        return list;
    }

    @Override
    public int getItemCount() {
        return 4;
    }



    @Override
    public void onDoubleClick(final int index) {
        if (views != null)
        {
            if (views.get(index) instanceof OnDoubleClickListener)
            {
                rg.postDelayed(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        ((OnDoubleClickListener) views.get(index)).onDoubleClick(index);
                    }
                }, 50);
            }
        }
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };



    /**
     * 获取主页搜索框中随机显示的字符
     * @return
     */
    private String getHint()
    {
        size++;
        return "hello title --- " + size;
    }

    public void changeSearchHintFromPager()
    {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 0);
    }

    @Override
    public void setCurrent_tab(int index)
    {
        if (index == 2)
        {
            headerViewLayout.setVisibility(View.GONE);
            Util.setStatusBar(this, true);
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
        }
        else if (index == 3 || index == 4)
        {
            headerViewLayout.setVisibility(View.GONE);
            statusbarView.setVisibility(View.GONE);
        }
        else
        {
            Util.setStatusBar(this, true);
            headerViewLayout.setVisibility(View.VISIBLE);
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
        }
        super.setCurrent_tab(index);
    }

}
