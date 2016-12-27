package com.android.lq.p2p.lili.fragment;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.BaseFragment;
import com.android.lq.p2p.lili.listener.OnLoadData;

/**
 * Created by a on 2016/12/23.
 */

public class PersonalCenterFragment extends BaseFragment implements OnLoadData {
    @Override
    protected void initView(RelativeLayout view) {
        setCenterView(R.layout.fragment_personal_center);
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

    @Override
    public void onLoadData() {

    }
}
