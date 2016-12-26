package com.android.lq.p2p.lili.view;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.android.lq.p2p.lili.listener.OnDoubleClickListener;


public class MyRadioGroup extends RadioGroup {
    private long lastClick;

    private int lastClickIndex = -1;

    OnDoubleClickListener doubleL;

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyRadioGroup(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MotionEvent.ACTION_UP == ev.getAction()) {
            long thisTime = System.currentTimeMillis();
            int thisClickIndex = getTouchItemIndex((int) ev.getX(),
                    (int) ev.getY());
            if (thisClickIndex >= 0) {
                if (thisClickIndex == lastClickIndex && thisTime - lastClick < 500) {
                    if (doubleL != null) {
                        doubleL.onDoubleClick(thisClickIndex);
                    }
                }
                lastClick = thisTime;
                lastClickIndex = thisClickIndex;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private int getTouchItemIndex(int x, int y) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = new Rect(child.getLeft(), child.getTop(),
                    child.getRight(), child.getBottom());
            if (rect.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    public void setOnDoubleClickListener(OnDoubleClickListener doubleL) {
        this.doubleL = doubleL;
    }
}
