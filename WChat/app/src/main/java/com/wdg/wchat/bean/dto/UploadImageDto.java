package com.wdg.wchat.bean.dto;

/**
 * Created by ${wdgan} on 2017/10/10 0010.
 * 邮箱18149542718@163
 */

public class UploadImageDto {

    /**
     * code : success
     * data : {"width":5472,"height":3648,"filename":"500320830.jpg","storename":"59dc3318c8c47.jpg","size":3544751,"path":"/2017/10/10/59dc3318c8c47.jpg","hash":"sqJ8dGk42uO6nSj","timestamp":1507603224,"ip":"125.120.116.160","url":"https://i.loli.net/2017/10/10/59dc3318c8c47.jpg","delete":"https://sm.ms/delete/sqJ8dGk42uO6nSj"}
     */

    private String code;
    private DataBean data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        /**
         * width : 5472
         * height : 3648
         * filename : 500320830.jpg
         * storename : 59dc3318c8c47.jpg
         * size : 3544751
         * path : /2017/10/10/59dc3318c8c47.jpg
         * hash : sqJ8dGk42uO6nSj
         * timestamp : 1507603224
         * ip : 125.120.116.160
         * url : https://i.loli.net/2017/10/10/59dc3318c8c47.jpg
         * delete : https://sm.ms/delete/sqJ8dGk42uO6nSj
         */

        private int width;
        private int height;
        private String filename;
        private String storename;
        private int size;
        private String path;
        private String hash;
        private int timestamp;
        private String ip;
        private String url;
        private String delete;

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getFilename() {
            return filename;
        }

        public String getStorename() {
            return storename;
        }

        public int getSize() {
            return size;
        }

        public String getPath() {
            return path;
        }

        public String getHash() {
            return hash;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public String getIp() {
            return ip;
        }

        public String getUrl() {
            return url;
        }

        public String getDelete() {
            return delete;
        }
    }
}
