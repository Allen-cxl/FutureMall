package com.futuremall.android.util;

import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.model.bean.OrderProduct;
import com.futuremall.android.model.bean.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2017/3/5.
 */

public class TestData {

    public static List<ShoppingCartBean> getShoppingCartBeanLsit() {

        List<ShoppingCartBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<ShoppingCartBean.ShoppingCartProductBean> productBeanList = new ArrayList<>();
            ShoppingCartBean cartBean = new ShoppingCartBean();
            cartBean.setShopName("店铺" + i);

            for (int j = 0; j < 3; j++) {
                ShoppingCartBean.ShoppingCartProductBean productBean = new ShoppingCartBean.ShoppingCartProductBean();
                String pic = "http://thumb.webps.cn/tk4771825/43956210618/i2/img/1/T1uQtbXulfXXXXXXXX_!!0-item_pic.jpg_200x200.jpg";
                productBean.setProductName("测试产品名字" + j);
                productBean.setProductPic(pic);
                productBean.setProductPrice(56);
                productBean.setProductCount(j);
                productBeanList.add(productBean);
                cartBean.setData(productBeanList);
            }

            list.add(cartBean);
        }
        return list;
    }

    public static List<OrderList> getOrderList() {

        List<OrderList> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<OrderProduct> orderList = new ArrayList<>();
            OrderList orderListBean = new OrderList();
            orderListBean.setShopName("店铺" + i);
            orderListBean.setExpressStatus(1);
            orderListBean.setOrderStatus(2);
            orderListBean.setIntegral(99999);

            for (int j = 0; j < 3; j++) {
                OrderProduct order = new OrderProduct();
                String pic = "http://thumb.webps.cn/tk4771825/43956210618/i2/img/1/T1uQtbXulfXXXXXXXX_!!0-item_pic.jpg_200x200.jpg";
                order.setProductName("测试产品名字" + j);
                order.setProductPic(pic);
                order.setProductPrice(56);
                order.setProductCount(j);
                orderList.add(order);
                orderListBean.setData(orderList);
            }

            list.add(orderListBean);
        }
        return list;
    }

    public static OrderDetail getOrderDeatail() {


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setShopName("店铺");
        orderDetail.setOrderNo("1324234234234");
        orderDetail.setOrderDate("2017-3-9");
        orderDetail.setReceiveName("隔壁老王");
        orderDetail.setReceivePhone("13800000000");
        orderDetail.setReceiveAddress("安庆市迎江区安庆市迎江区安庆市迎江区安庆市迎江区安庆市迎江区");
        orderDetail.setExpressStatus(1);
        orderDetail.setExpressType("圆通快递");
        orderDetail.setExpressNo("TH2313434");
        orderDetail.setExpressStatus(1);
        orderDetail.setProductTotalCount("3");
        orderDetail.setProductTotalPrice("2324.00");
        orderDetail.setExpressStatus(1);
        orderDetail.setOrderStatus(2);
        orderDetail.setIntegral(99999);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            OrderProduct order = new OrderProduct();
            String pic = "http://thumb.webps.cn/tk4771825/43956210618/i2/img/1/T1uQtbXulfXXXXXXXX_!!0-item_pic.jpg_200x200.jpg";
            order.setProductName("测试产品名字测试产品名字测试产品名字测试产品名字测试产品名字测试产品名字" + j);
            order.setProductPic(pic);
            order.setProductPrice(56);
            order.setProductCount(j);
            orderProducts.add(order);
            orderDetail.setData(orderProducts);
        }

        return orderDetail;
    }
}
