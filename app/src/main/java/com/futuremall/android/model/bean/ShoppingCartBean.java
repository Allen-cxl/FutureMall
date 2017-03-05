package com.futuremall.android.model.bean;

import java.util.List;

/**
 * Created by PVer on 2017/3/5.
 */

public class ShoppingCartBean {

    private String ID;
    private String shopName;
    private boolean containFreight;
    private List<ShoppingCartProductBean> data;
    private boolean isCheckEd;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isContainFreight() {
        return containFreight;
    }

    public void setContainFreight(boolean containFreight) {
        this.containFreight = containFreight;
    }

    public List<ShoppingCartProductBean> getData() {
        return data;
    }

    public void setData(List<ShoppingCartProductBean> data) {
        this.data = data;
    }

    public boolean isCheckEd() {
        return isCheckEd;
    }

    public void setCheckEd(boolean checkEd) {
        isCheckEd = checkEd;
    }

    public static class ShoppingCartProductBean {

        private String ID;
        private String productName;
        private String productPic;
        private float productPrice;
        private int productCount;
        private boolean isCheckEd;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public float getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(float productPrice) {
            this.productPrice = productPrice;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public boolean isCheckEd() {
            return isCheckEd;
        }

        public void setCheckEd(boolean checkEd) {
            isCheckEd = checkEd;
        }
    }
}
