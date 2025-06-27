package com.example.sign.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sign.R;
import com.example.sign.adapter.spAdapter;
import com.example.sign.bean.Device;
import com.example.sign.bean.Shop;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.SharedPreferencesUtils;
import com.example.sign.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SettingActivity extends BaseActivity {
    TextView deviceName,shopName,title;
    Button exit;
    ImageView back_btn;
    private SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    private View header_in;
    private Spinner spinner;

    List<Shop> spData;
    spAdapter adapter;
    private  boolean isFirst=true;
    //声明数据库帮助器的对象
//    private UserDBHelper userDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.setLayoutId(R.layout.setting_main);
        super.onCreate(savedInstanceState);
        initView();
        initSpinner();

        Util.l("我重启了 "+this.getClass().getSimpleName());
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        deviceName=findViewById(R.id.deviceName);
        exit=findViewById(R.id.exit);
        if(Util.userName!=null){
            deviceName.setText(Util.userName);
        }
        String shopNameStr=sp.get("shopName");
        if(shopNameStr!=null){
            shopName.setText(shopNameStr);
        }
        exit.setOnClickListener(o->{
            sp.put("token",null);
            Intent intent = getIntent();
            intent.putExtra("backType", 1);
            setResult(100,intent);
            finish();
        });

        back_btn=findViewById(R.id.back_btn);
        back_btn.setImageResource(R.drawable.back);
        back_btn.setVisibility(View.VISIBLE);
        back_btn.setOnClickListener(o->finish());
        title=findViewById(R.id.title);
        title.setText(getResources().getString(R.string.setting));


        header_in=findViewById(R.id.header_in);
        header_in.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private void initSpinner() {
        spinner=findViewById(R.id.sp);
        spData=new ArrayList<>();

        Shop shop=new Shop();
        shop.setName("English");
        shop.setAddress("en");
        spData.add(shop);

        Shop shop2=new Shop();
        shop2.setName("简体中文");
        shop2.setAddress("zh");
        spData.add(shop2);

        Shop shop3=new Shop();
        shop3.setName("한국어");
        shop3.setAddress("ko");
        spData.add(shop3);

        Shop shop4=new Shop();
        shop4.setName("မြန်မာ");
        shop4.setAddress("my");
        spData.add(shop4);

        Shop shop5=new Shop();
        shop5.setName("ภาษาไทย");
        shop5.setAddress("th");
        spData.add(shop5);

        Shop shop6=new Shop();
        shop6.setName("español");
        shop6.setAddress("es");
        spData.add(shop6);

        adapter = new spAdapter(spData, this, new CallBack() {
            @Override
            public void onDo(Object o) {
                int postion= (int) o;
                Shop shop =  spData.get(postion);
                sp.put("country","");
                sp.put("lang",shop.getAddress());
                spinner.clearFocus();
                reStart();
            }
        });//实例化适配器

        spinner.setAdapter(adapter);//给hero设置适配器
        String lang=sp.get("lang");
        for (int i = 0; i < spData.size(); i++) {
            if(spData.get(i).getAddress().equals(lang)){
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Shop shop= (Shop) parent.getItemAtPosition(position);
                sp.put("country","");
                sp.put("lang",shop.getAddress());
                if(lang!=null&&!lang.equals(shop.getAddress())){
                    reStart();
//                    MainActivity.needChangLang=shop.getAddress();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.version_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,LogActivity.class));
            }
        });
    }



}
