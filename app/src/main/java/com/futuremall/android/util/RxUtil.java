package com.futuremall.android.util;


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
                    public Flowable<T> apply(MyHttpResponse<T> tMyHttpResponse) throws Exception {
                        if(tMyHttpResponse.getCode() == 0) {
                            return createData(tMyHttpResponse.getData(), "服务器正常");
                        } else {
                            return Flowable.error(new ApiException("服务器异常"));
                        }
                    }

                });

            }
        };
    }

    /**
     * 生成Observable
     * @param <T>
     * @param srvMsg
     * @return
     */
    public static <T> Flowable<T> createData(final T t, final String srvMsg) {
        return  Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) {
                e.onNext(t);
            }
        },BackpressureStrategy.BUFFER);
    }
}
