package com.futuremall.android.model.event;

import com.futuremall.android.model.bean.AddressBean;

/**
 * Created by PVer on 2017/4/16.
 */

public class AddressEvent {

    public AddressBean mAddressBean;

    public AddressEvent(AddressBean bean){
        mAddressBean = bean;
    }
}
