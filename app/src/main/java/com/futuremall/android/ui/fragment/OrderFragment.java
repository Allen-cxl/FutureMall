package com.futuremall.android.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.presenter.OrderCenterPresenter;
import com.futuremall.android.ui.adapter.OrderCenterAdapter;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;


public class OrderFragment extends BaseFragment<OrderCenterPresenter> implements OrderCenterContract.View {


    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.swipeRefreshLayout)
    SHSwipeRefreshLayout mSwipeRefreshLayout;
    OrderCenterAdapter mAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new OrderCenterAdapter(getContext());
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setLoadmoreEnable(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.orderList();

                        Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }

            @Override
            public void onLoading() {

            }


            @Override
            public void onRefreshPulStateChange(float percent, int state) {

            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {

            }
        });
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showContent(List<OrderList> dataList) {

        mSwipeRefreshLayout.finishRefresh();
        mAdapter.setData(dataList);
    }
}
