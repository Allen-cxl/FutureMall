package com.pizidea.imagepicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.bean.ImageItem;

public class PickerActivity extends BaseImagePickerToolBarActivity implements AndroidImagePicker.OnImageSelectedListener{
    private final String TAG = PickerActivity.class.getSimpleName();

    ImagesGridFragment mFragment;
    AndroidImagePicker androidImagePicker;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.super_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("选择图片");

        androidImagePicker = AndroidImagePicker.getInstance();
        //most of the time you need to clear the last selected images or you can comment out this line

        final boolean isCrop = getIntent().getBooleanExtra("isCrop",false);
        imagePath = getIntent().getStringExtra(AndroidImagePicker.KEY_PIC_PATH);
        mFragment = new ImagesGridFragment();

        mFragment.setOnImageItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position = androidImagePicker.isShouldShowCamera() ? position-1 : position;

                if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_MULTI){
                    go2Preview(position);
                }else if(androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_SINGLE){
                    if(isCrop){
                        Intent intent = new Intent();
                        intent.setClass(PickerActivity.this,CropActivity.class);
                        intent.putExtra(AndroidImagePicker.KEY_PIC_PATH, androidImagePicker.getImageItemsOfCurrentImageSet().get(position).path);
                        startActivityForResult(intent, AndroidImagePicker.REQ_CAMERA);
                    }else{
                        androidImagePicker.clearSelectedImages();
                        androidImagePicker.addSelectedImageItem(position, androidImagePicker.getImageItemsOfCurrentImageSet().get(position));
                        setResult(RESULT_OK);
                        finish();
                    }

                }

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();

        androidImagePicker.addOnImageSelectedListener(this);

        int selectedCount = androidImagePicker.getSelectImageCount();
        onImageSelected(0, null, selectedCount, androidImagePicker.getSelectLimit());

    }

    /**
     * 预览页面
     * @param position
     */
    private void go2Preview(int position) {
        Intent intent = new Intent();
        intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, position);
        intent.setClass(PickerActivity.this, PreviewActivity.class);
        startActivityForResult(intent, AndroidImagePicker.REQ_PREVIEW);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preview_done, menu);
        MenuItem actionSettings = menu.findItem(R.id.action_settings);
        if (androidImagePicker.getSelectMode() == AndroidImagePicker.Select_Mode.MODE_MULTI){
            actionSettings.setVisible(true);
        }else {
            actionSettings.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }else if(id == R.id.action_settings){
            finish();
            androidImagePicker.notifyOnImagePickComplete(androidImagePicker.getSelectedImages());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onImageSelected(int position, ImageItem imageItem, int selectedItemsCount, int maxSelectLimit) {
        if(selectedItemsCount > 0){
            setTitle("选择图片("+selectedItemsCount+"/"+maxSelectLimit+")");
        }else{
            setTitle("选择图片");
        }
        Log.i(TAG, "=====EVENT:onImageSelected");
    }

    @Override
    protected void onDestroy() {
        androidImagePicker.removeOnImageItemSelectedListener(this);
        Log.i(TAG, "=====removeOnImageItemSelectedListener");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == AndroidImagePicker.REQ_PREVIEW && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
