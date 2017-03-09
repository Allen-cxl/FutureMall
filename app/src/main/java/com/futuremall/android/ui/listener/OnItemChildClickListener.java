package com.futuremall.android.ui.listener;

import android.view.View;


public interface OnItemChildClickListener<T>{

    void onItemChildClick(T t, View itemView, int position);
}
