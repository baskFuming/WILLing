package com.xxx.willing.view;


import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import java.lang.reflect.Field;
/**
 *  @desc   嵌套滑动冲突
 *  @author FM
 *  @date   2019-12-10
 */

public class FixAppBarLayout extends AppBarLayout.Behavior {
    private static final String TAG = "AppBarLayoutBehavior";
    private OverScroller mScroller;

    public FixAppBarLayout() {
        super();
    }

    public FixAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getParentScroller(context);
    }

    /**
     * 反射获得滑动属性。
     *
     * @param context
     */
    private void getParentScroller(Context context) {
        if (mScroller != null) return;
        mScroller = new OverScroller(context);
        try {
            Class<?> reflex_class = getClass().getSuperclass().getSuperclass();//父类AppBarLayout.Behavior  父类的父类   HeaderBehavior
            Field fieldScroller = reflex_class.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            fieldScroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        stopNestedScrollIfNeeded(dyUnconsumed, child, target, type);
    }


    /**
     * 重要代码，解决无法xrecycleview无法加载更多的问题
     * https://blog.csdn.net/xutaojxx/article/details/81481034
     * @param dy
     * @param child
     * @param target
     * @param type
     */
    private void stopNestedScrollIfNeeded(int dy, AppBarLayout child, View target, int type) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            final int currOffset = getTopAndBottomOffset();
            if ((dy < 0 && currOffset == 0) || (dy > 0 && currOffset == -child.getTotalScrollRange())) {
                ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH);
            }
        }
    }





    //fling上滑appbar然后迅速fling下滑recycler时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (mScroller != null) { //当recyclerView 做好滑动准备的时候 直接干掉Appbar的滑动
            if (mScroller.computeScrollOffset()) {
                mScroller.abortAnimation();
            }
        }
        if (type == ViewCompat.TYPE_NON_TOUCH && getTopAndBottomOffset() == 0) { //recyclerview 鸡儿的 惯性比较大 会顶在头部一会儿  到头直接干掉它的滑动
            ViewCompat.stopNestedScroll(target, type);
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        stopNestedScrollIfNeeded(dy, child, target, type);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent e) {

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
        }


        return super.onTouchEvent(parent, child, e);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (ev.getAction() == ev.ACTION_DOWN) {
            Object scroller = getSuperSuperField(this, "mScroller");
            if (scroller != null && scroller instanceof OverScroller) {
                OverScroller overScroller = (OverScroller) scroller;
                overScroller.abortAnimation();
            }
        }

        return super.onInterceptTouchEvent(parent, child, ev);
    }

    private Object getSuperSuperField(Object paramClass, String paramString) {
        Field field = null;
        Object object = null;
        try {
            field = paramClass.getClass().getSuperclass().getSuperclass().getDeclaredField(paramString);
            field.setAccessible(true);
            object = field.get(paramClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
