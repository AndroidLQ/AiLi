package com.android.lq.p2p.lili.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.BaseFragment;
import com.android.lq.p2p.lili.listener.OnLoadData;

/**
 * Created by a on 2016/12/23.
 */

public class InvestFragment extends BaseFragment implements OnLoadData ,View.OnClickListener{
    @Override
    protected void initView(RelativeLayout view) {
        //不要loading图
        isHaveLoadingView = false;
        if(loadingView != null) loadingView.setVisibility(View.GONE);

        setCenterView(R.layout.fragment_new);
        titleBarView.setRightViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"项目分类",Toast.LENGTH_LONG).show();
            }
        });
        titleBarView.setCenterTitle("理财");
        titleBarView.setRightText("理财分类");
        titleBarView.setLightViewVisibility();

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
