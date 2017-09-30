package com.wdg.wchat.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;


/**
 * Created by ${吴登赶} on 2017/2/26.
 */

public class ProgressDialogUtil {
    private static ProgressDialog mProgressDialog;

    public void setFinishCalls(IFinishCalls mFinishCalls) {
        this.mFinishCalls = mFinishCalls;
    }

    private static IFinishCalls mFinishCalls;

    public interface  IFinishCalls{
        void finshCall();
    }

    public static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (mProgressDialog != null){
                mProgressDialog.cancel();
            }
            if (mFinishCalls!=null){
                mFinishCalls.finshCall();
            }
            mFinishCalls = null;
        }
    };

    public static long mDefeatTime = 1000;

    /**
     * 创建progress
     * @param context 必须是Activity
     * @return
     */
    public static ProgressDialog show(Activity context) {
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("Context 必须是Activity");
        }
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mProgressDialog = progressDialog;
        return progressDialog;
    }

    /**
     * 不设置延迟时间直接取消
     *
     */
    public static void cancel() {
        mHandler.sendEmptyMessage(0x111);
    }

    /**
     * 设置取消progress延迟时间
     * @param time 延迟时间
     * @param
     */
    public static void cancelDelay(int time){
   if (time <0){

   }
        time = 0;
        mHandler.sendEmptyMessageAtTime(0x111,time);
    }

    /**
     * 取消 progress默认延迟
     * @param
     */
    public static  void cancelDefeatDelay(){
        mHandler.sendEmptyMessageDelayed(0x111,mDefeatTime);
    }

    /**
     *
     * @param IFinishCall progress 结束回调
     */
    public static  void cancelDelayCallBack(final IFinishCalls IFinishCall){
        mFinishCalls = IFinishCall;
        mHandler.sendEmptyMessageDelayed(0x111,mDefeatTime);
    }
    public static long getDefeatTime() {
        return mDefeatTime;
    }

    public static void setDefeatTime(long mDefeatTime) {
        ProgressDialogUtil.mDefeatTime = mDefeatTime;
    }
}
