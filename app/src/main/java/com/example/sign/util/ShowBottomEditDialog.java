package com.example.sign.util;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.sign.R;
import com.example.sign.util.img.ImgCallBack;


public class ShowBottomEditDialog {
    private View view;
    public  static   String str="";
    public void BottomDialog(Context context, ImgCallBack onClick) {
        if(context==null){
            Util.l("context is null");
            return;
        }
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        view = View.inflate(context, R.layout.dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText et1=dialog.findViewById(R.id.et1);
        et1.setText(str);
        EditText et2=dialog.findViewById(R.id.et2);
        EditText et3=dialog.findViewById(R.id.et3);
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onTakePic("");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onPickPic("");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialog.dismiss();
            }
        });

    }
}