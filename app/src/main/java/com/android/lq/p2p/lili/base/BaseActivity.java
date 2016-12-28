package com.android.lq.p2p.lili.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.util.LaunchActivityUtils;
import com.android.lq.p2p.lili.util.Util;
import com.android.lq.p2p.lili.view.ChildTitleView;
import com.android.lq.p2p.lili.view.LoadingNewView8500;

/**
 * Created by a on 2016/12/15.
 */

public abstract class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    protected View statusbarView = null;

    protected View errorView = null;

    protected RelativeLayout loadingView = null;

    protected LoadingNewView8500 loadingNewView1 = null;

    protected RelativeLayout errorViewLayout = null;

    protected RelativeLayout centerViewLayout = null;

    protected ChildTitleView titleBarView = null;

    /**
     * 是否设置标题颜色
     */
    protected boolean hasChangeStatuBar = false;

    protected int statusBarHeight = 0;

    /**
     * 是否需要白色背景，默认false不需要
     */
    private boolean isWhiteBg = false;

    /**
     * statusbar背景颜色是否为轻颜色
     */
    protected boolean statusBarLight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.setStatusBar(this, statusBarLight);
        initLayout(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

    }

    /**
     * 初始化试图
     */
    private void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_base);
        statusbarView = findViewById(R.id.baseStatusBarView);
        if (Build.VERSION.SDK_INT >= 21) {
            statusbarView.setVisibility(View.VISIBLE);
            statusBarHeight = Util.getStatusBarHight(this) == 0 ? 50 : Util.getStatusBarHight(this);
            Log.d("denglh", "height:" + statusBarHeight);
            statusbarView.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight));
        } else {
            statusbarView.setVisibility(View.GONE);
        }
        loadingView = (RelativeLayout) findViewById(R.id.baseLoadingView);
        loadingNewView1 = (LoadingNewView8500) findViewById(R.id.base_view_loading_view);
        errorViewLayout = (RelativeLayout) findViewById(R.id.baseErrorView);
        centerViewLayout = (RelativeLayout) findViewById(R.id.baseCenterView);
        titleBarView = (ChildTitleView) findViewById(R.id.baseMarketTitleBarView);
        initCenterView();
        setListener();
        processLogic(savedInstanceState);

        dayOrNight(true);
    }

    /**
     * 给View控件添加事件监听器
     */
    protected void setListener() {
    }

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);


    /**
     * 白天与夜间模式切换改变
     */
    protected void dayOrNight(boolean dayornight) {
        titleBarView.setBackgroundResource();
        if (dayornight) {
            if (!hasChangeStatuBar) {
                statusbarView.setBackgroundResource(R.color.night_mode_bg_deep);
            }
            loadingView.setBackgroundResource(R.color.night_mode_bg_shallow);
            errorViewLayout
                    .setBackgroundResource(R.color.night_mode_bg_shallow);
            centerViewLayout
                    .setBackgroundResource(R.color.night_mode_bg_shallow);
            if (errorView != null) {
                errorView.setBackgroundResource(R.color.night_mode_bg_shallow);
            }
        } else {
            if (!hasChangeStatuBar) {
                statusbarView.setBackgroundResource(R.color.title_bg_color2);
            }
            if (isWhiteBg) {
                loadingView.setBackgroundResource(R.color.white);
                errorViewLayout.setBackgroundResource(R.color.white);
                centerViewLayout.setBackgroundResource(R.color.white);
                if (errorView != null) {
                    errorView.setBackgroundResource(R.color.white);
                }
            } else {
                loadingView.setBackgroundResource(R.color.main_bg1);
                errorViewLayout.setBackgroundResource(R.color.main_bg1);
                centerViewLayout.setBackgroundResource(R.color.main_bg1);
                if (errorView != null) {
                    errorView.setBackgroundResource(R.color.main_bg1);
                }
            }
        }
    }


    /**
     * 是否显示标题
     *
     * @param isVisibility
     */
    protected void setTitleBarViewVisibility(boolean isVisibility) {
        if (titleBarView == null) {
            return;
        }
        if (isVisibility) {
            titleBarView.setVisibility(View.VISIBLE);
        } else {
            titleBarView.setVisibility(View.GONE);
        }
    }


    /**
     * 初始化视图
     */
    protected abstract void initCenterView();

    /**
     * 设置中间试图
     *
     * @param layoutId
     */
    protected void setCenterView(int layoutId) {
        View.inflate(this, layoutId, centerViewLayout);
    }

    /**
     * 设置中间试图
     *
     * @param view
     */
    protected void setCenterView(View view) {
        setCenterView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
    }

    /**
     * 设置中间试图
     *
     * @param view
     * @param params
     */
    protected void setCenterView(View view, ViewGroup.LayoutParams params) {
        centerViewLayout.addView(view, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity();
    }


    /**
     * 开启一个Activity
     *
     * @param from        从哪个Activity进行跳转
     * @param to          跳转到哪个Activity
     * @param intent      意图
     * @param requestCode 请求码
     * @param hasResult   是否需要返回的结果(true表示需要返回的结果)
     */
    protected void startActivity(Activity from, Class<?> to, Intent intent, int requestCode, boolean hasResult) {
        if (hasResult) {
            LaunchActivityUtils.startActivityForResult(from, to, intent, requestCode);
        } else {
            LaunchActivityUtils.startActivity(from, to, intent);
        }
    }

}
