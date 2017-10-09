package com.wdg.wchat.bean.event;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */

public class FinshSplashEvent extends BaseEvent{
    public FinshSplashEvent(final String msg, final boolean result) {
        super(msg, result);
    }
}
