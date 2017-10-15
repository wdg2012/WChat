package com.wdg.wchat.bean.bean;

import com.wdg.wchat.bean.dto.CountryCodeDto;

import java.util.List;
import java.util.Map;

/**
 * Created by HuangBin on 2017/10/15.
 */
public class CountryCodeBean {

    private List<CountryCodeDto> countryCodeDtoList;
    private Map<String, Integer> letterIndexMap;

    public List<CountryCodeDto> getCountryCodeDtoList() {
        return countryCodeDtoList;
    }

    public void setCountryCodeDtoList(List<CountryCodeDto> countryCodeDtoList) {
        this.countryCodeDtoList = countryCodeDtoList;
    }

    public Map<String, Integer> getLetterIndexMap() {
        return letterIndexMap;
    }

    public void setLetterIndexMap(Map<String, Integer> letterIndexMap) {
        this.letterIndexMap = letterIndexMap;
    }
}
