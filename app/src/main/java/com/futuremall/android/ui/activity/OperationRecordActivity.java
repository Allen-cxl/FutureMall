package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.OperationRecordBean;
import com.futuremall.android.presenter.Contract.OperationRecordContract;
import com.futuremall.android.presenter.OperationRecordPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.OperationRecordAdapter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.widget.LoadingLayout;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class OperationRecordActivity extends BaseActivity<OperationRecordPresenter> implements OperationRecordContract.View {

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

                CalendarActivity.enter(this);
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

        time = System.currentTimeMillis();
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
        mAdapter.addMoreDatas(recordBeanList);

        if (mAdapter.getItemCount() >= num) {
            mSwipeRefreshLayout.setLoadmoreEnable(true);
        }else{
            mSwipeRefreshLayout.setLoadmoreEnable(false);
        }
    }

    @Override
    public void showNoMore() {
        SnackbarUtil.show(mRecycleView, "暂无更多数据");
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, OperationRecordActivity.class);
        context.startActivity(intent);
    }
}
