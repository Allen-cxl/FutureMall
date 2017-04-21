package com.futuremall.android.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.AliPayResultBean;
import com.futuremall.android.model.bean.PaykeysBean;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.RechargeContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.OrderInfoUtil2_0;
import com.futuremall.android.util.RxUtil;

import org.reactivestreams.Publisher;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Allen on 2017/4/20.
 */

public class RechargePresenter extends RxPresenter<RechargeContract.View> implements RechargeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    RechargePresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }

    @Override
    public void recharge(String amount) {

        LoadingStateUtil.show(mContext);
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.recharge(token, amount)
                .compose(RxUtil.<MyHttpResponse<PaykeysBean>>rxSchedulerHelper())
                .compose(RxUtil.<PaykeysBean>handleMyResult())
                .subscribe(new Consumer<PaykeysBean>() {
                    @Override
                    public void accept(PaykeysBean value) {
                        LoadingStateUtil.close();
                        if (null != value) {
                            aliPay(value);
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void checkPay(String outTradeNo) {

        String token = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.checkPay(token, outTradeNo)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        mView.rechargeSuccess();
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onErrorMsg(String msg) {
                        mView.rechargeFail(msg);
                    }
                });
        addSubscrebe(rxSubscription);

    }

    private void aliPay(final PaykeysBean paykeysBean) {

        Flowable.just(paykeysBean)
                .flatMap(new Function<PaykeysBean, Publisher<AliPayResultBean>>() {
                    @Override
                    public Publisher<AliPayResultBean> apply(final PaykeysBean bean) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<AliPayResultBean>() {
                            @Override
                            public void subscribe(FlowableEmitter<AliPayResultBean> e) throws Exception {

                                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(bean.getAppid(),
                                        bean.getTotal_amount(),
                                        bean.getSubject(),
                                        bean.getBody(),
                                        bean.getOut_trade_no(),
                                        bean.getNotify_url());
                                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                                String sign = OrderInfoUtil2_0.getSign(params, bean.getPrivateKey(),true);
                                final String orderInfo = orderParam + "&" + sign;

                                PayTask payTask = new PayTask(mContext);
                                Map<String, String> result = payTask.payV2(orderInfo, true);
                                AliPayResultBean payResult = new AliPayResultBean(result);
                                e.onNext(payResult);
                                e.onComplete();
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AliPayResultBean>() {
                    @Override
                    public void accept(AliPayResultBean bean) {

                        String resultStatus = bean.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, Constants.RESULT_STATUS_SUCCESS)) {

                            checkPay(paykeysBean.getOut_trade_no());
                        } else {
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, Constants.RESULT_STATUS_LOADING)) {

                                mView.showViewMsg("支付结果确认中，请前往个人中心查看订单");
                            } else if (TextUtils.equals(resultStatus, Constants.RESULT_NOT_INSTALLED)) {
                                mView.showViewMsg("你还未安装支付宝，请先安装支付宝");
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                mView.showViewMsg("支付失败，请重试");
                            }
                        }
                    }
                });

    }
}
