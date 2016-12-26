package com.android.lq.p2p.lili.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LoadingNewView8500 extends View {
    /**
     * 圆形边框的圆心
     */
    int x0 = 0, y0 = 0;
    /**
     * 圆形边框的线条粗细
     */
    int radiusStroke = 0;
    /**
     * 小圆点的移动半径
     */
    int mRadius = 0;
    /**
     * 圆形边框的半径
     */
    int strokeWidht = 0;
    /**
     * 移动的圆点的半径
     */
    int dotRadius = 0;
    /**
     * 动画三振幅
     */
    int waveHeightAnim3 = 0;
    /**
     * 画圆形框的画笔
     */
    Paint paint;
    /**
     * 画圆形框上进度条的画笔
     */
    Paint paintProgress;
    /**
     * 画移动小圆点的画笔
     */
    Paint paintDot;
    /**
     * 动画3开始播放时候圆点的位置
     */
    Point anim3Start;
    /**
     * 动画4开始播放时候圆点的位置
     */
    Point anim4Start;
    /**
     * 动画5开始播放时候圆点的位置
     */
    Point anim5Start;

    RectF progressRect;

    Point dotPointNow;

    /**
     * 时间进度
     */
    float timePercentInCurrAnim = 0f;

    /**
     * 動畫刷新率（每一幀的時間）
     */
    final long timePerFrame = 17;

    long time0 = 0;
    final long time1 = 700;
    final long time2 = 500;
    final long time3 = 300;
    final long time4 = 250;
    final long time5 = 288;//time4*2*√(1/3)
    final long time6 = 167;//time4*2/3
    final long totolTime = time1 + time2 + time3 + time4 + time5 + time6;
    private boolean isLoading = true;

    @SuppressLint("NewApi")
    public LoadingNewView8500(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public LoadingNewView8500(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadingNewView8500(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingNewView8500(Context context) {
        super(context);
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            init();
        }
    }

    /**
     * 开始播放動畫
     */
    public void start() {
        this.removeCallbacks(refreshR);
        time0 = System.currentTimeMillis() + 20;
        this.postDelayed(refreshR, 20);
    }

    /**
     * 停止播放動畫
     */
    public void stop() {
        this.removeCallbacks(refreshR);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 开始动画
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止动画
        stop();
    }

    private void init() {
        setWillNotDraw(false);
        x0 = (getWidth() - 2) / 2;
        y0 = (getHeight() - 2) / 2;
        strokeWidht = Math.min(getWidth(), getHeight()) / 30;
        radiusStroke = Math.min(x0, y0) - 1 - strokeWidht / 2;
        dotRadius = (int) (Math.min(getWidth(), getHeight()) * 0.075 + 0.5f);
        mRadius = Math.min(x0, y0) - 1 - strokeWidht - dotRadius;
        waveHeightAnim3 = radiusStroke / 6;

        progressRect = new RectF(x0 - radiusStroke, y0 - radiusStroke, x0 + radiusStroke, y0 + radiusStroke);

        paint = new Paint();
        paint.setColor(0xffcacaca);
        paint.setStrokeWidth(strokeWidht);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        paintProgress = new Paint();
        paintProgress.setColor(0xff0bbc9b);
        paintProgress.setStrokeWidth(strokeWidht);
        paintProgress.setAntiAlias(true);
        paintProgress.setStyle(Paint.Style.STROKE);

        paintDot = new Paint();
        paintDot.setColor(0xff0bbc9b);
        paintDot.setAntiAlias(true);
        paintDot.setStyle(Paint.Style.FILL);

        anim3Start = getAnimation2(1);
        anim4Start = getAnimation3(1);
        anim5Start = getAnimation4(1);


    }

    Runnable refreshR = new Runnable() {

        @Override
        public void run() {
            invalidate();
            if (isLoading) {
                postDelayed(refreshR, timePerFrame);
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long currTime = System.currentTimeMillis();// 当前时间
        long time = currTime - time0;// 已经过去的时间
        if (time > totolTime)// 如果时间已经超过了整个动画的时间，则重置
        {
            time0 = currTime;
            time = 0;
        }

        long timeTap = 0;// 当前判断的阀值(判断过程中会累加)
        if (time < (timeTap += time1)) {// 动画1
            timePercentInCurrAnim = 1 - (float) (timeTap - time) / time1;
            dotPointNow = getAnimation1(timePercentToRealPercent1(timePercentInCurrAnim));
        } else if (time < (timeTap += time2)) {// 动画2
            timePercentInCurrAnim = 1 - (float) (timeTap - time) / time2;
            dotPointNow = getAnimation2(timePercentToRealPercent2(timePercentInCurrAnim));
        } else if (time < (timeTap += time3)) {// 动画3
            timePercentInCurrAnim = 1 - (float) (timeTap - time) / time3;
            dotPointNow = getAnimation3(timePercentInCurrAnim);
        } else if (time < (timeTap += time4)) {// 动画4
            timePercentInCurrAnim = 1 - (float) (timeTap - time) / time4;
            dotPointNow = getAnimation4(timePercentInCurrAnim);
        } else if (time < (timeTap += time5)) {// 动画5
            timePercentInCurrAnim = 1 - (float) (timeTap - time) / time5;
            dotPointNow = getAnimation5(timePercentInCurrAnim);
        } else if (time < (timeTap = totolTime)) {// 动画6
            timePercentInCurrAnim = 1 - (float) (timeTap - time) / time6;
            dotPointNow = getAnimation6(timePercentInCurrAnim);
        }

        canvas.drawCircle(x0, y0, radiusStroke, paint);
        canvas.drawArc(progressRect, 90, 360 * time / totolTime, false, paintProgress);

        canvas.drawCircle(dotPointNow.x, dotPointNow.y, dotRadius, paintDot);
    }

    private Point getAnimation1(float percent) {
        double angle = (0.125 - 0.75 * percent) * Math.PI;
        return circleToXY(mRadius, angle);
    }

    private Point getAnimation2(float percent) {
        double angle = (-0.625 + 1.375 * percent) * Math.PI;
        return circleToXY(mRadius, angle);
    }

    private Point getAnimation3(float percent) {
        int xMove = (int) ((anim3Start.x - x0) * percent);
        int x = anim3Start.x - xMove;
        int y = (int) (anim3Start.y - Math.sin(Math.PI * percent)
                * waveHeightAnim3);
        return new Point(x, y);
    }

    private Point getAnimation4(float percent) {
        int y = (int) (anim4Start.y + (y0 + mRadius - anim4Start.y) * Math.sin(Math.PI * percent * 0.5));
        return new Point(x0, y);
    }

    private Point getAnimation5(float percent) {
        int y = (int) (anim5Start.y - Math.sin(Math.PI * percent) * 0.5
                * mRadius);
        return new Point(x0, y);
    }

    private Point getAnimation6(float percent) {
        int y = (int) (anim5Start.y - Math.sin(Math.PI * percent) * 0.2
                * mRadius);
        return new Point(x0, y);
    }

    /**
     * 根据加速度公式，把时间进度转换成实际进度
     */
    private float timePercentToRealPercent1(float timePercent) {// 匀减速运动 s = v0t+0.5at^2(v0 =4;a=-8;t=0.5*timePercent)
        float t = (float) (timePercent * 0.5);
        float res = (float) (4 * t + 0.5 * -8f * t * t);
        return res;
    }

    /**
     * 根据加速度公式，把时间进度转换成实际进度
     */
    private float timePercentToRealPercent2(float timePercent) {// 先匀加速，后匀减速 s = v0t+0.5at^2(v0 =4;a=-8;t=0.5*timePercent)
        if (timePercent <= 0.5f) {// 先匀加速(v0 =0.6667;a=1.3333;t=0.5*timePercent*2)
            float t1 = (float) (timePercent);
            float res = (float) (0.6667 * t1 + 0.5 * 1.3333 * t1 * t1);
            return res;
        } else {// 后匀减速
            float t2 = (float) (timePercent - 0.5);
            float res2 = 0.5f + (float) (4 * t2 + 0.5 * -8f * t2 * t2) / 2;
            return res2;
        }
    }

    /**
     * 半径角度转坐标
     *
     * @param radius 半径
     * @param angle  角度，为弧度单位(pi)
     * @return 这个点的坐标
     */
    private Point circleToXY(int radius, double angle) {
        int x1 = (int) (x0 + radius * Math.sin(angle) + 0.5f);// 四舍五入
        int y1 = (int) (y0 + radius * Math.cos(angle) + 0.5f);// 四舍五入
        return new Point(x1, y1);
    }

    private float pointToAngle(Point point) {
        int x = point.x - x0;
        int y = point.y - y0;
        float angle = (float) Math.atan((double) x / y);
        return angle;
    }

}
