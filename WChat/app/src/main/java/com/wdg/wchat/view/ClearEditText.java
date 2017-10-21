package com.wdg.wchat.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wdg.wchat.R;


/**
 * 带清除按钮的编辑框
 * Created by HuangBin on 2017/10/20.
 */
public class ClearEditText extends AppCompatEditText
        implements View.OnFocusChangeListener {

    private Drawable clearDrawable;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //获取EditText的DrawableRight, 假如没有设置我们就使用默认的图片
        clearDrawable = getCompoundDrawables()[2];
        if (clearDrawable == null) {
            clearDrawable = getResources().getDrawable(R.drawable.search_delete);
        }
        clearDrawable.setBounds(0, 0,
                clearDrawable.getIntrinsicWidth(),
                clearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearDrawableVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间 我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (clearDrawable != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            setClearDrawableVisible(getText().length() > 0);
        } else {
            setClearDrawableVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    public void setClearDrawableVisible(boolean visible) {
        Drawable right = visible ? clearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                right,
                getCompoundDrawables()[3]);
    }

}
