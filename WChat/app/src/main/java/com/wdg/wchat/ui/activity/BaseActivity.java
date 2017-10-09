package com.wdg.wchat.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wdg.wchat.utils.ProgressDialogUtil;

/**
 * Created by ${吴登赶} on 2017/8/27.
 */

public class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }
    public ProgressDialog showProgress(){
        return ProgressDialogUtil.show(this);
    }
}
