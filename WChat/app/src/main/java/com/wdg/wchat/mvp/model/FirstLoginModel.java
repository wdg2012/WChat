package com.wdg.wchat.mvp.model;

import com.wdg.wchat.apiService.UserService;
import com.wdg.wchat.bean.dto.LoginDto;
import com.wdg.wchat.mvp.contract.FirstLoginContract;
import com.wdg.wchat.utils.RetrofitUtils;

import rx.Observable;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */
public class FirstLoginModel implements FirstLoginContract.Model {
    /**
     * 密码登录
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    @Override
    public Observable<LoginDto> passwordOfLogin( String phone,String password) {
        UserService userService = RetrofitUtils.createService(UserService.class);
        return userService.passwordLogin(phone,password);
    }

    /**
     * 验证码登录
     * @param phone 手机号
     * @param verCode 验证码
     * @return
     */
    @Override
    public Observable<LoginDto> smsOfLogin( String phone,String verCode) {
        UserService userService = RetrofitUtils.createService(UserService.class);
        return userService.smsLogin(phone,verCode);
    }
}
