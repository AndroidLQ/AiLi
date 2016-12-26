package com.android.lq.p2p.lili.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.lq.p2p.lili.R;

public class MarketFloateDialogBuilder {
    Dialog dialog;

    Context context;

    String[] buttonText = new String[2];

    OnClickListener leftL, rightL;

    String title = "温馨提示", message = "";

    boolean isCancelable = true;

    boolean titleVisibility = true;

    View dialogMain;

    View bottomView, centerView;

    TextView btnLeft, btnRight;

    View bottomBarLine, buttonDevide, topLine;

    LinearLayout bottomBar;

    private int dialogHeight = LayoutParams.WRAP_CONTENT;

    SpannableStringBuilder spanMessage = null;

    TextView titleTv = null;

    /**
     * 是否已经有了一个按钮了
     */
    boolean hasButton = false;

    /**
     * 是否显示标题左边的图片
     */
    boolean isShowTitleImage = true;

    public MarketFloateDialogBuilder(Context context) {
        this.context = context;
        // dialogHeight = context.getResources().getDimensionPixelSize(
        // R.dimen.update_dialog_height);
    }

    public Dialog crteate() {
        dialog = new Dialog(context, R.style.Float_Dialog);
        dialog.setContentView(R.layout.market_floate_dialog_content_keyboard);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        window.setLayout(LayoutParams.MATCH_PARENT, dialogHeight/*
                                                                 * lp.WRAP_CONTENT
																 */);// 此处高度不能自适应，否则窗口动画会异常
        window.setWindowAnimations(R.style.Float_Dialog_style);
        // window.setGravity(Gravity.BOTTOM); //此处可以设置dialog显示的位置

        dialogMain = dialog.findViewById(R.id.dialog_main);
        FrameLayout.LayoutParams contentLp = new FrameLayout.LayoutParams(
                ViewGroup.MarginLayoutParams.MATCH_PARENT, dialogHeight);
        dialogMain.setLayoutParams(contentLp);
        titleTv = (TextView) dialog.findViewById(R.id.title_tv);
        if (!titleVisibility) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setText(title);
        }
        if (!isShowTitleImage) {
            titleTv.setCompoundDrawables(null, null, null, null);
        }

        TextView messageTv = (TextView) dialog.findViewById(R.id.message_tv);
        if (spanMessage != null) {
            messageTv.setText(spanMessage);
        } else {
            messageTv.setText(message);
        }

        // 处理底部按钮布局
        bottomBarLine = dialog.findViewById(R.id.bottom_bar_line);
        bottomBar = (LinearLayout) dialog.findViewById(R.id.bottom_bar);
        buttonDevide = dialog.findViewById(R.id.btn_devide);
        btnLeft = (TextView) dialog.findViewById(R.id.btn_1);
        btnRight = (TextView) dialog.findViewById(R.id.btn_2);

        // 单按钮情况显示情况
        if (leftL != null && rightL == null) {// 左边按钮
            bottomBarLine.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.VISIBLE);
            buttonDevide.setVisibility(View.GONE);
            btnRight.setVisibility(View.GONE);
            // LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
            // context.getResources().getDimensionPixelSize(
            // R.dimen.dialog_single_button_width),
            // LayoutParams.MATCH_PARENT);
            // lp1.gravity = Gravity.CENTER_HORIZONTAL;
            // bottomBar.setLayoutParams(lp1);
        } else if (rightL != null && leftL == null) {// 右边按钮
            bottomBarLine.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.GONE);
            buttonDevide.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            // LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
            // context.getResources().getDimensionPixelSize(
            // R.dimen.dialog_single_button_width),
            // LayoutParams.MATCH_PARENT);
            // lp2.gravity = Gravity.CENTER_HORIZONTAL;
            // bottomBar.setLayoutParams(lp2);
        } else if (leftL != null && rightL != null) {// 双按钮
            bottomBarLine.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.VISIBLE);
            buttonDevide.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
        }

        if (leftL != null) {
            btnLeft.setText(buttonText[0]);
            btnLeft.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (leftL != null) {
                        leftL.onClick(v);
                    }
                }
            });
        }
        if (rightL != null) {
            btnRight.setText(buttonText[1]);
            btnRight.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (rightL != null) {
                        rightL.onClick(v);
                    }
                }
            });
        }

        if (bottomView != null) {
            bottomBarLine.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            bottomBar.removeAllViews();
            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            bottomBar.addView(bottomView, lp3);
        }
        if (centerView != null) {
            LinearLayout centerContainer = (LinearLayout) dialog
                    .findViewById(R.id.center_container);
            centerContainer.removeAllViews();
            centerContainer.addView(centerView);
        }
        dialog.setCancelable(isCancelable);
        return dialog;
    }

    /**
     * 设置背景透明度
     */
    public void setBackgroundAlpha(int alpha) {
        topLine.setVisibility(View.GONE);
        if (dialogMain.getBackground() != null) {
            dialogMain.getBackground().setAlpha(alpha);
        } else {
            dialogMain.setBackgroundColor(0xffffffff);
            dialogMain.getBackground().setAlpha(alpha);
        }
    }

    public void show() {
        if (dialog == null) {
            crteate();
        }
        dialog.show();
    }

    /**
     * 是否显示标题前面的感叹号
     *
     * @param isShow true,为显示
     */
    public void setTitleImageShow(boolean isShow) {
        this.isShowTitleImage = isShow;
    }

    /**
     * 设置最左边按钮
     *
     * @param text
     * @param listener
     */
    public void setLeftButton(String text, OnClickListener listener) {
        buttonText[0] = text;
        leftL = listener;
        if (dialog != null && leftL != null) {
            if (hasButton) {
                View btnline = dialog.findViewById(R.id.btn_devide);
                btnline.setVisibility(View.VISIBLE);
            } else {
                hasButton = true;
            }
            View bottomBar = dialog.findViewById(R.id.bottom_bar);
            View bottomline = dialog.findViewById(R.id.bottom_bar_line);
            bottomBar.setVisibility(View.VISIBLE);
            bottomline.setVisibility(View.VISIBLE);
            TextView btn1 = (TextView) dialog.findViewById(R.id.btn_1);
            btn1.setVisibility(View.VISIBLE);
            btn1.setText(buttonText[0]);
            btn1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (leftL != null) {
                        leftL.onClick(v);
                    }
                }
            });
        }
    }

    /**
     * 设置右边按钮
     *
     * @param text
     * @param listener
     */
    public void setRightButton(String text, OnClickListener listener) {
        buttonText[1] = text;
        rightL = listener;

        if (dialog != null && rightL != null) {
            if (hasButton) {
                View btnline = dialog.findViewById(R.id.btn_devide);
                btnline.setVisibility(View.VISIBLE);
            } else {
                hasButton = true;
            }
            View bottomBar = dialog.findViewById(R.id.bottom_bar);
            View bottomline = dialog.findViewById(R.id.bottom_bar_line);
            bottomline.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            TextView btn2 = (TextView) dialog.findViewById(R.id.btn_2);
            btn2.setVisibility(View.VISIBLE);
            btn2.setText(buttonText[1]);
            btn2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (rightL != null) {
                        rightL.onClick(v);
                    }
                }
            });
        }
    }

    /**
     * 设置对话框标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
        this.titleVisibility = true;
    }

    /**
     * 设置对话框中间的文本
     *
     */
    public void setMessage(String message) {
        this.spanMessage = null;
        this.message = message;
    }

    /**
     * 设置对话框中间的不同颜色的文本
     *
     */
    public void setSpanMessage(SpannableStringBuilder spanMessage) {
        this.spanMessage = spanMessage;
    }

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    /**
     * 设置是否显示标题
     *
     * @param titleVisibility
     */
    public void setTitleVisibility(boolean titleVisibility) {
        this.titleVisibility = titleVisibility;
    }

    /**
     * 自定义底部按钮条的内容
     *
     * @param view
     */
    public void setBottomView(View view) {
        bottomView = view;
        if (dialog != null && view != null) {
            LinearLayout bottomContainer = (LinearLayout) dialog
                    .findViewById(R.id.bottom_bar);
            bottomContainer.setVisibility(View.VISIBLE);
            bottomContainer.removeAllViews();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            bottomContainer.addView(view, lp);
        }
    }

    /**
     * 替换对话框中间布局的方法
     *
     * @param view
     */
    public void setCenterView(View view, RelativeLayout.LayoutParams params) {
        centerView = view;
        if (dialog != null && view != null) {
            LinearLayout centerContainer = (LinearLayout) dialog
                    .findViewById(R.id.center_container);
            centerContainer.removeAllViews();
            if (params != null) {
                centerContainer.setLayoutParams(params);
            }
            centerContainer.addView(view);
        }
    }

    public void setViewCenter() {
        LinearLayout centerContainer = (LinearLayout) dialog
                .findViewById(R.id.center_container);
        centerContainer.setGravity(Gravity.CENTER);
    }

    public int getDialogHeight() {
        return dialogHeight;
    }

    /**
     * 设置对话框高度。（非居中的dialog不支持WRAP_CONTENT，否则窗口弹出会出bug）
     *
     * @param dialogHeight
     */
    public void setDialogHeight(int dialogHeight) {
        // this.dialogHeight = dialogHeight;
    }

    /**
     * 设置标题icon
     *
     * @param resId
     */
    public void setTitleDrawable(int resId) {
        if (titleTv != null) {
            Drawable drawable = context.getResources().getDrawable(resId);
            // / 这一步必须要做，否则不会显示。
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            titleTv.setCompoundDrawables(drawable, null, null, null);
        }
    }
}
