package com.futuremall.android.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.futuremall.android.app.Constants;
import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.event.InfoEvent;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.UserInfoContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.RxUtil;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class UserInfoPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    UserInfoPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
        registerEvent();
    }

    private void registerEvent() {
        Disposable rxSubscription = RxBus.getDefault().toObservable(InfoEvent.class)
                .compose(RxUtil.<InfoEvent>rxSchedulerHelper())
                .map(new Function<InfoEvent, String>() {
                    @Override
                    public String apply(InfoEvent InfoEvent) {
                        return InfoEvent.info;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String info) {
                        mView.setInfo(info);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void saveUserInfo(File file, int sex, String birthday, String realName) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.updateUser(accessToken, file, sex, birthday, realName)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LogUtil.d("UserInfo:" + value);
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void galleryPic() {

        gallery();
    }

    @Override
    public void cropPic(Uri uri) {

        crop(uri);
    }

    @Override
    public void getPicFile(Uri uri){

        String[] pro = { MediaStore.Images.Media.DATA };

        Cursor cursor = mContext.getContentResolver().query(uri,pro,null,null,null);
        if(cursor != null){
            int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(actual_image_column_index);
            File file = new File(img_path);
            mView.pic(file);
            cursor.close();
        }

    }

    private void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mContext.startActivityForResult(intent, Constants.PHOTO_REQUEST_GALLERY);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        mContext.startActivityForResult(intent, Constants.PHOTO_REQUEST_CUT);
    }
}
