package com.wdg.wchat.mvp.contract;

import com.wdg.wchat.bean.bean.RegisterInfoBean;

/**
 * Created by ${wdgan} on 2017/9/28 0028.
 * 邮箱18149542718@163
 */
public interface RegisterContract {
    interface Model {
    }

    interface View  extends BaseContract.View{
        String getPhone();
        String getPassword();
        String getNick();
        String getCounty();
        String getHeadPhoto();
        void toVerCodeRegister(RegisterInfoBean bean);
    }

    interface Presenter {
        //获取验证码
       String getVerCode(String phone);
        void checkRegisterInfo();
    }
}
