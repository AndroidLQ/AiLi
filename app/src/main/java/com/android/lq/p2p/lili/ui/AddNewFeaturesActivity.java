package com.android.lq.p2p.lili.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.adapter.AddNewFeatuersAdapter;
import com.android.lq.p2p.lili.base.BaseActivity;
import com.android.lq.p2p.lili.base.Constants;
import com.android.lq.p2p.lili.model.InvestGirdModel;
import com.android.lq.p2p.lili.util.Util;

import java.util.ArrayList;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * Created by a on 2016/12/28.
 */

public class AddNewFeaturesActivity extends BaseActivity{
    private static String TAG = "AddNewFeaturesActivity";
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private AddNewFeatuersAdapter financingAdapter;
    private AddNewFeatuersAdapter accountAdapter;
    private RelativeLayout commonview;
    private int itemWidth;

    private String financingTitle[] = new String[]{"理财账号","投标记录","等待满标","收款中","理财统计","投标机器人","债权管理"};
    private int finacingImageId[] = new int[]{R.mipmap.licaizhangdan,R.mipmap.toubiaojilu,R.mipmap.dengdaimanbiao,R.mipmap.shoukuanzhong,R.mipmap.licaitongji,R.mipmap.jiqiren,R.mipmap.zhaiquanguanli};
    private int finacingSelectImageId[] = new int[]{R.mipmap.licaizhangdan_selected,R.mipmap.toubiaojilu_selected,R.mipmap.dengdaimanbiao_selected,R.mipmap.shoukuanzhong_selected,R.mipmap.licaitongji_selected,R.mipmap.jiqiren_selected,R.mipmap.zhaiquanguanli_selected};

    private String[] accountTitle = new String[]{"账户信息","资金记录","充值","提现","计算器","账户安全"};
    private int[] accountImageId = new int[]{R.mipmap.zhanghuxinxi_selected,R.mipmap.zijinjilu,R.mipmap.chongzhi_selected,R.mipmap.tixian_selected,R.mipmap.cal,R.mipmap.zhanghuanquan};
    private int[] accountSelectImageId = new int[]{R.mipmap.zhanghuxinxi_selected,R.mipmap.zijinjilu_selected,R.mipmap.chongzhi_selected,R.mipmap.tixian_selected,R.mipmap.cal_selected,R.mipmap.zhanghuanquan_selected};

    private ArrayList<InvestGirdModel> financingList = new ArrayList<>();
    private ArrayList<InvestGirdModel> accountList = new ArrayList<>();


    @Override
    protected void processLogic(Bundle savedInstanceState) {

        initFinacingList();
        initAccountList();

        // 测试 LinearLayoutManager
        recyclerView1.setLayoutManager(new GridLayoutManager(this,4));
        // 测试没有 Header 和 Footer 的情况
        recyclerView1.setAdapter(financingAdapter);
        financingAdapter.setData(financingList);

        // 测试 LinearLayoutManager
        recyclerView2.setLayoutManager(new GridLayoutManager(this,4));
        // 测试没有 Header 和 Footer 的情况
        recyclerView2.setAdapter(accountAdapter);
        accountAdapter.setData(accountList);

    }


    private void initAccountList() {
        InvestGirdModel investGirdModel = null;
        for (int i = 0 ; i < accountTitle.length ; i++){
            investGirdModel = new InvestGirdModel();
            investGirdModel.setTitle(accountTitle[i]);
            investGirdModel.setImageId(accountImageId[i]);
            accountList.add(investGirdModel);
        }
    }

    private void initFinacingList() {
        InvestGirdModel investGirdModel = null;
        for (int i = 0 ; i < financingTitle.length ; i++){
            investGirdModel = new InvestGirdModel();
            investGirdModel.setImageId(finacingImageId[i]);
            investGirdModel.setTitle(financingTitle[i]);
            financingList.add(investGirdModel);
        }
    }

    @Override
    protected void setListener() {
        financingAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                ImageView iv = (ImageView) itemView.findViewById(R.id.iv_image);
                TextView tx = (TextView) itemView.findViewById(R.id.tv_text);
                boolean bool = false;
                switch (position){
                    case 0:
                        bool = Constants.readBoolean(Constants.LICAIZHANGDAN,false);
                        Log.i(TAG,TAG+"#onRVItemClick-----bool:"+bool);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.LICAIZHANGDAN);
                        break;
                    case 1:
                        bool = Constants.readBoolean(Constants.TOUBIAOJILU,false);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.TOUBIAOJILU);
                        break;
                    case 2:
                        bool = Constants.readBoolean(Constants.DENGDAITOUBIAO,false);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.DENGDAITOUBIAO);
                        break;
                    case 3:
                        bool = Constants.readBoolean(Constants.SHOUKUANZHONG,false);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.SHOUKUANZHONG);
                        break;
                    case 4:
                        bool = Constants.readBoolean(Constants.LICAITONGJI,false);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.LICAITONGJI);
                        break;
                    case 5:
                        bool = Constants.readBoolean(Constants.JIQIREN,false);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.JIQIREN);
                        break;
                    case 6:
                        bool = Constants.readBoolean(Constants.ZHAIQUANGUANLI,false);
                        setImageBackground(bool,iv,tx,finacingSelectImageId,finacingImageId,position,Constants.ZHAIQUANGUANLI);
                        break;

                }


            }
        });

        accountAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                ImageView iv = (ImageView) itemView.findViewById(R.id.iv_image);
                TextView tx = (TextView) itemView.findViewById(R.id.tv_text);
                boolean bool = false;
                switch (position){
                    case 0:
                    case 2:
                    case 3:
                        Toast.makeText(AddNewFeaturesActivity.this,"此快捷方式不能修改！",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        bool = Constants.readBoolean(Constants.ZIJINJILU,false);
                        setImageBackground(bool,iv,tx,accountSelectImageId,accountImageId,position,Constants.ZIJINJILU);
                        break;
                    case 4:
                        bool = Constants.readBoolean(Constants.JISUANQI,false);
                        setImageBackground(bool,iv,tx,accountSelectImageId,accountImageId,position,Constants.JISUANQI);
                        break;
                    case 5:
                        bool = Constants.readBoolean(Constants.ZHANGHUANQUAN,false);
                        setImageBackground(bool,iv,tx,accountSelectImageId,accountImageId,position,Constants.ZHANGHUANQUAN);
                        break;
                }


            }
        });

    }

    public void setImageBackground(boolean bool ,ImageView iv,TextView tx,int[] selectImageId,int[] imageId,int position,String iamge_Tag){
        if(!bool){
            iv.setImageBitmap(BitmapFactory.decodeResource(AddNewFeaturesActivity.this.getResources(), selectImageId[position]));
            tx.setTextColor(0xffff9900);
            Constants.writeBoolean(iamge_Tag,true);
        }else{
            iv.setImageBitmap(BitmapFactory.decodeResource(AddNewFeaturesActivity.this.getResources(), imageId[position]));
            tx.setTextColor(0xff000000);
            Constants.writeBoolean(iamge_Tag,false);
        }
    }

    @Override
    protected void initCenterView() {
        setCenterView(R.layout.activity_add_new_features);
        titleBarView.setRightViewVisibility(false);
        titleBarView.setCenterTitleVisibility(false);
        titleBarView.setTitle("快捷功能");

        recyclerView1 = (RecyclerView) findViewById(R.id.financingLayout);
        recyclerView2 = (RecyclerView) findViewById(R.id.accountLayout);
        commonview = (RelativeLayout) findViewById(R.id.commonview);
        itemWidth = Util.getScreenPiexWidth(this) / 4;

        financingAdapter = new AddNewFeatuersAdapter(recyclerView1,itemWidth,"");
        accountAdapter = new AddNewFeatuersAdapter(recyclerView2,itemWidth,Constants.RECYCLERVIEWADPTER_TYPE);

    }



}
