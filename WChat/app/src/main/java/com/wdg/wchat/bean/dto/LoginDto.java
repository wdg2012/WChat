package com.wdg.wchat.bean.dto;

import java.util.List;

/**
 * Created by ${wdgan} on 2017/9/29 0029.
 * 邮箱18149542718@163
 */

public class LoginDto {

    /**
     * code : 101
     * error : 登录成功
     * list : []
     * obj : {"head_path":"http://47.93.21.48:8080/ssm_war/uploadfile/1505053976238link.jpg","user_country":"中国","user_id":55,"user_nick":"doge","user_password":"wdg2112063888"}
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

    @Override
    public String toString() {
        return "LoginDto{" +
                "code='" + code + '\'' +
                ", error='" + error + '\'' +
                ", obj=" + obj +
                ", list=" + list +
                '}';
    }

    public static class ObjBean {
        /**
         * head_path : http://47.93.21.48:8080/ssm_war/uploadfile/1505053976238link.jpg
         * user_country : 中国
         * user_id : 55
         * user_nick : doge
         * user_password : wdg2112063888
         */

        private String head_path;
        private String user_country;
        private int user_id;
        private String user_nick;
        private String user_password;
        private String user_phone;

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(final String user_phone) {
            this.user_phone = user_phone;
        }

        public String getHead_path() {
            return head_path;
        }

        public void setHead_path(String head_path) {
            this.head_path = head_path;
        }

        public String getUser_country() {
            return user_country;
        }

        public void setUser_country(String user_country) {
            this.user_country = user_country;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_nick() {
            return user_nick;
        }

        public void setUser_nick(String user_nick) {
            this.user_nick = user_nick;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        @Override
        public String toString() {
            return "ObjBean{" +
                    "head_path='" + head_path + '\'' +
                    ", user_country='" + user_country + '\'' +
                    ", user_id=" + user_id +
                    ", user_nick='" + user_nick + '\'' +
                    ", user_password='" + user_password + '\'' +
                    '}';
        }
    }
}
