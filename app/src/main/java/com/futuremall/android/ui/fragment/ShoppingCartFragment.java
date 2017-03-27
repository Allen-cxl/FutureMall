package com.futuremall.android.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.presenter.Contract.ShoppingCarContract;
import com.futuremall.android.presenter.ShoppingCartPresenter;
import com.futuremall.android.ui.ViewHolder.ShoppingCartHepler;
import com.futuremall.android.ui.adapter.ShoppingCarAdapter;
import com.futuremall.android.ui.listener.OnShopCartChangeListener;
import com.futuremall.android.util.StringUtil;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;


public class ShoppingCartFragment extends BaseFragment<ShoppingCartPresenter> implements ShoppingCarContract.View, View.OnClickListener, OnShopCartChangeListener{


    ShoppingCarAdapter mAdapter;
    @BindView(R.id.super_title)
    TextView mSuperTitle;
    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.swipeRefreshLayout)
    SHSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    @BindView(R.id.tv_product_price)
    TextView mTvProductPrice;
    @BindView(R.id.tv_freight)
    TextView mTvFreight;
    @BindView(R.id.bt_pay)
    Button mBtPay;
    @BindView(R.id.ll_pay)
    LinearLayout mLlPay;
    @BindView(R.id.bt_delete)
    Button mBtDelete;

    MenuItem menuItem;
    private boolean mIsChecked;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        setHasOptionsMenu(true);
        return R.layout.fragment_shoppingcart;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.shopping_cart), false);
        mTvProductPrice.setText(StringUtil.getPrice(getContext(), "0.00"));
        mBtPay.setText(String.format(getString(R.string.to_pay), "0"));
        mBtPay.setOnClickListener(this);
        mBtDelete.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsChecked = isChecked;

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ShoppingCarAdapter(getContext());
        mAdapter.setOnShopCartChangeListener(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setLoadmoreEnable(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.shoppingCar();

                        Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }

            @Override
            public void onLoading() {

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

            }
        });
        mPresenter.shoppingCar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit, menu);
        menuItem = menu.findItem(R.id.action_edit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_edit:

                String str = menuItem.getTitle().toString();
                setMenuItem(str);
                break;

        }
        return false;
    }

    public void setMenuItem(String itemName) {

        if (itemName.equals(getString(R.string.edit))) {

            if (mAdapter.getCurrentList() == null || mAdapter.getCurrentList().size() == 0) return;
            menuItem.setTitle(getString(R.string.done));
            mCheckBox.setChecked(false);
            mPresenter.menuDone(mAdapter.getCurrentList(), false);

        } else {
            menuItem.setTitle(getString(R.string.edit));
            mCheckBox.setChecked(true);
            mPresenter.menuEdit(mAdapter.getCurrentList(), true);
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showContent(List<ShoppingCartBean> data) {
        mSwipeRefreshLayout.finishRefresh();
        mAdapter.setData(data);
    }

    @Override
    public void showFreight(int visibility) {
        mTvFreight.setVisibility(visibility);
    }

    @Override
    public void showPayLayout(int visibility) {
        mLlPay.setVisibility(visibility);
    }

    @Override
    public void showDeleteButton(int visibility) {
        mBtDelete.setVisibility(visibility);
    }

    @Override
    public void updateShoppingCartCount(String recID, int count) {

        ShoppingCartHepler.setShoppingCartCount(mAdapter.getCurrentList(), recID, count);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTotalPrice(String totalPrice, String totalCount) {
        mTvProductPrice.setText(StringUtil.getPrice(getContext(),totalPrice));
        mBtPay.setText(String.format(getString(R.string.to_pay), totalCount));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBox:
                mCheckBox.setChecked(mIsChecked);
                mPresenter.selectOrCancelAll(mAdapter.getCurrentList(), mIsChecked);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.bt_delete:
                mPresenter.delete(mAdapter.getCurrentList());
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.bt_pay:
                mPresenter.toPay(mAdapter.getCurrentList());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDataChange(String id, String count) {

        mPresenter.dataChange(id, count);
    }

    @Override
    public void onTotalPrice(List<ShoppingCartBean> list) {

        mPresenter.calculateTotalPrice(list);
    }

    @Override
    public void onSelectItem(boolean isSelectedAll) {
        mCheckBox.setChecked(isSelectedAll);
    }
}
