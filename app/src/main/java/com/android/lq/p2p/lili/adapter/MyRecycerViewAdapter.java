package com.android.lq.p2p.lili.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.model.InvestGirdModel;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by a on 2016/12/27.
 */

public class MyRecycerViewAdapter extends BGARecyclerViewAdapter<InvestGirdModel> {
    private int itemWidth;
    public MyRecycerViewAdapter(RecyclerView recyclerView,int itemWidth) {
        super(recyclerView,R.layout.griditem_item);
        this.itemWidth = itemWidth;

    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.iv_image);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, InvestGirdModel model) {
        //动态设置item的宽度
        helper.getConvertView().setLayoutParams(new ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        helper.setText(R.id.tv_text, model.getTitle());
        helper.setImageBitmap(R.id.iv_image,BitmapFactory.decodeResource(mContext.getResources(), model.getImageId()));
    }
}
