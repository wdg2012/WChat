package com.wdg.wchat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdg.wchat.R;
import com.wdg.wchat.adapter.CountryCodeAdapter;
import com.wdg.wchat.bean.bean.CountryCodeBean;
import com.wdg.wchat.bean.dto.CountryCodeDto;
import com.wdg.wchat.mvp.contract.CountryCodeContract;
import com.wdg.wchat.mvp.presenter.CountryCodePresenter;
import com.wdg.wchat.view.CRecyclerView;
import com.wdg.wchat.view.ClearEditText;
import com.wdg.wchat.view.LetterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 国家地区选择
 * Created by HuangBin on 2017/10/14.
 */
public class CountryCodeActivity extends BaseActivity
        implements CountryCodeContract.View,
        CountryCodeAdapter.OnItemClickListener,
        LetterView.OnTouchLetterChangedListener {

    @BindView(R.id.ivBackBtn)
    ImageView ivBackBtn;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRightBtn)
    ImageView ivRightBtn;
    @BindView(R.id.rvCountryCode)
    CRecyclerView rvCountryCode;
    @BindView(R.id.letterView)
    LetterView letterView;
    @BindView(R.id.rlTitle)
    RelativeLayout rlTitle;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.cetSearch)
    ClearEditText cetSearch;

    private CountryCodeAdapter adapter;
    private List<CountryCodeDto> countryCodes;
    private Map<String, Integer> letterIndexs;
    private CountryCodePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        tvTitle.setText("选择国家和地区代码");
        countryCodes = new ArrayList<>();
        letterIndexs = new HashMap<>();
        adapter = new CountryCodeAdapter(this, countryCodes);
        adapter.setOnItemClickListener(this);
        rvCountryCode.setLayoutManager(new LinearLayoutManager(this));
        rvCountryCode.setAdapter(adapter);
        letterView.setLetterToast(R.layout.letter_toast);
        letterView.setOnTouchLetterChangedListener(this);
        presenter = new CountryCodePresenter(this);
        presenter.getCountryCodes();
    }

    @OnClick({R.id.ivBackBtn, R.id.ivRightBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackBtn:
                presenter.NoDataFinishActivity();
                break;
            case R.id.ivRightBtn:
                presenter.showSearchLayout();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        presenter.onItemClick(position);
    }

    @Override
    public void onTouchLetterChanged(String letter) {
        presenter.onTouchLetterChanged(letter);
    }

    @Override
    public void NoDataFinishActivity() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void finishActivity(String countryCode) {
        Intent data = new Intent().putExtra("countryCode", countryCode);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void moveToPosition(int position) {
        rvCountryCode.moveToPosition(position);
    }

    @Override
    public void showTitleLayout() {
        rlTitle.setVisibility(View.VISIBLE);
        rlSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSearchLayout() {
        rlTitle.setVisibility(View.INVISIBLE);
        rlSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public List<CountryCodeDto> getCountryCodeDtoList() {
        return countryCodes;
    }

    @Override
    public Map<String, Integer> getLetterMap() {
        return letterIndexs;
    }

    @Override
    public CountryCodeAdapter getCountryCodeAdapter() {
        return adapter;
    }

    @Override
    public RelativeLayout getTitleLayout() {
        return rlTitle;
    }

    @Override
    public ClearEditText getClearEditText() {
        return cetSearch;
    }

}
