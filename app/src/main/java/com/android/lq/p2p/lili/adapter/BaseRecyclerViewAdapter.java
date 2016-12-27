package com.android.lq.p2p.lili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lq.p2p.lili.base.BaseViewHolder;
import com.android.lq.p2p.lili.base.ImageConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/22.
 * RecyclerViewAdapter 基类
 * 主要封装onClick和onLongClick事件
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    /**
     * 以下方式为加载本地图片：
     * String imageUri = "http://site.com/image.png"; // from Web
     * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
     * String imageUri = "content://media/external/audio/albumart/13"; // from content provider
     * String imageUri = "assets://image.png"; // from assets
     * String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
     */

    //数据源
    protected ArrayList<T> datas;
    //布局加载器
    protected LayoutInflater inflater;

    protected DisplayImageOptions options;

    protected ImageLoader imageLoader;
    protected Context context;

    public BaseRecyclerViewAdapter(Context context, ArrayList<T> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
        options = ImageConfiguration.getDisplayImageOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(inflater.inflate(getLayoutResource(), parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (listener != null) {
            //按下
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRecyclerViewItemClick(v, position);
                }
            });

            //长按
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onRecyclerViewItemLongClick(v, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas != null && !datas.isEmpty() ? datas.size() : 0;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        //item的点击事件
        void onRecyclerViewItemClick(View view, int position);

        //item的长按点击事件
        void onRecyclerViewItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected abstract int getLayoutResource();
}
