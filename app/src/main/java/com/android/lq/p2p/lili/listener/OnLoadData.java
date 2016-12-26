package com.android.lq.p2p.lili.listener;

/**
 * viewPager滑动改变页面时加载数据，避免预加载
 * @author lung
 *
 */
public interface OnLoadData
{
    /**
     * 第一次加载数据
     */
    public void onLoadData();
}
