package com.android.lq.p2p.lili.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.android.lq.p2p.lili.R;
import com.android.lq.p2p.lili.adapter.GuideAdapter;
import com.android.lq.p2p.lili.base.BaseActivity;
import com.android.lq.p2p.lili.control.AiLiPreferences;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;

    private CirclePageIndicator indicator;

    private GuideAdapter guideAdapter;

    private List<View> viewLists;

    /** 屏幕高度 */
    private int height = 0;

    /** 屏幕宽度 */
    private int width = 0;

    /** 图片的宽度 */
    private int imgWidth = 0;

    /** 图片的高度 */
    private int imgHeight = 0;

    /** 按钮距离底部的高度 */
    private int btnBottomMargin = 110;
    private AiLiPreferences pref;

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initCenterView() {
        setCenterView(R.layout.activity_guid);
        setTitleBarViewVisibility(false);
        initView();
    }

    protected void initView() {
        pref = AiLiPreferences.getInstance(GuideActivity.this);
        pref.setFirstTag(true);


        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        indicator = (CirclePageIndicator) findViewById(R.id.guide_indicator);

        Resources res = getResources();
        width = res.getDisplayMetrics().widthPixels;
        height = res.getDisplayMetrics().heightPixels;
//      btnBottomMargin = 5;
//      imgHeight = (int) (height - btnBottomMargin- res.getDimension(R.dimen.dip38) - res.getDimension(R.dimen.dip100));
        imgHeight = width * 1034 / 1080;

        viewLists = new ArrayList<View>();
        viewLists.add(initItemLayout1(R.mipmap.guide1, null));
        viewLists.add(initItemLayout1(R.mipmap.guide2, null));
        viewLists.add(initItemLayout1(R.mipmap.guide3, null));
        viewLists.add(initItemLayout1(R.mipmap.guide4, ""));

        guideAdapter = new GuideAdapter(viewLists);
        indicator.setPadding(15f);
        viewPager.setAdapter(guideAdapter);
        viewPager.setCurrentItem(0);

        indicator.setViewPager(viewPager);
        indicator.setCurrentItem(0);
        indicator.setOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int arg0)
            {
                viewPager.setCurrentItem(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
    }

    /**
     * 向导页 样式一（图片+按钮(可有可无)）
     * @param resId 图片的资源ID
     * @param btnContent 按钮上文字，若为null，则不显示按钮
     */
    private View initItemLayout1(int resId, String btnContent)
    {
        View itemView = View.inflate(GuideActivity.this,  R.layout.guide_item_layout, null);
        TextView btnTv = (TextView) itemView.findViewById(R.id.guide_tv);
        LayoutParams paramsTv = (LayoutParams) btnTv.getLayoutParams();
        paramsTv.bottomMargin = btnBottomMargin;
        btnTv.setLayoutParams(paramsTv);

        ImageView iv = (ImageView) itemView.findViewById(R.id.bg_img);
        LayoutParams paramsIv = (LayoutParams) iv.getLayoutParams();
        paramsIv.height = height;
        paramsIv.width = width;
        iv.setLayoutParams(paramsIv);

        iv.setImageResource(resId);
        if (btnContent != null)
        {
            btnTv.setVisibility(View.VISIBLE);
            btnTv.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    HomeActivity.startActivity(GuideActivity.this);
                    finish();
                }
            });
        }
        else
        {
            btnTv.setVisibility(View.GONE);
        }
        return itemView;
    }

}
