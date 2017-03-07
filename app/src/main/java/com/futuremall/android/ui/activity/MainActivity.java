package com.futuremall.android.ui.activity;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.presenter.MainPresenter;
import com.futuremall.android.ui.fragment.FutureAddFragment;
import com.futuremall.android.ui.fragment.MainFragment;
import com.futuremall.android.ui.fragment.ShoppingCartFragment;
import com.futuremall.android.ui.fragment.TypeFragment;
import com.futuremall.android.ui.fragment.UserFragment;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.SnackbarUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {


    @BindView(R.id.radio_home)
    RadioButton mRadioHome;
    @BindView(R.id.radio_type)
    RadioButton mRadioType;
    @BindView(R.id.radio_future_add)
    RadioButton mRadioFutureAdd;
    @BindView(R.id.radio_shopping_car)
    RadioButton mRadioShoppingCar;
    @BindView(R.id.radio_user)
    RadioButton mRadioUser;

    @Inject
    MainFragment mMainFragment;
    @Inject
    TypeFragment mTypeFragment;
    @Inject
    FutureAddFragment mFutureAddFragment;
    @Inject
    ShoppingCartFragment mShoppingCartFragment;
    @Inject
    UserFragment mUserFragment;

    private int mPositionLast;
    private Fragment mFragmentNow;
    private List<Fragment> fragmentList;
    private FragmentManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        mPresenter.checkVersion();
        mRadioHome.setOnClickListener(this);
        mRadioType.setOnClickListener(this);
        mRadioFutureAdd.setOnClickListener(this);
        mRadioShoppingCar.setOnClickListener(this);
        mRadioUser.setOnClickListener(this);

        fragmentList = new ArrayList<>();
        mManager = getSupportFragmentManager();
        addFragment();
        mRadioHome.performClick();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.show(getWindow().getDecorView(), msg);
    }

    @Override
    public void showUpdateDialog(String versionContent) {
        LogUtil.i(versionContent);
    }

    private void addFragment() {
        fragmentList.add(mMainFragment);
        fragmentList.add(mTypeFragment);
        fragmentList.add(mFutureAddFragment);
        fragmentList.add(mShoppingCartFragment);
        fragmentList.add(mUserFragment);
    }

    private void switchContent(Fragment from, Fragment to, int index) {

        if (mFragmentNow != to) {
            FragmentTransaction transaction = mManager.beginTransaction();
            Fragment indexFragment = mManager.findFragmentByTag(String.valueOf(index));

            if (null == indexFragment) {    // 先判断是否被add过
                indexFragment = getCurrentFragmentByTag(index);
                transaction.hide(from).add(R.id.fragment_container, indexFragment, String.valueOf(index)).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                fragmentList.remove(index);
                fragmentList.add(index, indexFragment);
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
            mFragmentNow = to;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_home:
                int positionNow = Constants.RB_HOME;
                switchContent(fragmentList.get(mPositionLast), fragmentList.get(positionNow), positionNow);
                mPositionLast = positionNow;
                setRadioButtonCheck(mPositionLast);
                break;

            case R.id.radio_type:
                positionNow = Constants.RB_TYPE;
                switchContent(fragmentList.get(mPositionLast), fragmentList.get(positionNow), positionNow);
                mPositionLast = positionNow;
                setRadioButtonCheck(mPositionLast);
                break;

            case R.id.radio_future_add:
                positionNow = Constants.RB_FUTURE_ADD;
                switchContent(fragmentList.get(mPositionLast), fragmentList.get(positionNow), positionNow);
                mPositionLast = positionNow;
                setRadioButtonCheck(mPositionLast);
                break;

            case R.id.radio_shopping_car:
                positionNow = Constants.RB_SHOPPING_CAR;
                switchContent(fragmentList.get(mPositionLast), fragmentList.get(positionNow), positionNow);
                mPositionLast = positionNow;
                setRadioButtonCheck(mPositionLast);
                break;

            case R.id.radio_user:
                positionNow = Constants.RB_USER;
                switchContent(fragmentList.get(mPositionLast), fragmentList.get(positionNow), positionNow);
                mPositionLast = positionNow;
                setRadioButtonCheck(mPositionLast);
                break;
        }
    }

    private void setRadioButtonCheck(int position) {

        switch (position) {
            case Constants.RB_HOME:

                mRadioHome.setSelected(true);
                mRadioType.setSelected(false);
                mRadioFutureAdd.setSelected(false);
                mRadioShoppingCar.setSelected(false);
                mRadioUser.setSelected(false);
                break;

            case Constants.RB_TYPE:

                mRadioHome.setSelected(false);
                mRadioType.setSelected(true);
                mRadioFutureAdd.setSelected(false);
                mRadioShoppingCar.setSelected(false);
                mRadioUser.setSelected(false);
                break;

            case Constants.RB_FUTURE_ADD:

                mRadioHome.setSelected(false);
                mRadioType.setSelected(false);
                mRadioFutureAdd.setSelected(true);
                mRadioShoppingCar.setSelected(false);
                mRadioUser.setSelected(false);
                break;

            case Constants.RB_SHOPPING_CAR:

                mRadioHome.setSelected(false);
                mRadioType.setSelected(false);
                mRadioFutureAdd.setSelected(false);
                mRadioShoppingCar.setSelected(true);
                mRadioUser.setSelected(false);
                break;

            case Constants.RB_USER:

                mRadioHome.setSelected(false);
                mRadioType.setSelected(false);
                mRadioFutureAdd.setSelected(false);
                mRadioShoppingCar.setSelected(false);
                mRadioUser.setSelected(true);
                break;

            default:
                mRadioHome.setSelected(true);
                mRadioType.setSelected(false);
                mRadioFutureAdd.setSelected(false);
                mRadioShoppingCar.setSelected(false);
                mRadioUser.setSelected(false);
                break;

        }
    }

    private Fragment getCurrentFragmentByTag(int index) {
        switch (index) {
            case Constants.RB_HOME:
                return mMainFragment;

            case Constants.RB_TYPE:
                return mTypeFragment;

            case Constants.RB_FUTURE_ADD:
                return mFutureAddFragment;

            case Constants.RB_SHOPPING_CAR:
                return mShoppingCartFragment;

            case Constants.RB_USER:
                return mUserFragment;
            default:
                return null;
        }

    }

}
