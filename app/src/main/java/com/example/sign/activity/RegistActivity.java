package com.example.sign.activity;

import static com.example.sign.util.img.TakePhotoUtil.REQUEST_ALBUM_N;
import static com.example.sign.util.img.TakePhotoUtil.REQUEST_CAMERA;

import android.content.Intent;
import android.net.Uri;
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
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sign.R;
import com.example.sign.adapter.spAdapter;
import com.example.sign.bean.Shop;
import com.example.sign.net.Api;
import com.example.sign.net.OnBack;
import com.example.sign.util.img.ImgCallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.ShowBottomEditDialog;
import com.example.sign.util.Util;
import com.example.sign.util.img.TakePhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class RegistActivity extends BaseActivity {
    EditText name;
    EditText pwd;
    EditText phone;
    List<Shop> spData;
    spAdapter adapter;
    Shop shop;
    LinearLayout header_in;

    ImageView back;
    private ImageView img;
    public static String url = null;
    /**
     * 1注册，2修改
     */
    public static int type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        name = findViewById(R.id.ed_login_user);
        pwd = findViewById(R.id.ed_login_password);
        phone = findViewById(R.id.et_phone);

        ImageView clear = findViewById(R.id.clear);
        ImageView clear2 = findViewById(R.id.clear2);
        ImageView clear3 = findViewById(R.id.clear3);
        Util.setEditEvent(name, clear);
        Util.setEditEvent(pwd, clear2);
        Util.setEditEvent(phone, clear3);

        back = findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(view -> finish());
        TextView title = findViewById(R.id.title);
        header_in = findViewById(R.id.header_in);
        header_in.setBackgroundColor(getResources().getColor(R.color.transparent));
        img = findViewById(R.id.img);
        Button bt= findViewById(R.id.bt_register);

        if (type == 2) {
            title.setText(R.string.modify);
            name.setText(user.getUsername());
            phone.setText(user.getPhone());
            if (Util.isNotEmpty(BaseActivity.user.getImg())) {
                //圆角
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.user)
                        .circleCropTransform();
                Glide.with(context)
                        .load(Api.getImgUrl(BaseActivity.user.getImg()))
                        .apply(options)
                        .into(img);
            }
            bt.setText(R.string.modify);
        } else {
            setImg(null);
            title.setText(getString(R.string.register));
            bt.setText(R.string.register);
        }


        initSpinner();

        bt.setOnClickListener(view -> {
//            if (TextUtils.isEmpty(url)
//            ) {
//                Toast.makeText(RegistActivity.this, "Add header image please.", Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (TextUtils.isEmpty(name.getText().toString())
                    ||TextUtils.isEmpty(pwd.getText().toString())
            ) {
                Toast.makeText(RegistActivity.this, "Name or password can not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setUsername(name.getText().toString());
            user.setPassword(pwd.getText().toString());
            user.setPhone(phone.getText().toString());
            user.setImg(url);
            Util.l(url);
            NetUitl.addUser(user, new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    if (obj != null) {
                        int code = (int) obj;
                        if (code == 200) {
                            if (type == 1) {
                                Util.show(getString(R.string.registerSuccess));
                                user.setId(null);
                                finish();
                            }else {
                                Util.show("Modify success!");
                                Util.userName=user.getUsername();
                            }
                        } else {

                        }
                    } else {
                        Util.show(getString(R.string.userNameExsit));
                    }
                }

                @Override
                public void onFail(Object obj) {
                    Util.show("Error:" + (String) obj);
                }
            });
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBottomEditDialog showBottomEditDialog = new ShowBottomEditDialog();
                showBottomEditDialog.BottomDialog(RegistActivity.this, new ImgCallBack() {
                    @Override
                    public void onTakePic(Object o) {
                        takepic();
                    }

                    @Override
                    public void onPickPic(Object o) {
                        pick();

                    }
                });

            }
        });

    }

    private void pick() {
        TakePhotoUtil.startAlbum(RegistActivity.this);
    }

    String destinationFileName = System.currentTimeMillis() + ".png";

    private void takepic() {
        destinationFileName = System.currentTimeMillis() + ".png";
        TakePhotoUtil.startCamera(RegistActivity.this, destinationFileName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_ALBUM_N) {//相册数据返回
            setImg(data.getData());

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {//相机拍照返回
            File file = new File(TakePhotoUtil.getCacheDirectory(this, null) + "/" + destinationFileName);// 指定路径
            Uri dataUri = FileProvider.getUriForFile(this, TakePhotoUtil.PROVIDER, file);
            setImg(dataUri);
        }
    }

    public void setImg(Uri uri) {
        url = null;
        //圆角
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.user)
                .circleCropTransform();
        if (uri == null) {
            Glide.with(this)
                    .load(R.drawable.user)
                    .apply(options)
                    .into(img);
            return;
        }
        String path = TakePhotoUtil.getPath(this, uri);
        Util.l(path);
        NetUitl.upLoadFile(path, new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                url = (String) obj;
                Glide.with(context)
                        .load(Api.getImgUrl(url))
                        .apply(options)
                        .into(img);
            }

            @Override
            public void onFail(Object obj) {
                Glide.with(context)
                        .load(uri)
                        .apply(options)
                        .into(img);
            }
        });
    }


    private void initSpinner() {
        Spinner spinner = findViewById(R.id.sp);
        spData = new ArrayList<>();

        Shop shop = new Shop();
        shop.setName("User");
        shop.setAddress("zuhu");
        spData.add(shop);

        Shop shop2 = new Shop();
        shop2.setName("Administer");
        shop2.setAddress("fangdong");
        spData.add(shop2);

        adapter = new spAdapter(spData, this, null);//实例化适配器

        spinner.setAdapter(adapter);//给hero设置适配器
        String utype = sp.get("utype");
        if (!TextUtils.isEmpty(utype)) {
            user.setUtype(utype);
            for (int i = 0; i < spData.size(); i++) {
                if (spData.get(i).getAddress().equals(utype)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = (Shop) parent.getItemAtPosition(position);
                sp.put("utype", shop.getAddress());
                user.setUtype(shop.getAddress());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
