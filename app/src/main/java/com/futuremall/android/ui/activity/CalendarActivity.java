package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import com.futuremall.android.R;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class CalendarActivity extends SimpleActivity {

    @BindView(R.id.date_picker)
    DatePicker mDatePicker;

    @Override
    protected int getLayout() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initEventAndData() {



        mDatePicker.setDate(TimeUtils.getYear(), TimeUtils.getMonth());
        mDatePicker.setMode(DPMode.SINGLE);
        mDatePicker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.GREEN);
                canvas.drawRect(rect, paint);
            }

            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.BLUE);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
            }
        });
        mDatePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                SnackbarUtil.show(getWindow().getDecorView(), date);
            }
        });
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, CalendarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
