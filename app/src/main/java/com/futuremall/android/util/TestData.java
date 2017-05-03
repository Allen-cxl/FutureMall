package com.futuremall.android.util;

import com.futuremall.android.model.bean.HotKeyWord;
import com.futuremall.android.model.bean.OperationRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2017/3/5.
 */

public class TestData {


/*    public static List<OrderList> getOrderList() {

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
    }*/

    public static List<OperationRecordBean> getRecordList() {


        List<OperationRecordBean> recordBeanList = new ArrayList<>();


        for (int j = 0; j < 6; j++) {
            OperationRecordBean bean = new OperationRecordBean();
            bean.setChange_desc("转账支付用户[1022][15157393979]积分：2000" + j);
            bean.setRebate("35353");
            if(j %2 ==0){
                bean.setPay_points("35353.00");
            }else{
                bean.setPay_points("-35353.00");
            }
            bean.setPayin("45453");
            bean.setHighreward("44444");
            bean.setUser_money("6565.89");
            bean.setChange_time("2017年2月15日  09:28:00");
            recordBeanList.add(bean);
        }

        return recordBeanList;
    }

    public static List<HotKeyWord> getHotLabel() {


        List<HotKeyWord> labels = new ArrayList<>();


        for (int j = 0; j < 6; j++) {
            HotKeyWord tag = new HotKeyWord();
            tag.setKeyword("测试"+j);
            labels.add(tag);
        }

        return labels;
    }
}
