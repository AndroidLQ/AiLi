package com.android.lq.p2p.lili.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.model.InvestBean;
import com.android.lq.p2p.lili.model.InvestGirdModel;
import com.android.lq.p2p.lili.view.ProgressWheel;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by a on 2016/12/27.
 */

public class InvestBottomRecycerViewAdapter extends BGARecyclerViewAdapter<InvestBean.InvestingBidsBean> {

    public InvestBottomRecycerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView,R.layout.item_new_full_invest);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, InvestBean.InvestingBidsBean model) {
        //动态设置item的宽度
        helper.setText(R.id.tv_invest_money,String.valueOf(model.getAmount()));
        helper.setText(R.id.tv_title,model.getTitle());
        helper.setText(R.id.img_right_rate,model.getApr()+"");

        ProgressWheel progressWheel = (ProgressWheel)helper.getView(R.id.rateLayout);
        progressWheel.isSpinning();
        progressWheel.setProgress(model.getApr());
    }
}
