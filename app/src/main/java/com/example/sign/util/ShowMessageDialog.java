package com.example.sign.util;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.activity.MainActivity;
import com.example.sign.adapter.CheckAdapter;
import com.example.sign.adapter.NumAdapter;
import com.example.sign.bean.Dictionary;
import com.example.sign.bean.Good;
import com.example.sign.bean.GoodDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowMessageDialog {
    private View view,divider;
    private Dialog dialog;

    TextView title, content, cancel, confirm;


    private Context context;
    private CallBack callBack;
    private GoodDetail goodDetail;
    private String titleStr="";

    public ShowMessageDialog(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        if (dialog == null) {
            //1、使用Dialog、设置style
            dialog = new Dialog(context, R.style.DialogTheme);
            //2、设置布局
            view = View.inflate(context, R.layout.dialog_normal, null);

            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            cancel = view.findViewById(R.id.cancel);
            confirm = view.findViewById(R.id.confirm);
            divider=view.findViewById(R.id.divider);

            title.setText(context.getString(R.string.tishi));
            content.setText(titleStr);
            cancel.setText(context.getString(R.string.cancel));
            confirm.setText(context.getString(R.string.confirm));

            cancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            confirm.setOnClickListener(v -> {
                dialog.dismiss();
                callBack.onDo(null);
            });

            dialog.setContentView(view);

            Window window = dialog.getWindow();
            //设置弹出位置
            window.setGravity(Gravity.CENTER);
            //设置弹出动画
            window.setWindowAnimations(R.style.main_menu_animStyle);
            int dialogWidth= (int) (DpUtil.getScreenWidth(context)*0.8);
            //设置对话框大小
            window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        content.setText(titleStr);

    }
    public void setCancelHide() {
     cancel.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
    }
    public void setNotCancel( ) {
        if(dialog!=null){
            dialog.setCancelable(false);
        }
    }
    public void show( ) {

        dialog.show();
    }

    public void setData(String message) {
        content.setText(message);
    }
}