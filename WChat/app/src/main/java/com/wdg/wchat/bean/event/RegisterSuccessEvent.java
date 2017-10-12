package com.wdg.wchat.bean.event;

/**
 * Created by ${wdgan} on 2017/10/12 0012.
 * 邮箱18149542718@163
 */

public class RegisterSuccessEvent extends BaseEvent {
    public RegisterSuccessEvent(final String msg, final boolean result) {
        super(msg, result);
    }
}
