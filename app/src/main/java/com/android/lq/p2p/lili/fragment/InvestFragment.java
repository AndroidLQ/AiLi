package com.android.lq.p2p.lili.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.adapter.HotPlaceRecycerViewAdapter;
import com.android.lq.p2p.lili.base.BaseFragment;
import com.android.lq.p2p.lili.listener.OnLoadData;
import com.android.lq.p2p.lili.model.InvestGirdModel;
import com.android.lq.p2p.lili.util.Util;

import java.util.ArrayList;

/**
 * Created by a on 2016/12/23.
 */

public class InvestFragment extends BaseFragment implements OnLoadData ,View.OnClickListener{
    private RecyclerView hot_place_recyclerview;
    private HotPlaceRecycerViewAdapter hotPlaceRecycerViewAdapter;
    private ArrayList<InvestGirdModel> hotplaceData = new ArrayList<>();

    private RecyclerView classical_journey_recyclerview;

    @Override
    protected void initView(RelativeLayout view) {
        //不要loading图
        isHaveLoadingView = false;
        if(loadingView != null) loadingView.setVisibility(View.GONE);

        setCenterView(R.layout.fragment_new);
        titleBarView.setRightViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity,"项目分类",Toast.LENGTH_LONG).show();
            }
        });
        titleBarView.setCenterTitle("理财");
        titleBarView.setRightText("理财分类");
        titleBarView.setLightViewVisibility();

        hot_place_recyclerview = (RecyclerView) view.findViewById(R.id.hot_place_recyclerview);
        classical_journey_recyclerview = (RecyclerView) view.findViewById(R.id.classical_journey_recyclerview);


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        int itemWidth = Util.getScreenPiexWidth(mActivity) / 3;
        hotPlaceRecycerViewAdapter = new HotPlaceRecycerViewAdapter(hot_place_recyclerview,itemWidth);
        hot_place_recyclerview.setLayoutManager(getLinearLayoutManager());
        hot_place_recyclerview.setAdapter(hotPlaceRecycerViewAdapter);
        initListData();
        hotPlaceRecycerViewAdapter.setData(hotplaceData);

        classical_journey_recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        classical_journey_recyclerview.setAdapter(hotPlaceRecycerViewAdapter);

    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
    }

    private void initListData() {

        InvestGirdModel investGirdModel = null;
        for (int i = 0 ; i < 3 ; i++){
            investGirdModel = new InvestGirdModel();
            investGirdModel.setImageId(R.mipmap.u1830);
            investGirdModel.setTitle("香港");
            hotplaceData.add(investGirdModel);
        }

    }

    @Override
    protected boolean initData(Integer... params) {
        return false;
    }

    @Override
    protected void refreshView(boolean initSuccess, Integer... params) {

    }

    //点击RadioButton调用
    @Override
    public void onLoadData() {
        Log.i("HomeFragment","InvestFragment--onLoadData");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.child_title_right_lay:

                break;
        }
    }
}
