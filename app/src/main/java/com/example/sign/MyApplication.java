package com.example.sign;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.sign.net.Api;
import com.example.sign.service.Receiver1;
import com.example.sign.service.Receiver2;
import com.example.sign.service.Service1;
import com.example.sign.service.Service2;
import com.example.sign.util.DConst;
import com.example.sign.util.SharedPreferencesUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyApplication extends Application {
    private static MyApplication instance;

    private static Retrofit mRetrofit;
    private static Api mApi;
    private static String ipStr="192.168.0.108";
    private static SharedPreferencesUtils sp;
    @Override
    public void onCreate() {
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        super.onCreate();
        instance = this;
        sp=SharedPreferencesUtils.getInstance(DConst.SP_NAME);

        //初始化AutoSize
//        AutoSizeConfig.getInstance();
//
//        //配置示例
//        AutoSizeConfig.getInstance()
//                .setBaseOnWidth(true)
//                .setUseDeviceSize(false)
//                .setCustomFragment(false);
    }
    public static Context getInstance(){
        return instance;
    }
    public static SharedPreferencesUtils getSp(){
        return sp;
    }
//    @Override

//    protected void attachBaseContext(Context base) {
//
//        super.attachBaseContext(base);
//
////        MultiDex.install(this);
//
//    }

    public static Api getNet() {
        if(mApi!=null){
            return mApi;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
//                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----err =", e.getMessage());
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        Interceptor intercepto = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
//                        .addHeader("Cookie", "add cookies here")
//                        .build();
//                return chain.proceed(request);
//            }
//
//        };


        OkHttpClient.Builder client = new OkHttpClient.Builder();

//        client.addInterceptor(interceptor);

//        client.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Response response = chain.proceed(chain.request());
//                long responseTime = System.currentTimeMillis();
//                boolean successful = response.isSuccessful();
//                HttpUrl responseUrl = response.request().url();
//                long contentLength = response.body().contentLength();
//                //此处本来是想用 contentLength 来代替 1000 * 1000(会导致内容不完整) .但是contentLength 返回 -1 。会导致奔溃。
//                ResponseBody responseBody = response.peekBody(1000 * 1000);
//                String bodyInfo = responseBody.string();
//                Log.e("ResponseBody==", bodyInfo);
//                return chain.proceed(chain.request());
//            }
//        });
//        client.addInterceptor();
        OkHttpClient okHttpClient = client.build();

        ////步骤4:构建Retrofit实例
        mRetrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl(Api.IP)
//                .baseUrl("http://47.252.35.202:8080/")
//                .baseUrl("http://192.168.0.109:8083/")
                .client(okHttpClient)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);

        return mApi;
    }

}
