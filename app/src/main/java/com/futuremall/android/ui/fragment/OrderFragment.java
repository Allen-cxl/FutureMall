package com.futuremall.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.presenter.OrderCenterPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.OrderCenterAdapter;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.SnackbarUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;


public class OrderFragment extends BaseFragment<OrderCenterPresenter> implements OrderCenterContract.View {


    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.swipeRefreshLayout)
    SHSwipeRefreshLayout mSwipeRefreshLayout;
    OrderCenterAdapter mAdapter;
    int p = 1, num = 15;
    String status;

    public static OrderFragment newInstance(String status) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IT_STATUS, status);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initEventAndData() {

    }

    private void initValue() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getString(Constants.IT_STATUS);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new OrderCenterAdapter(getContext());
        mRecycleView.addItemDecoration(new DividerItemDecoration(
                getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        mPresenter.orderList(p + "", num + "", status, true);
        mSwipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p = 1;
                        num = 15;
                        mPresenter.orderList(p + "", num + "", status, false);
                    }
                }, 1000);
            }

            @Override
            public void onLoading() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.orderList((p++) + "", num + "", status, false);
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
    public void showError(String msg) {

    }

    @Override
    public void showContent(List<OrderList> dataList) {

        if(p >1 && (dataList==null || dataList.size()<=0)){
            SnackbarUtil.show(mRecycleView, "暂无更多数据");
            return;
        }
        mSwipeRefreshLayout.finishRefresh();
        mAdapter.setData(dataList);

        if (mAdapter.getItemCount() >= num) {
            mSwipeRefreshLayout.setLoadmoreEnable(true);
        }else{
            mSwipeRefreshLayout.setLoadmoreEnable(false);
        }
    }


    @Override
    public void onFirstUserVisible() {
        initValue();
    }
}
