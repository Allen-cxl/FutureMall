package com.futuremall.android.util;


import com.futuremall.android.app.Constants;
import com.futuremall.android.http.ApiException;
import com.futuremall.android.http.MyHttpResponse;


import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> upstream) {
                return upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
        return new FlowableTransformer<MyHttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<MyHttpResponse<T>> upstream) {
                return upstream.flatMap(new Function<MyHttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(MyHttpResponse<T> tMyHttpResponse){

                        return createData(tMyHttpResponse.getData(), tMyHttpResponse.getStatus(), tMyHttpResponse.getInfo());
                    }
                });

            }
        };
    }

    /**
     * 生成Observable
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createData(final T t, final  int srvCode, final String srvMsg) {
        return  Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) {
                if(srvCode == Constants.SERVER_SUCCESS){
                    e.onNext(t);
                }

                if(!StringUtil.isEmpty(srvMsg)){
                    e.onError(new ApiException(getSrvMsg(srvCode)));
                }
                if(srvCode != Constants.SERVER_SUCCESS){
                    e.onError(new ApiException(getSrvMsg(srvCode)));
                }
            }
        },BackpressureStrategy.BUFFER);
    }

    private static String getSrvMsg(int srvCode){

        if(srvCode == Constants.SERVER_BUSY){
            return Constants.SERVER_BUSY_MSG;
        }else if(srvCode == Constants.SERVER_FAIL){
            return Constants.SERVER_FAIL_MSG;
        }else if(srvCode == Constants.SERVER_TOKEN_FAIL){
            return Constants.SERVER_TOKEN_FAIL_MSG;
        }else if(srvCode == Constants.SERVER_SIGN_FAIL){
            return Constants.SERVER_SIGN_FAIL_MSG;
        }else if(srvCode == Constants.SERVER_PARAMETER_LOST){
            return Constants.SERVER_PARAMETER_LOST_MSG;
        }else if(srvCode == Constants.SERVER_NO_PERMISSIONS){
            return Constants.SERVER_NO_PERMISSIONS_MSG;
        }else if(srvCode == Constants.SERVER_ACOUNT_PASSWORD_ERROR){
            return Constants.SERVER_ACOUNT_PASSWORD_ERROR_MSG;
        }else if(srvCode == Constants.SERVER_ACOUNT_PASSWORD_FREEZE){
            return Constants.SERVER_ACOUNT_PASSWORD_FREEZE_MSG;
        }
        return null;
    }
}
