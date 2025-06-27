package com.example.sign.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.sign.R;
import com.example.sign.adapter.spAdapter;
import com.example.sign.bean.Device;
import com.example.sign.bean.Shop;
import com.example.sign.bean.Token;
import com.example.sign.bean.User;
import com.example.sign.net.Data;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.SharedPreferencesUtils;
import com.example.sign.util.ShowMessageDialog;
import com.example.sign.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginActivity extends BaseActivity  {
    private EditText username,pwd;
    private Button login_bt;
    private TextView register_tv;

    private static final String TAG = "Login2Activity";

        private static final int REQUEST_CODE = 1;
    private String[] permissions = {
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_PRIVILEGED,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private List<String> permissionList = new ArrayList<>();
    private SharedPreferencesUtils sp;

//    private Shop shop;
    Spinner spinner;
//    List<Shop> spData;
    spAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 23) {
            System.out.println("版本正确");
            getPermission();
        }else {
            System.out.println("版本过低");
        }
        bindView();
        sp= SharedPreferencesUtils.getInstance("isLogin");

        String isLogin=sp.get("isLogin");
        if(Util.isNotEmpty(isLogin)&&isLogin.equals("true")){
            Util.userName=sp.get("username");
            Util.userId=Integer.parseInt(sp.get("userId"));
            user.setUsername(Util.userName);
            user.setId(Util.userId);
            user.setPhone(sp.get("phone"));
            user.setUtype(sp.get("utype"));
            user.setImg(sp.get("uimg"));
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
    }

    private void bindView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        login_bt = findViewById(R.id.login_bt);
        register_tv = findViewById(R.id.register_tv);
        username= findViewById(R.id.username);
        pwd= findViewById(R.id.pwd);
        ImageView clear = findViewById(R.id.clear);
        ImageView clear2= findViewById(R.id.clear2);
        Util.setEditEvent(username,clear);
        Util.setEditEvent(pwd,clear2);
        login_bt.setOnClickListener(v -> {
            String psw = pwd.getText().toString().trim();
            String userNameStr = username.getText().toString().trim();
            if (userNameStr.isEmpty()) {
                Toast.makeText(this, "UserName can not be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (psw.isEmpty()) {
                Toast.makeText(this, "Password can not be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            User userNew=new User();
            userNew.setUsername(username.getText().toString());
            userNew.setPassword(pwd.getText().toString());
            NetUitl.login(userNew, new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    User data= (User) obj;
                    user=data;
                    if(data!=null){
                        Util.l("  "+data.getUsername());
                        sp.put("isLogin",true+"");
                        sp.put("username",userNameStr);
                        sp.put("userId",data.getId()+"");
                        sp.put("phone",data.getPhone());
                        sp.put("utype",data.getUtype());
                        sp.put("uimg",data.getImg());
                        Util.userId=data.getId();
                        Util.userName=data.getUsername();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                    }else {
                        Util.show(getString(R.string.userNamePwdErr));
                    }
                }

                @Override
                public void onFail(Object obj) {
                    String msg= (String) obj;
                    Util.showErr(context,msg);
                }
            });

        });
        register_tv.setOnClickListener(v->{
            RegistActivity.type=1;
            Intent setPsw_intent = new Intent(LoginActivity.this, RegistActivity.class);
            startActivity(setPsw_intent);
        });
    }


       private void getPermission() {
        if (permissionList != null) {
            permissionList.clear();
        }
        //版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }

            if(!permissionList.isEmpty()){
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1000);
            }else{
                startActivity(new Intent(this, MainActivity.class));
                LoginActivity.this.finish();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            //权限请求失败
            if (grantResults.length > 0) {
                //存放没授权的权限
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission);
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    //说明都授权了
//                    getPic();
                } else {
                    //默认false 第二次提醒会有是否不在询问按钮，选择则为true  不在提醒是否申请权限
//                    if (!shouldShowRequestPermissionRationale(deniedPermissions.get(0))) {
////                        permissionDialog();
//                    }
                }
            }
        }
    }

}
