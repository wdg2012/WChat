package com.wdg.wchat.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */
public interface FirstLoginContract {
    interface Model extends AlreadyLoginContract.Model {
    }

    interface View extends BaseContract.View {
        TextView getAccountView();
        TextView getVerCodeView();
        void showPasswordLoginLayout();
        void showSmsLoginLayout();
        TextView getPasswordView();
        void toMainActivity();

    }

    interface Presenter {
        void passwordLogin();
        void SmsLogin();
        void getSms();
        void  login();
        void switchLogin();
    }
}
