package com.futuremall.android.ui.ViewHolder;

import android.view.View;
import android.widget.CheckBox;

import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.util.DecimalUtil;
import com.futuremall.android.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCartHepler {

    public static final int TYPE_DEL = 1;
    public static final int TYPE_EDIT = 2;
    public static final int TYPE_ADD = 3;
    public static final int TYPE_REDUCE = 4;

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static void selectOrCancelAll(List<ShoppingCartBean> list, boolean isSelectAll) {


        for (ShoppingCartBean shoppingCartBean : list){
            shoppingCartBean.setCheckEd(isSelectAll);
            for (ShoppingCartBean.ShoppingCartProductBean shoppingCartProductBean : shoppingCartBean.getData()){
                shoppingCartProductBean.setCheckEd(isSelectAll);
            }
        }
    }

    /**
     * 根据商品的选中状态，点下删除按钮，删除商品
     */
    public static void  deleteGoods(List<ShoppingCartBean> list) {

        Iterator<ShoppingCartBean> iteratorShopCarts = list.iterator();
        while (iteratorShopCarts.hasNext()) {

            ShoppingCartBean sc = iteratorShopCarts.next();
            if (sc.isCheckEd()) {

                iteratorShopCarts.remove();
            } else {

                Iterator<ShoppingCartBean.ShoppingCartProductBean> iteratorGoods = sc.getData().iterator();
                while (iteratorGoods.hasNext()) {
                    ShoppingCartBean.ShoppingCartProductBean shopCartGood = iteratorGoods.next();
                    if (shopCartGood.isCheckEd()) {
                        iteratorGoods.remove();
                    }
                }

            }
        }

    }

    public static List<String> selectGoods(List<ShoppingCartBean> list) {

        List<String> shopCartIDs = new ArrayList<>();
        //Iterator<String> ids = shopCartIDs.iterator();

        Iterator<ShoppingCartBean> iteratorShopCarts = list.iterator();
        while (iteratorShopCarts.hasNext()) {

            ShoppingCartBean sc = iteratorShopCarts.next();
            if (sc.isCheckEd()) {
                Iterator<ShoppingCartBean.ShoppingCartProductBean> iteratorGoods = sc.getData().iterator();

                while (iteratorGoods.hasNext()) {
                    ShoppingCartBean.ShoppingCartProductBean shopCartGood = iteratorGoods.next();
                    shopCartIDs.add(shopCartGood.getID());
                }

            } else {

                Iterator<ShoppingCartBean.ShoppingCartProductBean> iteratorGoods = sc.getData().iterator();
                while (iteratorGoods.hasNext()) {
                    ShoppingCartBean.ShoppingCartProductBean shopCartGood = iteratorGoods.next();
                    if (shopCartGood.isCheckEd()) {
                        shopCartIDs.add(shopCartGood.getID());
                    }
                }

            }
        }

        return shopCartIDs;
    }


    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isCheckEd();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<ShoppingCartBean.ShoppingCartProductBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isCheckEd();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCartBean> list, int groudPosition, int childPosition, boolean isChecked) {
        boolean isSelectAll;

        list.get(groudPosition).getData().get(childPosition).setCheckEd(isChecked);//单个图标的处理
        boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getData());
        list.get(groudPosition).setCheckEd(isSelectCurrentGroup);//组图标的处理
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCartBean> list, int groudPosition, boolean isChecked) {
        boolean isSelectAll;
        list.get(groudPosition).setCheckEd(isChecked);
        for (int i = 0; i < list.get(groudPosition).getData().size(); i++) {
            list.get(groudPosition).getData().get(i).setCheckEd(isChecked);
        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param checkBox
     * @return 是否勾上，之后状态
     */
    public static void checkItem(boolean isSelect, CheckBox checkBox) {

        checkBox.setChecked(isSelect);
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价
     */
    public static String[] getShoppingCount(List<ShoppingCartBean> listGoods) {
        String[] infos = new String[2];
        String selectedCount = "0";
        String selectedMoney = "0";
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getData().size(); j++) {
                boolean isSelectd = listGoods.get(i).getData().get(j).isCheckEd();
                if (isSelectd) {
                    String price = String.valueOf(listGoods.get(i).getData().get(j).getProductPrice());
                    String num = String.valueOf(listGoods.get(i).getData().get(j).getProductCount());
                    String countMoney = DecimalUtil.multiplyWithScale(price, num, 2);
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                    selectedCount = DecimalUtil.add(selectedCount, "1");
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        return infos;
    }

    /**
     * 增减数量，操作通用，数据不通用
     */
    public static String addOrReduceGoodsNum(boolean isPlus, String count, View view) {
        String num = "1";
        if (isPlus) {
            num = String.valueOf(Integer.parseInt(count) + 1);
        } else {
            int i = Integer.parseInt(count);
            if (i > 1) {
                num = String.valueOf(i - 1);
            } else {
                SnackbarUtil.show(view, "商品数量不能小于1！");
                num = "1";
            }
        }
        return num;
    }

}
