package com.wdg.wchat.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobSDK;
import com.wdg.wchat.utils.GlideImageLoader;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */

public class MyAPP extends Application {
    private static MyAPP sApp;



    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        setSingleImagePicker();
        //初始化 图片选择器
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置图片加载器
        imagePicker.setImageLoader(new GlideImageLoader());
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        MobSDK.init(getApplicationContext(), "210abc1ae1e65", "c4d57dfdb1660ef890a75d7b2a6985b9");
    }

    public static MyAPP getApp() {
        return sApp;
    }
    /**
     * 设置图片单选
     */
    public void setSingleImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置单选
        imagePicker.setMultiMode(false);
        //允许裁剪（单选才有效）
        imagePicker.setCrop(true);
        //是否按矩形区域保存
        imagePicker.setSaveRectangle(false);
        //裁剪框的形状
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        //imagePicker.setFocusWidth(800);
        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        //imagePicker.setFocusHeight(800);
        //保存文件的宽度。单位像素
        imagePicker.setOutPutX(200);
        //保存文件的高度。单位像素
        imagePicker.setOutPutY(200);
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

