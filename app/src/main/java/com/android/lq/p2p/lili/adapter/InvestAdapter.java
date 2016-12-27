package com.android.lq.p2p.lili.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.base.BaseViewHolder;
import com.android.lq.p2p.lili.model.InvestGirdModel;

import java.util.ArrayList;

/**
 * Created by a on 2016/11/25.
 */

public class InvestAdapter extends BaseRecyclerViewAdapter<InvestGirdModel> {
    private Context mContext;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new ViewHolder(inflater.inflate(getLayoutResource(), parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            InvestGirdModel investGirdModel = datas.get(position);
            viewHolder.iv.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), investGirdModel.getImageId()));
            viewHolder.title.setText(investGirdModel.getTitle());
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.griditem_item;
    }


    public InvestAdapter(Context context, ArrayList<InvestGirdModel> datas) {
        super(context, datas);
        this.mContext = context;

    }

    private class ViewHolder extends BaseViewHolder {

        private ImageView iv;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_image);
            title = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
