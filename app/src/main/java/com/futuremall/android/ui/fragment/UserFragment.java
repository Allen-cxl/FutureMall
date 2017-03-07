package com.futuremall.android.ui.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.UserContract;
import com.futuremall.android.presenter.UserPresenter;
import com.futuremall.android.ui.activity.OrderCenterActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PVer on 2017/3/2.
 */

public class UserFragment extends BaseFragment<UserPresenter> implements UserContract.View {


    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_userInfo)
    TextView mTvUserInfo;
    @BindView(R.id.tv_back_integral)
    TextView mTvBackIntegral;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    @BindView(R.id.tv_generalize_money)
    TextView mTvGeneralizeMoney;
    @BindView(R.id.tv_generalize_give)
    TextView mTvGeneralizeGive;
    @BindView(R.id.tv_transfer)
    TextView mTvTransfer;
    @BindView(R.id.tv_payment)
    TextView mTvPayment;
    @BindView(R.id.tv_order)
    TextView mTvOrder;
    @BindView(R.id.tv_record)
    TextView mTvRecord;
    @BindView(R.id.tv_invite_register)
    TextView mTvInviteRegister;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;
    @BindView(R.id.tv_setup)
    TextView mTvSetup;
    @BindView(R.id.tv_about)
    TextView mTvAbout;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showContent() {

    }

    @OnClick({R.id.tv_userInfo, R.id.tv_transfer, R.id.tv_payment, R.id.tv_order, R.id.tv_record, R.id.tv_invite_register, R.id.tv_recharge, R.id.tv_setup, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_userInfo:
                break;
            case R.id.tv_transfer:
                break;
            case R.id.tv_payment:
                break;
            case R.id.tv_order:
                OrderCenterActivity.enter(getContext());
                break;
            case R.id.tv_record:
                break;
            case R.id.tv_invite_register:
                break;
            case R.id.tv_recharge:
                break;
            case R.id.tv_setup:
                break;
            case R.id.tv_about:
                break;
        }
    }
}
