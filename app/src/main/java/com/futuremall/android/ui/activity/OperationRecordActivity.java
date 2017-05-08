package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.OperationRecordBean;
import com.futuremall.android.presenter.Contract.OperationRecordContract;
import com.futuremall.android.presenter.OperationRecordPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.OperationRecordAdapter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.TimeUtils;
import com.futuremall.android.widget.LoadingLayout;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OperationRecordActivity extends BaseActivity<OperationRecordPresenter> implements OperationRecordContract.View,TimePickerView.OnTimeSelectListener{

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.swipeRefreshLayout)
    SHSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    OperationRecordAdapter mAdapter;
    int p = 1, num = 15;
    long time;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_operation_record;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_calender:
                if(time <= 0){
                    time = TimeUtils.dataLong();
                }
                TimePickerView pvTime = new TimePickerView.Builder(this, this)
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                        .setSubmitColor(R.color.font_normal)//确定按钮文字颜色
                        .setCancelColor(R.color.font_normal)//取消按钮文字颜色
                        .setLabel("", "", "", "", "", "")
                        .build();
                Calendar date = Calendar.getInstance();
                date.set(TimeUtils.getYear(time*1000),TimeUtils.getMonth(time*1000)-1,TimeUtils.getDay(time*1000));
                pvTime.setDate(date);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;

        }
        return false;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.record), true);
        mLoadingLayout = loadingLayout;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OperationRecordAdapter(this);
        mRecycleView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);


        mPresenter.recordList(1, 15, time+"", true);
        mLoadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.recordList(1, 15, time+"", true);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.recordList(1, 15, time+"", false);
                    }
                }, 1000);
            }

            @Override
            public void onLoading() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.recordList((p++), num, time+"",  false);
                        mSwipeRefreshLayout.finishLoadmore();
                    }
                }, 1600);
            }


            @Override
            public void onRefreshPulStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        mSwipeRefreshLayout.setRefreshViewText("下拉刷新");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        mSwipeRefreshLayout.setRefreshViewText("松开刷新");
                        break;
                    case SHSwipeRefreshLayout.START:
                        mSwipeRefreshLayout.setRefreshViewText("正在刷新");
                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        mSwipeRefreshLayout.setLoaderViewText("上拉加载");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        mSwipeRefreshLayout.setLoaderViewText("松开加载");
                        break;
                    case SHSwipeRefreshLayout.START:
                        mSwipeRefreshLayout.setLoaderViewText("正在加载...");
                        break;
                }
            }
        });
    }

    @Override
    public void showData(List<OperationRecordBean> recordBeanList) {

        mSwipeRefreshLayout.finishRefresh();
        mAdapter.addDatas(recordBeanList);
        if (recordBeanList.size() >= num) {
            mSwipeRefreshLayout.setLoadmoreEnable(true);
        }else{
            mSwipeRefreshLayout.setLoadmoreEnable(false);
        }
    }

    @Override
    public void showMoreData(List<OperationRecordBean> recordBeanList) {

        mAdapter.addMoreDatas(recordBeanList);
    }

    @Override
    public void showNoMore() {
        SnackbarUtil.show(mRecycleView, "暂无更多数据");
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, OperationRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onTimeSelect(Date date, View view) {
        time = TimeUtils.dataOne(date);
        mPresenter.recordList(p, num, time+"", p <=1);
    }
}
