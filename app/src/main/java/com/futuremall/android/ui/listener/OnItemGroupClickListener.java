package com.futuremall.android.ui.listener;

import android.view.View;


public interface OnItemGroupClickListener<T>{

    void onItemGroupClick(T t, View itemView, int position);
}
