package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.event.InfoEvent;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.util.TimeUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyInfoActivity extends SimpleActivity {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.cb_man)
    CheckBox mCbMan;
    @BindView(R.id.cb_woman)
    CheckBox mCbWoman;
    @BindView(R.id.ll_checkBox)
    LinearLayout mLlCheckBox;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    int mType;

    @Override
    protected int getLayout() {
        return R.layout.activity_modify_info;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_save:

                String tvValue = mEtContent.getText().toString();
                String tvBirthday = mTvBirthday.getText().toString();

                if(mType == Constants.ACTIVITY_TEXT){

                    RxBus.getDefault().post(new InfoEvent(tvValue));
                }else if(mType == Constants.ACTIVITY_CHECKBOX){

                    if(mCbMan.isChecked()){
                        RxBus.getDefault().post(new InfoEvent(getString(R.string.man)));
                    }else if(mCbWoman.isChecked()){
                        RxBus.getDefault().post(new InfoEvent(getString(R.string.woman)));
                    }
                }else if(mType == Constants.ACTIVITY_PICKER){

                    RxBus.getDefault().post(new InfoEvent(tvBirthday));
                }

                finish();
                break;

        }
        return false;
    }

    @Override
    protected void initEventAndData() {

        mType = getIntent().getIntExtra(Constants.IT_TYPE, Constants.ACTIVITY_TEXT);
        String title = getIntent().getStringExtra(Constants.IT_TITLE);
        String value = getIntent().getStringExtra(Constants.IT_VALUE);

        setToolBar(mSuperToolbar, title, true);
        initView(mType, title, value);
    }

    private void initView(int type, String title, String value){

        switch (type){

            case Constants.ACTIVITY_TEXT:
                mEtContent.setVisibility(View.VISIBLE);
                if (StringUtil.isEmpty(value)) {
                    mEtContent.setHint(title);
                } else {
                    mEtContent.setText(value);
                }
                break;

            case Constants.ACTIVITY_CHECKBOX:
                mLlCheckBox.setVisibility(View.VISIBLE);
                if(value.equalsIgnoreCase(getString(R.string.man))){
                    mCbMan.setChecked(true);
                }else if(value.equalsIgnoreCase(getString(R.string.woman))){
                    mCbWoman.setChecked(false);
                }
                break;

            case Constants.ACTIVITY_PICKER:
                mTvBirthday.setVisibility(View.VISIBLE);
                mTvBirthday.setText(value);
                break;
        }

        mCbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbWoman.setChecked(!isChecked);
            }
        });
        mCbWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbMan.setChecked(!isChecked);
            }
        });
    }

    public static void enter(Context context, String title, String value, int type) {

        Intent intent = new Intent(context, ModifyInfoActivity.class);
        intent.putExtra(Constants.IT_TITLE, title);
        intent.putExtra(Constants.IT_VALUE, value);
        intent.putExtra(Constants.IT_TYPE, type);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_birthday)
    public void onClick() {

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvBirthday.setText(TimeUtils.getYearAndMonthAndDay(date));
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setSubmitColor(R.color.font_normal)//确定按钮文字颜色
                .setCancelColor(R.color.font_normal)//取消按钮文字颜色
                .setLabel("", "", "", "", "", "")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }
}
