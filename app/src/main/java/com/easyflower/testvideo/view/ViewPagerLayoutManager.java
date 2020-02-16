//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.easyflower.testvideo.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class ViewPagerLayoutManager extends LinearLayoutManager {
    private static final String TAG = "ViewPagerLayoutManager";
    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;
    private RecyclerView mRecyclerView;
    private int mDrift;
    private OnChildAttachStateChangeListener mChildAttachStateChangeListener = new OnChildAttachStateChangeListener() {
        public void onChildViewAttachedToWindow(View view) {
            if (ViewPagerLayoutManager.this.mOnViewPagerListener != null && ViewPagerLayoutManager.this.getChildCount() == 1) {
                ViewPagerLayoutManager.this.mOnViewPagerListener.onInitComplete(view);
            }

        }

        public void onChildViewDetachedFromWindow(View view) {
            if (ViewPagerLayoutManager.this.mDrift >= 0) {
                if (ViewPagerLayoutManager.this.mOnViewPagerListener != null) {
                    ViewPagerLayoutManager.this.mOnViewPagerListener.onPageRelease(true, ViewPagerLayoutManager.this.getPosition(view),view);
                }
            } else if (ViewPagerLayoutManager.this.mOnViewPagerListener != null) {
                ViewPagerLayoutManager.this.mOnViewPagerListener.onPageRelease(false, ViewPagerLayoutManager.this.getPosition(view),view);
            }

        }
    };

    public ViewPagerLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        this.init();
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.init();
    }

    private void init() {
        this.mPagerSnapHelper = new PagerSnapHelper();
    }


    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        this.mPagerSnapHelper.attachToRecyclerView(view);
        this.mRecyclerView = view;
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mChildAttachStateChangeListener);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        super.onLayoutChildren(recycler, state);
    }

    public void onScrollStateChanged(int state) {

//        SCROLL_STATE_IDLE（空闲）
//
//        SCROLL_STATE_DRAGGING（拖动）
//
//        SCROLL_STATE_SETTLING（要移动到最后位置时）
//
        switch(state) {
            case 0:
                View viewIdle = this.mPagerSnapHelper.findSnapView(this);
                if (viewIdle != null) {
                    int positionIdle = this.getPosition(viewIdle);
                    if (this.mOnViewPagerListener != null && this.getChildCount() == 1) {
                        this.mOnViewPagerListener.onPageSelected(positionIdle, positionIdle == this.getItemCount() - 1, viewIdle);
                    }
                }
                break;
            case 1:
                View viewDrag = this.mPagerSnapHelper.findSnapView(this);
                this.getPosition(viewDrag);
                break;
            case 2:
                View viewSettling = this.mPagerSnapHelper.findSnapView(this);
                this.getPosition(viewSettling);
        }

    }

    public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    public int scrollHorizontallyBy(int dx, Recycler recycler, State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    public void setOnViewPagerListener(OnViewPagerListener listener) {
        this.mOnViewPagerListener = listener;
    }
}
