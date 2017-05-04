package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.ui.ViewHolder.LoginHelper;
import com.futuremall.android.ui.fragment.FutureAddFragment;
import com.futuremall.android.ui.fragment.MainFragment;
import com.futuremall.android.ui.fragment.ShoppingCartFragment;
import com.futuremall.android.ui.fragment.TypeFragment;
import com.futuremall.android.ui.fragment.UserFragment;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.TimeUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends SimpleActivity implements View.OnClickListener {

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

    private int mFromTag;
    private FragmentManager mManager;


    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        int type = getIntent().getIntExtra(Constants.IT_TYPE, Constants.RB_HOME);
        mRadioHome.setOnClickListener(this);
        mRadioType.setOnClickListener(this);
        mRadioFutureAdd.setOnClickListener(this);
        mRadioShoppingCar.setOnClickListener(this);
        mRadioUser.setOnClickListener(this);

        mManager = getSupportFragmentManager();
        setOnClickView(type);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra(Constants.IT_TYPE, Constants.RB_HOME);
        setOnClickView(type);
    }

    private void setRadioButtonOnClick(int fromTag, int currentTag) {
        switchContent(fromTag, currentTag, getCurrentFragmentByTag(currentTag));
        setRadioButtonCheck(currentTag);
        mFromTag = currentTag;
    }

    private void switchContent(int fromTag, int toTag, Fragment fragment) {

        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fromFragment = mManager.findFragmentByTag(String.valueOf(fromTag));
        Fragment toFragment = mManager.findFragmentByTag(String.valueOf(toTag));

        if (fromFragment != null) {
            transaction.hide(fromFragment);
        }
        if (toFragment == null) {
            transaction.add(R.id.fragment_container, fragment, String.valueOf(toTag)).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.show(toFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_home:

                setRadioButtonOnClick(mFromTag, Constants.RB_HOME);
                break;

            case R.id.radio_type:

                setRadioButtonOnClick(mFromTag, Constants.RB_TYPE);
                break;

            case R.id.radio_future_add:
                setRadioButtonOnClick(mFromTag, Constants.RB_FUTURE_ADD);
                break;

            case R.id.radio_shopping_car:
                if(LoginHelper.ensureLogin(this)){
                    setRadioButtonOnClick(mFromTag, Constants.RB_SHOPPING_CART);
                }
                break;

            case R.id.radio_user:
                setRadioButtonOnClick(mFromTag, Constants.RB_USER);
                break;
        }
    }

    private void setRadioButtonCheck(int tag) {

        switch (tag) {
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

            case Constants.RB_SHOPPING_CART:

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

            case Constants.RB_SHOPPING_CART:
                return mShoppingCartFragment;

            case Constants.RB_USER:
                return mUserFragment;
            default:
                return null;
        }

    }

    private void setOnClickView(int index) {
        switch (index) {
            case Constants.RB_HOME:
                mRadioHome.performClick();
                break;

            case Constants.RB_TYPE:
                mRadioType.performClick();
                break;

            case Constants.RB_FUTURE_ADD:
                mRadioFutureAdd.performClick();
                break;

            case Constants.RB_SHOPPING_CART:
                mRadioShoppingCar.performClick();
                break;

            case Constants.RB_USER:
                mRadioUser.performClick();
                break;

        }

    }

    public static void enter(Context context, int type) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.IT_TYPE, type);
        context.startActivity(intent);
    }

}
