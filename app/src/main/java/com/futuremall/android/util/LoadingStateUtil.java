package com.futuremall.android.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.futuremall.android.R;


public class LoadingStateUtil {

    static Dialog dialogProgress;

    public  static void  show(Context context){

        canCancel(context, false);
    }

    public  static void  canCancel(Context context, boolean isCanCancel){
        if(dialogProgress == null){
            View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_loding, null,
                    false);
            dialogProgress = new Dialog(context, R.style.MyDialogStyle);
            dialogProgress.setContentView(view);
        }
        if(!isCanCancel){
            dialogProgress.setCancelable(false);
            dialogProgress.setCanceledOnTouchOutside(false);
        }
        dialogProgress.show();
    }

    public  static void  close(){
        if (dialogProgress !=null){
            dialogProgress.dismiss();
            dialogProgress = null;
        }
    }
}
