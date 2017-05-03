package com.futuremall.android.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;
import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.HotKeyWord;
import com.futuremall.android.presenter.Contract.SearchContract;
import com.futuremall.android.presenter.SearchPresenter;
import com.futuremall.android.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View{


    @BindView(R.id.et_txt)
    EditText mEtTxt;
    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.ll_label)
    LinearLayout mLlLabel;
    @BindView(R.id.hot_label)
    AutoLabelUI mHotLabel;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, null, true);
        mPresenter.hotLabel();
        mHotLabel.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(View view) {
                mEtTxt.setText(((Label) view).getText());
            }
        });
    }

    @OnClick(R.id.iv_search)
    public void onClick() {
        String searchTxt = mEtTxt.getText().toString();
        if(!StringUtil.isEmpty(searchTxt)){
            String url = Constants.SEARCH_URL + searchTxt;
            MallH5Activity.enter(this, url);
        }
    }

    @Override
    public void showContent(List<HotKeyWord> tags) {

        mLlLabel.setVisibility(View.VISIBLE);
        for(HotKeyWord tag: tags){
            mHotLabel.addLabel(tag.getKeyword());
        }
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
