package com.android.lq.p2p.lili.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.adapter.MyRecycerViewAdapter;
import com.android.lq.p2p.lili.base.BaseFragment;
import com.android.lq.p2p.lili.base.URL;
import com.android.lq.p2p.lili.listener.OnLoadData;
import com.android.lq.p2p.lili.model.InvestBean;
import com.android.lq.p2p.lili.model.InvestGirdModel;
import com.android.lq.p2p.lili.net.okhttp.HttpRequest;
import com.android.lq.p2p.lili.net.okhttp.InvestNet;
import com.android.lq.p2p.lili.net.request.xutils.IRequestCallback;
import com.android.lq.p2p.lili.net.request.xutils.IRequestManager;
import com.android.lq.p2p.lili.net.request.xutils.XutilRequestManager;
import com.android.lq.p2p.lili.ui.AddNewFeaturesActivity;
import com.android.lq.p2p.lili.ui.WebViewActivity;
import com.android.lq.p2p.lili.util.GsonImpl;
import com.android.lq.p2p.lili.util.Util;
import com.android.lq.p2p.lili.view.NetworkImageHolderView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * Created by a on 2016/12/23.
 */

public class HomeFragment extends BaseFragment implements OnLoadData, ViewPager.OnPageChangeListener, com.bigkoo.convenientbanner.listener.OnItemClickListener,BGAOnItemChildClickListener,BGAOnRVItemClickListener {

    private String TAG = this.getClass().getSimpleName().toString();

    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<String> networkImages = new ArrayList<>();
    private InvestBean investBean;
    private IRequestManager iRequestManager;

    /** 加载广告头数据 */
    private final int LOAD_INVERTADS_CACHE_DATA = 0;

    private RecyclerView recyclerView;
    private ArrayList<InvestGirdModel> datas = new ArrayList<>();
    private String title[] = new String[]{"账户信息", "充值", "提现"};
    private int imageId[] = new int[]{R.mipmap.icon_zhanghuxinxi2, R.mipmap.icon_yinhangkaguanli2,R.mipmap.icon_zijinjilu2};
    private MyRecycerViewAdapter myRecycerViewAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"homefragment#onCreate");
        if(savedInstanceState == null){
            setLoadDataOnce(false);
        }

    }

    @Override
    protected void initView(RelativeLayout view) {
        setCenterView(R.layout.fragment_home);
        setTitleBarViewVisibility(false);
        iRequestManager = XutilRequestManager.getInstance();

        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);

        recyclerView = (RecyclerView) view.findViewById(R.id.invest_recyclerview);
        int itemWidth = Util.getScreenPiexWidth(mActivity) / 4;
        myRecycerViewAdapter = new MyRecycerViewAdapter(recyclerView,itemWidth);
        //设置 item子view监听事件
//        myRecycerViewAdapter.setOnItemChildClickListener(this);
        myRecycerViewAdapter.setOnRVItemClickListener(this);

        //初始化
        initDatas();
        //加载数据
        loadingData = true;
        doLoadData(LOAD_INVERTADS_CACHE_DATA);
    }

    public void initDatas() {
        datas = new ArrayList<>();
        InvestGirdModel investGirdModel = null;
        for (int i = 0; i < title.length; i++) {
            investGirdModel = new InvestGirdModel();
            investGirdModel.setImageId(imageId[i]);
            investGirdModel.setTitle(title[i]);
            datas.add(investGirdModel);
        }
        //最后一个
        addEndItem();
    }

    public void addEndItem() {
        //最后一个
        InvestGirdModel investGirdModel = new InvestGirdModel();
        investGirdModel.setImageId(R.mipmap.small_add);
        investGirdModel.setTitle("添加");
        datas.add(investGirdModel);
    }


    private void init() {
        Log.i(TAG, "HomeFragment--init");
        initImageLoader();
        //设置广告头
        initConvenientBanner();

        //手动New并且添加到ListView Header的例子
//        ConvenientBanner mConvenientBanner = new ConvenientBanner(this,false);
//        mConvenientBanner.setMinimumHeight(500);
//        mConvenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, networkImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
//                        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnItemClickListener(this);
//        listView.addHeaderView(mConvenientBanner);
    }

    private void initConvenientBanner() {
        if(convenientBanner != null){
            //网络加载例子
            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, networkImages)
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setOnPageChangeListener(this)//监听翻页事件
                    .setOnItemClickListener(this);
        }
    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mActivity.getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        //停止翻页
        convenientBanner.stopTurning();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        WebViewActivity.startActivity(mActivity,investBean.getHomeAds().get(position).getUrl());
    }


    //点击RadioButton调用
    @Override
    public void onLoadData() {
        Log.i(TAG, "HomeFragment--onLoadData");

    }


    @Override
    protected void tryAgain() {
        super.tryAgain();
        setCenterViewLayoutVisibity(false);
        doLoadData(LOAD_INVERTADS_CACHE_DATA);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 测试 LinearLayoutManager
        recyclerView.setLayoutManager(getLinearLayoutManager());

        // 测试没有 Header 和 Footer 的情况
        recyclerView.setAdapter(myRecycerViewAdapter);

        myRecycerViewAdapter.setData(datas);
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    protected boolean initData(Integer... params) {
        boolean isSuccess = true;
        switch (params[0]){
            case LOAD_INVERTADS_CACHE_DATA:
                ArrayList<String> mainUrls = new ArrayList<String>();
                mainUrls.add(URL.IVEST_URL);
                HttpRequest.init(mActivity.getApplicationContext(), mainUrls);
                investBean = InvestNet.getBannerInfos();
                if(investBean != null){
                    isSuccess = true;
                }else{
                    isSuccess = false;
                }
                break;
        }
        return isSuccess;
    }

    @Override
    protected void refreshView(boolean initSuccess, Integer... params) {
        switch (params[0]){
            case LOAD_INVERTADS_CACHE_DATA:
                if(initSuccess){

                    List<InvestBean.HomeAdsBean> adsList = investBean.getHomeAds();
                    for (int i = 0; i < adsList.size(); i++) {
                        networkImages.add(adsList.get(i).getImage_filename());
                    }

                    if(!isLoadDataOnce){
                        init();
                    }else{
                        if(convenientBanner != null){
                            convenientBanner.notifyDataSetChanged();
                        }
                    }

                    if (loadingView != null)
                    {
                        loadingView.setVisibility(View.GONE);
                    }
                    if (errorViewLayout != null)
                    {
                        errorViewLayout.setVisibility(View.GONE);
                    }
                    if (loadingNewView1 != null)
                    {
                        loadingNewView1.stop();
                    }

                    setCenterViewLayoutVisibity(true);

                    loadingView.setVisibility(View.GONE);

                }else{
                    showErrorView();
                }

                break;
        }

        isLoadDataOnce = true;
    }

    /***************************** recyclerview 监听器开始 ************************************/
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Toast.makeText(mActivity,position+"item 子view监听事件",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if(position == datas.size() - 1){
            startActivity(mActivity, AddNewFeaturesActivity.class,null,0,false);
        }
    }
    /***************************** recyclerview 监听器结束 ************************************/
}
