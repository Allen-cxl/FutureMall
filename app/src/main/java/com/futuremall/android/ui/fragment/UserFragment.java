package com.futuremall.android.ui.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.presenter.Contract.UserContract;
import com.futuremall.android.presenter.UserPresenter;
import com.futuremall.android.ui.ViewHolder.LoginHelper;
import com.futuremall.android.ui.activity.InviteRegisterActivity;
import com.futuremall.android.ui.activity.LoginActivity;
import com.futuremall.android.ui.activity.OperationRecordActivity;
import com.futuremall.android.ui.activity.OrderCenterActivity;
import com.futuremall.android.ui.activity.PaymentActivity;
import com.futuremall.android.ui.activity.RechargeActivity;
import com.futuremall.android.ui.activity.SettingActivity;
import com.futuremall.android.ui.activity.TransferActivity;
import com.futuremall.android.ui.activity.UserInfoActivity;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.widget.GlideCircleTransform;

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
    @BindView(R.id.ll_login_register)
    LinearLayout mLlLoginRegister;
    @BindView(R.id.ll_userInfo)
    LinearLayout mLlUserInfo;
    UserInfo mUserInfo;

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

        mPresenter.showLayout();
    }

    @Override
    public void showRegisterLayout() {
        mLlLoginRegister.setVisibility(View.VISIBLE);
        mLlUserInfo.setVisibility(View.GONE);
        mTvBackIntegral.setText(null);
        mTvTotalMoney.setText(null);
        mTvGeneralizeMoney.setText(null);
        mTvGeneralizeGive.setText(null);
    }

    @Override
    public void showLoginLayout() {
        mLlLoginRegister.setVisibility(View.GONE);
        mLlUserInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserInfo(UserInfo info) {

        mUserInfo = info;
        Glide.with(mContext.getApplicationContext())
                .load(info.getUser_picture())
                .crossFade()
                .transform(new GlideCircleTransform(mContext.getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.default_user)
                .error(R.drawable.default_user)
                .into(mIvUserAvatar);
        mTvUserName.setText(info.getUser_name());
        mTvBalance.setText(String.format(getString(R.string.price), info.getUser_money()));
        mTvBackIntegral.setText(String.format(getString(R.string.price), info.getRebate()));
        mTvTotalMoney.setText(String.format(getString(R.string.price), info.getPay_points()));
        mTvGeneralizeMoney.setText(String.format(getString(R.string.price), info.getHighreward()));
        mTvGeneralizeGive.setText(String.format(getString(R.string.price), info.getPayin()));
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_userInfo, R.id.tv_transfer, R.id.tv_payment, R.id.tv_order, R.id.tv_record, R.id.tv_invite_register, R.id.tv_recharge, R.id.tv_setup, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_login:

                LoginActivity.enter(getContext());
                break;

            case R.id.tv_register:
                SnackbarUtil.show(mView, "注册h5界面");
                break;

            case R.id.tv_userInfo:

                UserInfoActivity.enter(getContext(), mUserInfo);
                break;
            case R.id.tv_transfer:

                if (LoginHelper.ensureLogin(getContext())) {
                    TransferActivity.enter(getContext());
                }
                break;
            case R.id.tv_payment:

                if (LoginHelper.ensureLogin(getContext())) {
                    PaymentActivity.enter(getContext());
                }
                break;
            case R.id.tv_order:

                if (LoginHelper.ensureLogin(getContext())) {
                    OrderCenterActivity.enter(getContext());
                }
                break;
            case R.id.tv_record:

                if (LoginHelper.ensureLogin(getContext())) {
                    OperationRecordActivity.enter(getContext());
                }
                break;
            case R.id.tv_invite_register:

                if (LoginHelper.ensureLogin(getContext())) {
                    InviteRegisterActivity.enter(getContext());
                }
                break;
            case R.id.tv_recharge:

                if (LoginHelper.ensureLogin(getContext())) {
                    RechargeActivity.enter(getContext());
                }
                break;
            case R.id.tv_setup:

                SettingActivity.enter(getContext());
                break;
            case R.id.tv_about:
                SnackbarUtil.show(mView, "暂无UI");
                break;
        }
    }
}
