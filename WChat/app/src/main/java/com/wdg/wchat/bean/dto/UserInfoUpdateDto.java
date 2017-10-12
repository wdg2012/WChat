package com.wdg.wchat.bean.dto;

import java.util.List;

/**
 * Created by ${wdgan} on 2017/10/12 0012.
 * 邮箱18149542718@163
 */

public class UserInfoUpdateDto {

    /**
     * code : 101
     * error : 头像更新成功
     * list : []
     * obj : {}
     */

    private String code;
    private String error;
    private ObjBean obj;
    private List<?> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public static class ObjBean {
    }
}
