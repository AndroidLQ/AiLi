package com.android.lq.p2p.lili.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.Constants;
import com.android.lq.p2p.lili.model.InvestGirdModel;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by a on 2016/12/27.
 */

public class AddNewFeatuersAdapter extends BGARecyclerViewAdapter<InvestGirdModel> {
    private int itemWidth;
    private String type;
    public AddNewFeatuersAdapter(RecyclerView recyclerView, int itemWidth,String type) {
        super(recyclerView,R.layout.griditem_item);
        this.itemWidth = itemWidth;
        this.type = type;
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.iv_image);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, InvestGirdModel model) {
        //动态设置item的宽度
        helper.getConvertView().setLayoutParams(new ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(Constants.RECYCLERVIEWADPTER_TYPE.equals(type)){
            if(position == 0 || position == 2 || position == 3){
                helper.setText(R.id.tv_text, model.getTitle()).setTextColor(R.id.tv_text,0xffff9900);
            }else{
                helper.setText(R.id.tv_text, model.getTitle()).setTextColor(R.id.tv_text,0xff000000);
            }
            helper.setImageBitmap(R.id.iv_image,BitmapFactory.decodeResource(mContext.getResources(), model.getImageId()));
        }else{
            helper.setText(R.id.tv_text, model.getTitle());
            helper.setImageBitmap(R.id.iv_image,BitmapFactory.decodeResource(mContext.getResources(), model.getImageId()));
        }

    }
}
