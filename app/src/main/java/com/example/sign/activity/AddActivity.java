package com.example.sign.activity;

import static com.example.sign.util.img.TakePhotoUtil.REQUEST_ALBUM_N;
import static com.example.sign.util.img.TakePhotoUtil.REQUEST_CAMERA;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.sign.R;
import com.example.sign.adapter.spAdapter;
import com.example.sign.bean.School;
import com.example.sign.bean.Shop;
import com.example.sign.net.Api;
import com.example.sign.net.OnBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.ShowBottomEditDialog;
import com.example.sign.util.Util;
import com.example.sign.util.img.ImgCallBack;
import com.example.sign.util.img.TakePhotoUtil;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class AddActivity extends BaseActivity {
    EditText price,area, addressEt,des;
    List<Shop> spData;
    spAdapter adapter;
    Shop shop;
    LinearLayout header_in;

    ImageView back;
    private ImageView img;
    private String url=null;
    School school =new School();
    Button submit;

    double latitude=0;
    double longitude=0;
    String myAddress="";
    /**
     * 1添加，2修改 3查看
     */
    public static int type=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        price=findViewById(R.id.et_price);
        area=findViewById(R.id.et_area);
        des=findViewById(R.id.et_des);
        addressEt =findViewById(R.id.et_address);

        ImageView clear = findViewById(R.id.clear);
        ImageView clear3= findViewById(R.id.clear3);
        ImageView clear4= findViewById(R.id.clear4);
        Util.setEditEvent(price,clear);
        Util.setEditEvent(area,clear3);
        Util.setEditEvent(des,clear4);
        submit=findViewById(R.id.bt_register);

        back= findViewById(R.id.back_btn);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.back);
        back.setOnClickListener(view -> finish());
        TextView title=findViewById(R.id.title);
        header_in=findViewById(R.id.header_in);
        header_in.setBackgroundColor(getResources().getColor(R.color.transparent));
        img=findViewById(R.id.img);
        setImg(null);
        Util.l(" typd="+type);
        if(type==1){
            title.setText("Add new VTC");
        }else if(type==2){
            title.setText("Modify VTC");
            getInfo();
        }else if(type==3){
            title.setText("VTC information");
            price.setEnabled(false);
            addressEt.setEnabled(false);
            area.setEnabled(false);
            des.setEnabled(false);
            img.setClickable(false);
            getInfo();
            submit.setText("JOIN");
            clear.setVisibility(View.GONE);
            clear3.setVisibility(View.GONE);
            clear4.setVisibility(View.GONE);
        }
        setText(price,123+"");
        setText(addressEt,"");
        setText(area,"100");
        setText(des,"des");

        findViewById(R.id.share).setOnClickListener(v->{
            share("Tuition fee："+Util.getValue(school.getContent())+"\n"
                    +"Address："+Util.getValue(school.getContent2())+"\n"
                    +"Area:"+Util.getValue(school.getContent3())+"\n"
                    +"Description："+Util.getValue(school.getDes())+"\n"
            );
        });

        ImageView right_btn= findViewById(R.id.right_btn);
        if(type==3){
            right_btn.setVisibility(View.VISIBLE);
        }
        right_btn.setOnClickListener(v-> {
            NetUitl.addSave(school.getId(), !school.isSave, new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    school.isSave = !school.isSave;
                    if (school.isSave) {
                        right_btn.setImageResource(R.drawable.stars);
                    } else {
                        right_btn.setImageResource(R.drawable.star);
                    }
                }

                @Override
                public void onFail(Object obj) {

                }
            });
        });

        submit.setOnClickListener(view -> {
            Util.l("my latitude="+latitude+" longitude="+longitude);
            if(latitude==0||longitude==0){
                Util.showErr(this,"click location image to select position.");
                return;
            }
            if(type==3){
                TalkActivity.fid= school.getId();
                Intent intent=new Intent(this,TalkActivity.class);
                startActivity(intent);
                return;
            }
            if(TextUtils.isEmpty(price.getText().toString())) {
                Toast.makeText(AddActivity.this, "Tuition fee can not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            school.setUid(Util.userId);
            school.setAlias(user.getImg());
            school.setContent(price.getText().toString());
            school.setContent3(area.getText().toString());
            school.setDes(des.getText().toString());
            school.setName(Util.getValue(Util.userName));
            school.setImgs(url);
            school.setCreateTime(new Date().getTime());
            school.setLatitude(latitude);
            school.setLongitude(longitude);
            school.setContent2(myAddress);
            NetUitl.addFanZi(school,new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    if(obj!=null){
                        boolean isSuc= (boolean) obj;
                        if(isSuc){
                            if(type==1){
                                Util.show("Saved success!");
                            }else {
                                Util.show("Modified");
                            }
                        }else {

                            if(type==1){
                                Util.show("Save failed.");
                            }else {
                                Util.show("Modify failed.");
                            }
                        }
                    }

                }

                @Override
                public void onFail(Object obj) {
                    Util.show("Error:"+(String)obj);
                }
            });
    });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==3){
                    return;
                }
                ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
                showBottomEditDialog.BottomDialog(AddActivity.this, new ImgCallBack() {
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

        findViewById(R.id.loc_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddActivity.this,MapActivity.class),100);
            }
        });

    }


    private void share(String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 设置分享类型为文本
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText); // 设置要分享的文本内容
        startActivity(Intent.createChooser(shareIntent,"Share to:"));
    }
    public static String fid=null;
    private void getInfo() {
        if(fid==null){
            return;
        }
        NetUitl.getSchool(fid, new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                school = (School) obj;
                latitude=school.getLatitude();
                longitude=school.getLongitude();
                setText(price, school.getContent());
                setText(addressEt, school.getContent2());
                setText(area, school.getContent3());
                setText(des, school.getDes());
                Glide.with(context)
                        .load(Api.getImgUrl(school.getImgs()))
                        .placeholder(R.drawable.b1)
                        .into(img);
                Util.l(school.getName());
                Util.l(school.getCreateTime());
                if(type==3){
                    TextView name=findViewById(R.id.name);
                    name.setText("Send:"+Util.getValue(school.getName()));
                    TextView time=findViewById(R.id.time);
                    time.setText("Send time:\n"+Util.formatTime(school.getCreateTime()));
                }
            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }

    private void pick() {
        TakePhotoUtil.startAlbum(AddActivity.this);
    }

    String destinationFileName = System.currentTimeMillis() + ".png";
    private void takepic() {
        destinationFileName = System.currentTimeMillis() + ".png";
        TakePhotoUtil.startCamera(AddActivity.this, destinationFileName);

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
        }else if (resultCode == RESULT_OK && requestCode == 100) {//选择位置返回
             latitude=data.getDoubleExtra("latitude",0);
             longitude=data.getDoubleExtra("longitude",0);
            Util.l("onActivityResult longitude="+longitude+" latitude="+latitude);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String address=getAddressFromLocation(AddActivity.this,latitude,longitude);
                    Util.l("address="+address);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myAddress=address;
                            setText(addressEt,address);
                        }
                    });
                }
            }).start();
            // 使用方法2
//            getAddressUsingApi(31.2304, 121.4737, new GeocodingCallback() {
//                @Override
//                public void onSuccess(String address) {
//                    Util.l("API Address"+ address);
//                    myAddress=address;
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    Util.l("API Error"+ error);
//                }
//            });
        }
    }
    public void setImg(Uri uri){
        url=null;
        if(uri==null){
            Glide.with(this)
                    .load(R.drawable.fangzi)
                    .into(img);
            return;
        }
        String path = TakePhotoUtil.getPath(this, uri);
        Util.l(path);
        NetUitl.upLoadFile(path, new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                url= (String) obj;
                Glide.with(context)
                        .load(Api.getImgUrl(url))
                        .into(img);
            }

            @Override
            public void onFail(Object obj) {
                Glide.with(context)
                        .load(uri)
                        .into(img);
            }
        });


    }
    public String getAddressFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressText = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // 构建地址字符串
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        sb.append(", ");
                    }
                }

                addressText = sb.toString();

                // 或者获取地址的各个组成部分
            /*
            String thoroughfare = address.getThoroughfare(); // 街道
            String subThoroughfare = address.getSubThoroughfare(); // 门牌号
            String locality = address.getLocality(); // 城市
            String adminArea = address.getAdminArea(); // 省/州
            String countryName = address.getCountryName(); // 国家
            String postalCode = address.getPostalCode(); // 邮编
            */
            }
        } catch (IOException e) {
            e.printStackTrace();
            addressText = "Can not get address.";
        }

        return addressText;
    }
    public interface GeocodingService {
        @GET("json")
        Call<GeocodingResponse> reverseGeocode(
                @Query("latlng") String latlng,
                @Query("key") String apiKey,
                @Query("language") String language);
    }

    public class GeocodingResponse {
        @SerializedName("results")
        public List<Result> results;

        public  class Result {
            @SerializedName("formatted_address")
            public String formattedAddress;
        }
    }

    public void getAddressUsingApi(double lat, double lng, GeocodingCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeocodingService service = retrofit.create(GeocodingService.class);

        String latlng = lat + "," + lng;
        String apiKey = "YOUR_API_KEY"; // 替换为你的API密钥

        service.reverseGeocode(latlng, apiKey, "zh-CN").enqueue(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                if (response.isSuccessful() && response.body() != null
                        && !response.body().results.isEmpty()) {
                    String address = response.body().results.get(0).formattedAddress;
                    callback.onSuccess(address);
                } else {
                    callback.onFailure("无法获取地址");
                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface GeocodingCallback {
        void onSuccess(String address);
        void onFailure(String error);
    }
}
