package com.android.lq.p2p.lili.base;

/**
 * Created by a on 2016/12/23.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.ui.HomeActivity;
import com.android.lq.p2p.lili.util.MarketAsyncTask;
import com.android.lq.p2p.lili.util.Util;
import com.android.lq.p2p.lili.view.ChildTitleView;
import com.android.lq.p2p.lili.view.LoadingNewView8500;

/**
 * Created by a on 2016/12/12.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    private RelativeLayout baseView;
    protected RelativeLayout centerViewLayout = null;
    protected RelativeLayout loadingView = null;
    protected LoadingNewView8500 loadingNewView1 = null;
    protected RelativeLayout errorViewLayout = null;
    protected ChildTitleView titleBarView = null;

    protected View errorView = null;

    /** 初始化是否只拿一次数据 */
    protected boolean isLoadDataOnce = true;

    /**
     * 子Fragment是否需要Loading图
     */
    protected boolean isHaveLoadingView = true;

    private HomeActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HomeActivity) activity;
    }


    public void setLoadDataOnce(boolean isLoadDataOnce)
    {
        this.isLoadDataOnce = isLoadDataOnce;
    }


    /**
     * 设置中间试图
     *
     * @param layoutId
     */
    protected void setCenterView(int layoutId) {
        View.inflate(getActivity(), layoutId, centerViewLayout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        baseView = (RelativeLayout) inflater.inflate(R.layout.fragment_base, null);
        baseView.findViewById(R.id.baseStatusBarView).setVisibility(View.GONE);
        //加载进度view
        loadingView = (RelativeLayout) baseView.findViewById(R.id.baseLoadingView);
        loadingNewView1 = (LoadingNewView8500) baseView.findViewById(R.id.base_view_loading_view);
        //加载失败view
        errorViewLayout = (RelativeLayout) baseView.findViewById(R.id.baseErrorView);
        //设置内容view
        centerViewLayout = (RelativeLayout) baseView.findViewById(R.id.baseCenterView);
        //头部布局
        titleBarView = (ChildTitleView) baseView
                .findViewById(R.id.baseMarketTitleBarView);
        initView(baseView);

        setListener();
        processLogic(savedInstanceState);

        if (!isHaveLoadingView) {
            loadingNewView1.stop();
            loadingNewView1.setLoading(false);
        }

        if (loadingData)
        {
            loadingView.setVisibility(View.VISIBLE);
        }

        return baseView;
    }

    /**
     * 初始化中间视图
     *
     * @return
     */
    protected abstract void initView(RelativeLayout view);


    protected void setCenterViewLayoutVisibity(boolean isvisable){

        if(isvisable){
            centerViewLayout.setVisibility(View.VISIBLE);
        }else{
            centerViewLayout.setVisibility(View.GONE);
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
     * 默认显示网络连接失败失败
     */
    protected void showErrorView() {
        // 网络不给力，请稍后刷新
        showNewErrorView(R.mipmap.blank_img_despair_big, R.mipmap.blank_net_error, R.mipmap.blank_goto_refresh);
    }

    /**
     * 显示无网络或者其他错误页面信息的新视图
     *
     * @param resId      图片资源
     * @param tvResId    错误的图片文字资源
     * @param clickResId 点击的图片文字资源,不需要时，置为0.
     */
    protected void showNewErrorView(int resId, int tvResId, int clickResId) {
        showNewErrorView(getActivity(), resId, tvResId, clickResId);
    }

    protected void showNewErrorView(Context context, int resId, int tvResId,
                                    int clickResId) {
        if (errorView == null) {
            errorView = getNewErrorView(context, resId, tvResId, clickResId);
            errorViewLayout.setVisibility(View.VISIBLE);
            errorViewLayout.addView(errorView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        } else {
            errorViewLayout.setVisibility(View.VISIBLE);
        }

        if (false) {
            errorView.setBackgroundResource(R.color.night_mode_bg_shallow);
        } else {
            errorView.setBackgroundResource(R.color.main_bg);
        }
    }

    /**
     * 得到显示无网络或者其他错误页面信息的新视图(图片+图片文字+可点击的图片文字)
     *
     * @param resId      图片资源
     * @param tvResId    错误的图片文字资源
     * @param clickResId 点击的图片文字资源,不需要时，置为0.
     */
    protected View getNewErrorView(Context context, final int resId,
                                   int tvResId, final int clickResId) {
//        View view = View.inflate(context, R.layout.show_new_error, null);
//        ImageView img = (ImageView) view.findViewById(R.id.show_new_error_img);
//        ImageView textView = (ImageView) view
//                .findViewById(R.id.show_new_error_text);
//        ImageView retryButton = (ImageView) view
//                .findViewById(R.id.show_new_error_retry);
//        RelativeLayout retry = (RelativeLayout) view
//                .findViewById(R.id.retry_new_layout);
//        retryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!Util.isNetworkAvailable(getActivity())) {
//                    Util.showNetErrorDialog(getActivity());
//                } else if (clickResId != 0) {
//                    if (clickResId == R.mipmap.blank_goto_refresh) {
//                        if (loadingView != null) {
//                            loadingView.setVisibility(View.VISIBLE);
//                            if (errorViewLayout != null) {
//                                errorViewLayout.setVisibility(View.GONE);
//                            }
//                        }
//                        tryAgain();
//                    } else {
//                        // 其他点击处理
//                        clickEvent();
//                    }
//                }
//            }
//        });
//
//        img.setBackgroundResource(resId);
//        textView.setBackgroundResource(tvResId);
//        if (clickResId != 0) {
//            retryButton.setBackgroundResource(clickResId);
//        } else {
//            retryButton.setVisibility(View.GONE);
//        }

        //另外一种errorview
        View view = View.inflate(context, R.layout.view_load_error, null);
        Button loadErrorBn = (Button) view.findViewById(R.id.loadErrorBn);
        loadErrorBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(getActivity())) {
                    Util.showNetErrorDialog(getActivity());
                } else if (clickResId != 0) {
                    if (clickResId == R.mipmap.blank_goto_refresh) {
                        if (loadingView != null) {
                            loadingView.setVisibility(View.VISIBLE);
                            if (errorViewLayout != null) {
                                errorViewLayout.setVisibility(View.GONE);
                            }
                        }
                        tryAgain();
                    } else {
                        // 其他点击处理
                        clickEvent();
                    }
                }
            }
        });
        return view;
    }

    /**
     * 重试
     */
    protected void tryAgain() {
        Toast.makeText(getActivity(), "调用子类方法", Toast.LENGTH_SHORT).show();
    }

    /**
     * 其他点击事件
     */
    protected void clickEvent() {
        Toast.makeText(getActivity(), "点击其他区域调用方法", Toast.LENGTH_SHORT).show();
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


    protected boolean loadingData = false;

    /**
     * 开始加载数据
     */
    protected MarketAsyncTask<Integer, Void, Boolean> doLoadData(
            Integer... params)
    {
//        DLog.d(TAG, "doLoadData ---isActivityCreated = " + isActivityCreated
//                + "---getActivity =" + getActivity());

        MarketAsyncTask<Integer, Void, Boolean> task = new MarketAsyncTask<Integer, Void, Boolean>()
        {
            Integer[] params;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                if (loadingNewView1 != null)
                {
                    loadingNewView1.start();
                }
            }

            @Override
            protected Boolean doInBackground(Integer... params)
            {
//                DLog.i(TAG, "BaseFragment,doLoadData(),doInBackground() run!!!");
                this.params = params;
                loadingData = true;
                return initData(params);
            }

            @Override
            protected void onPostExecute(Boolean result)
            {
                super.onPostExecute(result);
//                DLog.i(TAG, "BaseFragment,doLoadData(),onPostExecute() run!!!");
                if (getActivity() == null)
                {
//                    DLog.i(TAG, "getActivity = null");
                    return;
                }

                if (errorViewLayout != null)
                {
                    errorViewLayout.setVisibility(View.GONE);
                }
                if (loadingNewView1 != null)
                {
                    loadingNewView1.stop();
                }
                refreshView(result, params);
                loadingData = false;
            }
        };

        task.doExecutor(params);
        return task;
    }

    /**
     * 子线程执行
     */
    protected abstract boolean initData(Integer... params);

    /**
     * UI线程执行
     */
    protected abstract void refreshView(boolean initSuccess, Integer... params);

}
