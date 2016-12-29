package com.android.lq.p2p.lili.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.BaseFragment;
import com.android.lq.p2p.lili.listener.OnLoadData;

/**
 * Created by a on 2016/12/23.
 */

public class PersonalCenterFragment extends BaseFragment implements OnLoadData {

    private FrameLayout fragmentContainer;

    @Override
    protected void initView(RelativeLayout view) {
        setCenterView(R.layout.fragment_personal_center);
        setTitleBarViewVisibility(false);
        loadingView.setVisibility(View.GONE);

        fragmentContainer = (FrameLayout) view.findViewById(R.id.fragmentContainer);
        View.inflate(mActivity,R.layout.morelist_layout,fragmentContainer);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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

    }
}
