package com.wdg.wchat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.bean.bean.NetSubscriber;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 字母视图
 * Created by HuangBin on 2017/10/15.
 */
public class LetterView extends View {

    private final String TAG = LetterView.class.getSimpleName();

    private final String[] letters = {"↑", "☆", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private int choose = -1;
    private Paint paint;
    private boolean showBackground = false;
    private TextView letterToast;
    private Subscription subscription;
    private OnTouchLetterChangedListener changedListener;

    public LetterView(Context context) {
        this(context, null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFakeBoldText(true);
        //paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(30);
        paint.setColor(getResources().getColor(R.color.font_gray_2));
    }

    @Override
    protected void onDetachedFromWindow() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeViewImmediate(letterToast);
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        subscription = null;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(showBackground) {
            canvas.drawColor(getResources().getColor(R.color.color_black_t2));
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / letters.length;

        for (int i = 0; i < letters.length; i++) {
/*            paint.setColor(i == choose ?
                    getResources().getColor(R.color.font_gray_1)
                    : getResources().getColor(R.color.font_gray_1));*/
            float xPos = width / 2 - paint.measureText(letters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(letters[i], xPos, yPos, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchLetterChangedListener listener = changedListener;
        final int index = (int) (y / getHeight() * letters.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(action == MotionEvent.ACTION_DOWN){
                    showBackground = true;
                }
                if (oldChoose != index && listener != null) {
                    if (index >= 0 && index < letters.length) {
                        if(letterToast != null) {
                            letterToast.setText(letters[index]);
                            letterToast.setVisibility(View.VISIBLE);
                            delayCloseToast();
                        }
                        listener.onTouchLetterChanged(index == 0 ? letters[2] : letters[index]);
                        choose = index;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBackground = false;
                choose = -1;
                invalidate();
                break;
        }

        return true;
    }

    public void setLetterToast(int res){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if(letterToast != null){
            windowManager.removeViewImmediate(letterToast);
        }
        letterToast = (TextView) View.inflate(getContext(), res, null);
        letterToast.setVisibility(View.INVISIBLE);
        int width = (int) getResources().getDimension(R.dimen.toast_width);
        int height = (int) getResources().getDimension(R.dimen.toast_height);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                width, height,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager.addView(letterToast, lp);
    }

    public void delayCloseToast(){
        if(subscription != null && !subscription.isUnsubscribed()){
            //Log.d(TAG, "unsubscribe");
            subscription.unsubscribe();
        }
        subscription = Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<Long>(){

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        if(letterToast != null){
                            letterToast.setVisibility(View.INVISIBLE);
                        }
                    }

                });
    }

    public void setOnTouchLetterChangedListener(OnTouchLetterChangedListener listener) {
        changedListener = listener;
    }

    public interface OnTouchLetterChangedListener {
        public void onTouchLetterChanged(String letter);
    }

}
