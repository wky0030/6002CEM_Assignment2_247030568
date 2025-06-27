package com.example.sign.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sign.R;
import com.example.sign.net.OnBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.SharedPreferencesUtils;
import com.example.sign.util.Util;

public class LogActivity extends BaseActivity{
    TextView tv;
    public static String titleS="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initTitle();
        tv=findViewById(R.id.tv);
        if(titleS.equals("Notice")){
            NetUitl.getNews(new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    String info= (String) obj;
                    tv.setText(Util.getValue(info));
                }

                @Override
                public void onFail(Object obj) {

                }
            });
        }else  if(titleS.equals("About")){
            NetUitl.getAbout(new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    String info= (String) obj;
                    tv.setText(Util.getValue(info));
                }

                @Override
                public void onFail(Object obj) {

                }
            });
        }else {
            new Thread(() -> {
                String log=Util.readFileToLocalStorage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(log);
                    }
                });
            }).start();
        }

    }
    private void initTitle() {
        TextView title=findViewById(R.id.title);
        title.setText("Record");
        if(!TextUtils.isEmpty(titleS)){
            title.setText(titleS);
        }
        ImageView bacBtn=findViewById(R.id.back_btn);
        bacBtn.setVisibility(View.VISIBLE);
        bacBtn.setImageResource(R.drawable.back);
        bacBtn.setOnClickListener(v -> finish());

//        ImageView rb=findViewById(R.id.right_btn);
//        rb.setVisibility(View.VISIBLE);
//        rb.setOnClickListener(v->{
//            AddActivity.type=1;
//            startActivity(new Intent(this,AddActivity.class));
//        });

    }

}
