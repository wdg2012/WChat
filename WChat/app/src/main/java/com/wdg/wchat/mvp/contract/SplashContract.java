package com.wdg.wchat.mvp.contract;

import android.content.Context;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */
public interface SplashContract {
    interface Model {
         boolean existLogin(Context context);
    }

    interface View extends BaseContract.View{
        void showLoginAndRegisterLayout();
        void toFirstLoginActivity();
        void toMainActivity();
        void toAlreadyLoginActivity();
        void toRegisterActivity();
    }

    interface Presenter {
       void init();
        void showLoginAndRegisterLayout();
        void toMainActivity();
        void toAlreadyLoginActivity();

    }
}
