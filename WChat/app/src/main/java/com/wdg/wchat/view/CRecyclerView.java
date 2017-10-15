package com.wdg.wchat.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * 回收视图
 * Created by HuangBin on 2017/10/15.
 */
public class CRecyclerView extends RecyclerView {

    private boolean isMove = false;
    private LinearLayoutManager layoutManager;
    private int position;

    public CRecyclerView(Context context) {
        this(context, null);
    }

    public CRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //在这里进行第二次滚动（最后的100米）
                if (layoutManager != null && isMove){
                    isMove = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = position - layoutManager.findFirstVisibleItemPosition();
                    if (n >= 0 && n < getChildCount()){
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = getChildAt(n).getTop();
                        //最后的移动
                        scrollBy(0, top);
                    }
                }
            }
        });
    }

    public void moveToPosition(int index) {
        if(getLayoutManager() instanceof LinearLayoutManager) {
            layoutManager = (LinearLayoutManager) getLayoutManager();
            //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
            int firstItem = layoutManager.findFirstVisibleItemPosition();
            int lastItem = layoutManager.findLastVisibleItemPosition();
            position = index;
            //然后区分情况
            if (position <= firstItem) {
                //当要置顶的项在当前显示的第一个项的前面时
                scrollToPosition(position);
            }
            else if (position <= lastItem) {
                //当要置顶的项已经在屏幕上显示时
                int top = getChildAt(position - firstItem).getTop();
                scrollBy(0, top);
            }
            else {
                //当要置顶的项在当前显示的最后一项的后面时
                scrollToPosition(position);
                //这里这个变量是用在RecyclerView滚动监听里面的
                isMove = true;
            }
        }
    }

}
