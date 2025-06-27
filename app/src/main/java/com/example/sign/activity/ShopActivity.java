package com.example.sign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.sign.R;
import com.example.sign.bean.School;
import com.example.sign.net.Api;
import com.example.sign.net.OnBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.Util;


public class ShopActivity extends Activity {
    //FangZi item;
    ImageView img;
    TextView name, alias, info2, des;
    EditText info;

    School fangZi;

    LinearLayout header_in;
    ImageView back, right_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        back = findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(view -> finish());
        TextView title = findViewById(R.id.title);
        header_in = findViewById(R.id.header_in);
        header_in.setBackgroundColor(getResources().getColor(R.color.transparent));
        title.setText("购买");
        right_btn = findViewById(R.id.right_btn);
        right_btn.setVisibility(View.INVISIBLE);

        int fid = getIntent().getIntExtra("fid", -1);
        if (fid != -1) {
            getInfo(fid);
        }

        findViewById(R.id.bt).setOnClickListener(v -> {
            NetUitl.addSave(fangZi.getId(), !fangZi.isSave, new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    Util.showAlert(ShopActivity.this, "购买成功", () -> finish());

                }

                @Override
                public void onFail(Object obj) {

                }
            });
        });

        name = findViewById(R.id.name);
        alias = findViewById(R.id.alias);
        info = findViewById(R.id.info);
        info2 = findViewById(R.id.info2);

        img = findViewById(R.id.img);


        info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Util.isNotEmpty(s.toString())
                        && Util.isNotEmpty(fangZi.getContent())) {
                    try {
                        Util.l(s.toString());
                        Util.l(fangZi.getContent());
                        int num = Integer.parseInt(s.toString());
                        float price = Float.parseFloat(fangZi.getContent());
                        float total = price * num;
                        info2.setText("总价（元）：" + total);
                    }catch (Exception e){

                    }

                }

            }
        });


    }

    private void getInfo(Integer fid) {
        if (fid == null) {
            return;
        }
        NetUitl.getSchool(fid + "", new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                fangZi = (School) obj;
                setData();
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }

    private void setData() {
        if (fangZi == null) {
            return;
        }
        Glide.with(this).load(Api.getImgUrl(fangZi.getImgs())).into(img);
        Util.l("名称：" + Util.getValue(fangZi.getName()));
        name.setText("每月租金(元/月):" + Util.getValue(fangZi.getContent()));
        alias.setText("地址：" + Util.getValue(fangZi.getContent2()));
        info.setText("");
        info2.setText("总价(元）：" + Util.getValue(fangZi.getContent()));
    }

}
