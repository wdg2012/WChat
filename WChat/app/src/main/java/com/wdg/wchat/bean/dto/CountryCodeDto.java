package com.wdg.wchat.bean.dto;

/**
 * 国家地区代码
 * Created by HuangBin on 2017/10/14.
 */
public class CountryCodeDto {


    /**
     * country_id : 100008
     * country_code : +355
     * country_name_en : Albania
     * country_name_cn : 阿尔巴尼亚
     * country_group : false
     * country_group_name : A
     * ab : AL
     */

    private int country_id;
    private String country_code;
    private String country_name_en;
    private String country_name_cn;
    private boolean country_group;
    private String country_group_name;
    private String ab;

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name_en() {
        return country_name_en;
    }

    public void setCountry_name_en(String country_name_en) {
        this.country_name_en = country_name_en;
    }

    public String getCountry_name_cn() {
        return country_name_cn;
    }

    public void setCountry_name_cn(String country_name_cn) {
        this.country_name_cn = country_name_cn;
    }

    public boolean isCountry_group() {
        return country_group;
    }

    public void setCountry_group(boolean country_group) {
        this.country_group = country_group;
    }

    public String getCountry_group_name() {
        return country_group_name;
    }

    public void setCountry_group_name(String country_group_name) {
        this.country_group_name = country_group_name;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

}
