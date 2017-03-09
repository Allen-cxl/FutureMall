package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
import com.futuremall.android.presenter.OrderDetailPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.OrderDetailAdapter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;

    View mHeadView, mFootView;
    OrderDetailHeadHolder mDetailHeadHolder;
    OrderDetailFootHolder mDetailFootHolder;


    OrderDetailAdapter mAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.activity_order_detail;
    }

    @Override
    protected void initEventAndData() {
        mHeadView = LayoutInflater.from(this).inflate(R.layout.layout_order_detail_head, null);
        mFootView = LayoutInflater.from(this).inflate(R.layout.layout_order_detail_foot, null);
        mDetailHeadHolder = new OrderDetailHeadHolder(mHeadView);
        mDetailFootHolder = new OrderDetailFootHolder(mFootView);

        setToolBar(mSuperToolbar, getString(R.string.order_detail), false);

        mAdapter = new OrderDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OrderDetailAdapter(this);
        mRecycleView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);


        mAdapter.addHeadView(mHeadView);
        mAdapter.addFootView(mFootView);

        mPresenter.orderDetail();
    }

    @Override
    public void showContent(OrderDetail dataList) {

        mDetailHeadHolder.mTvOrderNo.setText(getString(R.string.order_no) + dataList.getExpressNo());
        mDetailHeadHolder.mTvOrderDate.setText(getString(R.string.order_data) + dataList.getOrderDate());
        mDetailHeadHolder.mTvUserName.setText(dataList.getReceiveName() +"   "+ dataList.getReceivePhone());
        mDetailHeadHolder.mTvAddress.setText(dataList.getReceiveAddress());
        mDetailHeadHolder.mTvOrderStatus.setText("卖家已发货");
        mDetailHeadHolder.mTvShopName.setText(dataList.getShopName());

        mDetailFootHolder.mTvTotalCount.setText(String.format(mContext.getString(R.string.total_count), dataList.getProductTotalCount()));
        String price = StringUtil.getPrice(mContext, dataList.getProductTotalPrice()).toString();
        String integral = String.format(mContext.getString(R.string.total_integral), dataList.getIntegral());
        mDetailFootHolder.mTvTotalPrice.setText(price + integral);

        mAdapter.setData(dataList.getData());
        mDetailFootHolder.mTvExpressType.setText(mContext.getString(R.string.express_type) + dataList.getExpressType());
        mDetailFootHolder.mTvExpressNo.setText(mContext.getString(R.string.express_no) + dataList.getExpressNo());
        mDetailFootHolder.mLayoutExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackbarUtil.show(getCurrentFocus(),"物流信息");
            }
        });
    }

    class OrderDetailHeadHolder{

        @BindView(R.id.tv_orderNo)
        TextView mTvOrderNo;
        @BindView(R.id.tv_orderStatus)
        TextView mTvOrderStatus;
        @BindView(R.id.tv_orderDate)
        TextView mTvOrderDate;
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;

        OrderDetailHeadHolder(View v) {
            ButterKnife.bind(this,v);

        }
    }

    class OrderDetailFootHolder{

        @BindView(R.id.tv_total_count)
        TextView mTvTotalCount;
        @BindView(R.id.tv_total_price)
        TextView mTvTotalPrice;
        @BindView(R.id.tv_express_type)
        TextView mTvExpressType;
        @BindView(R.id.tv_express_no)
        TextView mTvExpressNo;
        @BindView(R.id.layout_express)
        LinearLayout mLayoutExpress;

        OrderDetailFootHolder(View v) {
            ButterKnife.bind(this,v);
        }
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        context.startActivity(intent);
    }
}
