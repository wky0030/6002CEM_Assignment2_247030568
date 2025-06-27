package com.example.sign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sign.R;
import com.example.sign.bean.User;
import com.example.sign.util.SharedPreferencesUtils;
import com.example.sign.util.Util;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    protected SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    private int layoutId=R.layout.setting_main;

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
    protected Context context;

    public static User user=new User();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        context=this;
        String lang=sp.get("lang");
        String country=sp.get("country");
        if(!TextUtils.isEmpty(lang)){
            changeLanguage(this,lang,country);
        }
        setContentView(layoutId);
    }
    public  final void changeLanguage(Context context, String language, String country) {
        if (context == null || TextUtils.isEmpty(language)) {return;}
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = new Locale(language, country);
        resources.updateConfiguration(config, null);

    }
    public void setText(EditText et,String str){
        if(et==null){
            return;
        }
        if(Util.isNotEmpty(str)){
            et.setText(str);
        }else {
            et.setText("");
        }
    }
    public void reStart() {
        recreate();
    }
}
