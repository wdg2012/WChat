package com.wdg.wchat.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.wdg.wchat.mvp.contract.SplashContract;
import com.wdg.wchat.utils.SharedPreferencesUtils;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */
public class SplashModel implements SplashContract.Model {
    /**
     * 判断是否安装了
     *
     * @return
     */
    @Override
    public boolean existLogin(Context context) {
        boolean existLogin = false;
        String user_phone = (String) SharedPreferencesUtils.getParam(context,"user_phone","");
        if (!TextUtils.isEmpty(user_phone)){
            existLogin = true;
        }
        return existLogin;
    }
}
