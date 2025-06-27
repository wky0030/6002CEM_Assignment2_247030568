package com.example.sign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sign.R;
import com.example.sign.adapter.spAdapter;
import com.example.sign.bean.Shop;
import com.example.sign.bean.User;
import com.example.sign.net.OnBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.Util;

import java.util.List;


public class ReSetPassWordActivity extends Activity {
    EditText name;
    EditText pwd;
    Spinner sp;
    List<Shop> spData;
    spAdapter adapter;
    Shop shop;
    LinearLayout header_in;
    ImageView back;
    //声明数据库帮助器的对象
//    private UserDBHelper userDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_main);
        name=findViewById(R.id.ed_login_user);
        pwd=findViewById(R.id.ed_login_password);

        ImageView clear = findViewById(R.id.clear);
        ImageView clear2= findViewById(R.id.clear2);
        Util.setEditEvent(name,clear);
        Util.setEditEvent(pwd,clear2);

        back= findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(view -> finish());
        TextView title=findViewById(R.id.title);
        header_in=findViewById(R.id.header_in);
        header_in.setBackgroundColor(getResources().getColor(R.color.transparent));
        title.setText("Modify password");

//        initSpinner();

        findViewById(R.id.bt_register).setOnClickListener(view -> {
            Util.l("name="+name.getText()+" pwd="+pwd.getText());
            if(TextUtils.isEmpty(name.getText().toString())||
                    TextUtils.isEmpty(pwd.getText().toString())) {
                Toast.makeText(ReSetPassWordActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            User device=new User();
            device.setUsername(Util.userName);
            device.setPassword(pwd.getText().toString());
//            if(shop!=null){
//                device.setShopId(shop.getId());
//            }else {
//                Toast.makeText(RegistActivity.this, getString(R.string.needShop), Toast.LENGTH_SHORT).show();
//                return;
//            }
            NetUitl.addUser(device, new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    if(obj!=null){
                        int code= (int) obj;
                        if(code==200){
                            Util.show("Modify success");
                        }else {
                            Util.show("Modify failed");
                        }
                        finish();
                    }

                }

                @Override
                public void onFail(Object obj) {
                    Util.show("Error:"+(String)obj);
                }
            });
    });
    }
    @Override
    protected void onStop() {
        super.onStop();
//        userDBHelper.closeLink();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


//    private void initSpinner() {
//        sp=findViewById(R.id.sp);
//        spData=new ArrayList<>();
//        adapter = new spAdapter(spData, this);//实例化适配器
//
//        sp.setAdapter(adapter);//给hero设置适配器
//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                shop= (Shop) parent.getItemAtPosition(position);
//
//              Toast.makeText(RegistActivity.this, "选中的分类是: " + spData.get(position).getName(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        NetUitl.getShops(new OnBack() {
//            @Override
//            public void onSuccess(Object obj) {
//                if(obj!=null&&adapter!=null){
//                    spData= (List<Shop>) obj;
//                    adapter.setData(spData);
//                }
//            }
//
//            @Override
//            public void onFail(Object obj) {
//
//            }
//        });
//    }
}
