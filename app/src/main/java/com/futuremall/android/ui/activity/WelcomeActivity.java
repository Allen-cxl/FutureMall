package com.futuremall.android.ui.activity;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;

public class WelcomeActivity extends SimpleActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {
        MainActivity.enter(this, Constants.RB_HOME);
        finish();
    }
}
