package com.futuremall.android.model.bean;

import java.util.List;

/**
 * Created by PVer on 2017/3/5.
 */

public class ShoppingCartBean {

    private String shop_id;
    private String shop_name;
    private boolean containFreight;
    private List<ShoppingCartProductBean> cart_goods;
    private boolean isCheckEd;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public boolean isContainFreight() {
        return containFreight;
    }

    public void setContainFreight(boolean containFreight) {
        this.containFreight = containFreight;
    }

    public List<ShoppingCartProductBean> getCart_goods() {
        return cart_goods;
    }

    public void setCart_goods(List<ShoppingCartProductBean> cart_goods) {
        this.cart_goods = cart_goods;
    }

    public boolean isCheckEd() {
        return isCheckEd;
    }

    public void setCheckEd(boolean checkEd) {
        isCheckEd = checkEd;
    }

    public static class ShoppingCartProductBean {

        private String rec_id;
        private String goods_id;
        private String goods_name;
        private String goods_img;
        private float goods_price;
        private int goods_num;
        private boolean isCheckEd;

        public String getRec_id() {
            return rec_id;
        }

        public void setRec_id(String rec_id) {
            this.rec_id = rec_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public float getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(float goods_price) {
            this.goods_price = goods_price;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public boolean isCheckEd() {
            return isCheckEd;
        }

        public void setCheckEd(boolean checkEd) {
            isCheckEd = checkEd;
        }
    }
}
