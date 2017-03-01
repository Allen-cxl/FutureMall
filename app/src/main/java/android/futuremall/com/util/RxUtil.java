package android.futuremall.com.util;


import android.futuremall.com.http.ApiException;
import android.futuremall.com.http.MyHttpResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
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
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
        return new ObservableTransformer<MyHttpResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<MyHttpResponse<T>> upstream) {

                return upstream.flatMap(new Function<MyHttpResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(MyHttpResponse<T> tMyHttpResponse) throws Exception {
                        if(tMyHttpResponse.getCode() == 200) {
                            return createData(tMyHttpResponse.getData());
                        } else {
                            return Observable.error(new ApiException(tMyHttpResponse.getMessage()));
                        }
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
    public static <T> Observable<T> createData(final T t) {
        return  Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(t);
            }
        });
    }
}
