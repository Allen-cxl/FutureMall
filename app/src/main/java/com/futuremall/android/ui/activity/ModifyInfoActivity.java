package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;

import butterknife.BindView;

public class ModifyInfoActivity extends SimpleActivity {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_content)
    EditText mEtContent;

    @Override
    protected int getLayout() {
        return R.layout.activity_modify_info;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_save:

            String value = mEtContent.getText().toString();
                break;

        }
        return false;
    }

    @Override
    protected void initEventAndData() {

        String title = getIntent().getStringExtra(Constants.IT_TITLE);
        String value = getIntent().getStringExtra(Constants.IT_VALUE);
        setToolBar(mSuperToolbar, title, true);
        mEtContent.setText(value);
    }



    public static void enter(Context context, String title, String value) {

        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.IT_TITLE, title);
        intent.putExtra(Constants.IT_VALUE, value);
        context.startActivity(intent);
    }
}
