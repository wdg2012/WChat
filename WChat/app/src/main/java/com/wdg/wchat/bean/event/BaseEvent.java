package com.wdg.wchat.bean.event;

/**
 * Created by ${wdgan} on 2017/10/9 0009.
 * 邮箱18149542718@163
 */

public class BaseEvent {
    private String msg;
    private boolean result;

    public BaseEvent(final String msg, final boolean result) {
        this.msg = msg;
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
