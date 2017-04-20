package com.futuremall.android.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.AliPayResultBean;
import com.futuremall.android.presenter.Contract.RechargeContract;
import org.reactivestreams.Publisher;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public void recharge(String monney) {

    }

    @Override
    public void alipay(String phone) {


    }

    @Override
    public void onCheckPaySuccess(String orderID) {

    }

    private void aliPay(final String orderInfo) {

        Flowable.just(orderInfo)
                .flatMap(new Function<String, Publisher<AliPayResultBean>>() {
                    @Override
                    public Publisher<AliPayResultBean> apply(final String para) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<AliPayResultBean>() {
                            @Override
                            public void subscribe(FlowableEmitter<AliPayResultBean> e) throws Exception {

                                PayTask alipay = new PayTask(mContext);
                                Map<String, String> result = alipay.payV2(para, true);
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

                            onCheckPaySuccess();
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
