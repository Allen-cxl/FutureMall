package com.futuremall.android.di.component;

import android.app.Activity;
import com.futuremall.android.di.module.ActivityModule;
import com.futuremall.android.di.scope.ActivityScope;
import com.futuremall.android.ui.activity.ForgetPasswordActivity;
import com.futuremall.android.ui.activity.InviteRegisterActivity;
import com.futuremall.android.ui.activity.LoginActivity;
import com.futuremall.android.ui.activity.MainActivity;
import com.futuremall.android.ui.activity.MallH5Activity;
import com.futuremall.android.ui.activity.OperationRecordActivity;
import com.futuremall.android.ui.activity.OrderDetailActivity;
import com.futuremall.android.ui.activity.PayOrderActivity;
import com.futuremall.android.ui.activity.PaymentActivity;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.ReceivableQrCodeActivity;
import com.futuremall.android.ui.activity.RechargeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.futuremall.android.ui.activity.TransferActivity;
import com.futuremall.android.ui.activity.UpdateLoginPasswordActivity;
import com.futuremall.android.ui.activity.UpdatePayPasswordActivity;
import com.futuremall.android.ui.activity.UserInfoActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(QrCodeActivity qrCodeActivity);

    void inject(InviteRegisterActivity inviteRegisterActivity);

    void inject(ReceivableQrCodeActivity receivableQrCodeActivity);

    void inject(SearchActivity searchActivity);

    void inject(OrderDetailActivity orderDetailActivity);

    void inject(OperationRecordActivity operationRecordActivity);

    void inject(LoginActivity operationRecordActivity);

    void inject(TransferActivity transferActivity);

    void inject(PaymentActivity payActivity);

    void inject(UpdatePayPasswordActivity updatePayPasswordActivity);

    void inject(UpdateLoginPasswordActivity updateLoginPasswordActivity);

    void inject(ForgetPasswordActivity forgetPasswordActivity);

    void inject(UserInfoActivity userInfoActivity);

    void inject(PayOrderActivity payOrderActivity);

    void inject(RechargeActivity rechargeActivity);

    void inject(MallH5Activity mallH5Activity);

}
