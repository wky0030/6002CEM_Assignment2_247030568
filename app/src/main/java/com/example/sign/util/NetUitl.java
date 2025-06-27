package com.example.sign.util;

import android.util.Log;

import com.example.sign.MyApplication;
import com.example.sign.bean.School;
import com.example.sign.bean.Device;
import com.example.sign.bean.Msg;
import com.example.sign.bean.Shop;
import com.example.sign.bean.User;
import com.example.sign.net.Api;
import com.example.sign.net.Data;
import com.example.sign.net.MyResponse;
import com.example.sign.net.MyUser;
import com.example.sign.net.OnBack;
import com.example.sign.net.OnNetBack;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetUitl {
    private static final String TAG = "NET:";
    /**
     * 示例，get加载Json数据
     */
    public static void getShops(OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        //步骤6：对发送请求进行封装，传入接口参数
//        Map<String, Object> map = new HashMap<>();
//        map.put("pageNum", 1);
//        map.put("pageSize", 100);
//        Gson gson = new Gson();
//
//        String s = gson.toJson(device);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data<MyResponse<Shop>>> jsonDataCall = api.getAllShop(1,1000);


        //步骤7:发送网络请求(异步)
//        Log.e(TAG, "get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Data<MyResponse<Shop>>>() {

            @Override
            public void onResponse(Call<Data<MyResponse<Shop>>> call, Response<Data<MyResponse<Shop>>> response) {
                //步骤8：请求处理,输出结果
                Log.i("xpf","get回调成功:异步执行"+response.code());
                Data<MyResponse<Shop>> body = response.body();
                if (body == null) return;
                MyResponse<Shop> info = body.getData();
                if (info == null) return;
//                Log.i("xpf","get回调成功:异步执行"+info.getData().size());
                if(back!=null){
                    back.onSuccess(info.getData());
                }

            }

            @Override
            public void onFailure(Call<Data<MyResponse<Shop>>> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }
    /**
     * 示例，get加载Json数据
     */
    public static void login(User user, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        //步骤6：对发送请求进行封装，传入接口参数
//        Map<String, Object> map = new HashMap<>();
//        map.put("pageNum", 1);
//        map.put("pageSize", 100);
        Gson gson = new Gson();

        String s = gson.toJson(user);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data<MyUser>> jsonDataCall = api.login(body);
//
//
//        //步骤7:发送网络请求(异步)
        Log.e(TAG, "get == url：" + user.getUsername());
        jsonDataCall.enqueue(new Callback<Data<MyUser>>() {
            @Override
            public void onResponse(Call<Data<MyUser>> call, Response<Data<MyUser>> response) {
                //步骤8：请求处理,输出结果
                Data<MyUser> body = response.body();
                Log.i("xpf","get回调成功:异步执行"+response.code()+body.getData().getData());
                if(body.getData().getData()==null){
                    if(back!=null){
                        back.onFail("登陆失败");
                    }
                    return;
                }
                Log.i("xpf","get回调成功:异步执行"+body.getData().getData().getPhone());
                Log.i("xpf","get回调成功:异步执行"+body.getData().getData().getImg());
                Log.i("xpf","get回调成功:异步执行"+body.getData().getData().getUtype());
                Log.i("xpf","get回调成功:异步执行"+body.getData().getData().getUsername());
                Log.i("xpf","get回调成功:异步执行"+body.getData().getData().getId());
                if (body == null) return;
                if(back!=null){
                    back.onSuccess(body.getData().getData());
                }

            }

            @Override
            public void onFailure(Call<Data<MyUser>> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }

    /**
     * 示例，get加载Json数据
     */
    public static void updateDeviceById(Device device, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        //步骤6：对发送请求进行封装，传入接口参数
//        Map<String, Object> map = new HashMap<>();
//        map.put("pageNum", 1);
//        map.put("pageSize", 100);
        Gson gson = new Gson();

        String s = gson.toJson(device);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data<Object>> jsonDataCall = api.updateDeviceById(body);


        //步骤7:发送网络请求(异步)
//        Log.e(TAG, "get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Data<Object>>() {
            @Override
            public void onResponse(Call<Data<Object>> call, Response<Data<Object>> response) {
                //步骤8：请求处理,输出结果
                Log.i("xpf","get回调成功:异步执行"+response.code());
                Data<Object> body = response.body();
                Log.i("xpf","get回调成功:异步执行"+body);
                if (body == null) return;
                if(body.getData()==null){
                    back.onFail(null);
                    return;
                }
                if(back!=null&&body.getData()!=null){
                    back.onSuccess(body.getCode());
                }
            }

            @Override
            public void onFailure(Call<Data<Object>> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }

    /**
     * 示例，get加载Json数据
     */
    public static void addUser(User user, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        //步骤6：对发送请求进行封装，传入接口参数
//        Map<String, Object> map = new HashMap<>();
//        map.put("pageNum", 1);
//        map.put("pageSize", 100);
        Gson gson = new Gson();

        String s = gson.toJson(user);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data> jsonDataCall = api.addUser(body);


        //步骤7:发送网络请求(异步)
//        Log.e(TAG, "get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //步骤8：请求处理,输出结果
                Log.i("xpf","get回调成功:异步执行"+response.code());
                Data<Object> body = response.body();
                Log.i("xpf","get回调成功:异步执行"+body);
                if (body == null) return;
                if(body.getData()==null){
                    back.onFail(null);
                    return;
                }
                if(back!=null&&body.getData()!=null){
                    back.onSuccess(body.getCode());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }
    /**
     * 示例，get加载Json数据
     */
    public static void getData( String keyword,OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();

        Call<Data<MyResponse<School>>> jsonDataCall
                = api.getAllSchool(Util.userId +"，"+keyword);


        //步骤7:发送网络请求(异步)
//        Log.e(TAG, "get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Data<MyResponse<School>>>() {
            @Override
            public void onResponse(Call<Data<MyResponse<School>>> call, Response<Data<MyResponse<School>>> response) {
                //步骤8：请求处理,输出结果
                Log.e("xpf","get回调成功:异步执行"+response.code());
                Data<MyResponse<School>> body = response.body();
                if (body == null) return;
                Log.e("xpf","get回调成功:异步执行"+body.getMessage());
                if(body.getData()==null){
                    back.onFail(null);
                    return;
                }
                MyResponse<School> info = body.getData();
                if(back!=null){
                    back.onSuccess(info.getData());
                }
            }

            @Override
            public void onFailure(Call<Data<MyResponse<School>>> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());

                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }

    /**
     * 示例，get加载Json数据
     */
    public static void getDataSave( OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();

        Call<Data<MyResponse<School>>> jsonDataCall
                = api.getAllSchoolSave(Util.userId );
        //步骤7:发送网络请求(异步)
//        Log.e(TAG, "get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Data<MyResponse<School>>>() {
            @Override
            public void onResponse(Call<Data<MyResponse<School>>> call, Response<Data<MyResponse<School>>> response) {
                //步骤8：请求处理,输出结果
                Log.e("xpf","get回调成功:异步执行"+response.code());
                Data<MyResponse<School>> body = response.body();
                if (body == null) return;
                Log.e("xpf","get回调成功:异步执行"+body.getMessage());
                if(body.getData()==null){
                    back.onFail(null);
                    return;
                }
                MyResponse<School> info = body.getData();
                if(back!=null){
                    back.onSuccess(info.getData());
                }
            }

            @Override
            public void onFailure(Call<Data<MyResponse<School>>> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());

                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }

    /**
     * 示例，get加载Json数据
     */
    public static void upLoadFile( String path,OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
// 准备文件
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<Data<Map<String, Object>>> jsonDataCall
                = api.updateFile(filePart);

        doNet(jsonDataCall, new OnNetBack<Data<Map<String, Object>>>() {
            @Override
            public void onSuccess(Data<Map<String, Object>> obj) {
                if(obj.getData().containsKey("success")){
                    String url = (String) obj.getData().get("success");
                    Util.l(url);
                    back.onSuccess(url);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    public static <T> void doNet(Call jsonDataCall, OnNetBack<T> back) {
        jsonDataCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T > call, Response<T> response) {
                Log.e(TAG,"回调成功:异步执行"+response.code());
                //步骤8：请求处理,输出结果
                T  body = response.body();
                if (body == null) return;
                Log.e(TAG,"回调成功:body="+body.toString());
                if(back!=null){
                    back.onSuccess(body);
                }else {
                    back.onFail("fail");
                }
            }

            @Override
            public void onFailure(Call<T > call, Throwable t) {
                Log.e(TAG, "回调失败：" + t.getMessage() );
                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }

    public static void addSave(Integer id, boolean isSave,OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Call<Data> jsonDataCall = api.addSave(Util.userId+"，"+id+"，"+isSave);
        jsonDataCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //步骤8：请求处理,输出结果
                Log.i("xpf","get回调成功:异步执行"+response.code());
                Data<Object> body = response.body();
                Log.i("xpf","get回调成功:异步执行"+body);
                if (body == null) return;
                if(body.getData()==null){
                    back.onFail(null);
                    return;
                }
                if(back!=null&&body.getData()!=null){
                    back.onSuccess(body.isSuccess());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                if(back!=null){
                    back.onFail(t.getMessage());
                }
            }
        });
    }

    public static void addFanZi(School fangZi, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();

        Gson gson = new Gson();

        String s = gson.toJson(fangZi);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data> jsonDataCall
                = api.updateSchool(body,Util.userId);

        doNet(jsonDataCall, new OnNetBack<Data>() {
            @Override
            public void onSuccess(Data obj) {
                if(back!=null&&obj!=null){
                    Util.l(obj.isSuccess());
                    back.onSuccess(obj.isSuccess());
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });

    }

    /**
     * 示例，get加载Json数据
     */
    public static void getSchool(String id, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Call<Data<Map<String , School>>> jsonDataCall
                = api.getSchoolById(id);
        doNet(jsonDataCall, new OnNetBack<Data<Map<String , School>>>() {
            @Override
            public void onSuccess(Data<Map<String , School>> obj) {
                if(back!=null&&obj!=null){
                    back.onSuccess(obj.getData().get("data"));
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });

    }

    /**
     * 示例，get加载Json数据
     */
    public static void addMsg(Msg msg, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();

        Gson gson = new Gson();

        String s = gson.toJson(msg);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data> jsonDataCall
                = api.addMsg(body);
        doNet(jsonDataCall, new OnNetBack<Data<School>>() {
            @Override
            public void onSuccess(Data<School> obj) {
                if(back!=null&&obj!=null){
                    back.onSuccess(obj.getData());
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });

    }

    public static void getAllMsgByFid(Msg msg, OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Gson gson = new Gson();

        String s = gson.toJson(msg);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);

        Call<Data<MyResponse<Msg>>> jsonDataCall
                = api.getAllMsgByFid(body);
        doNet(jsonDataCall, new OnNetBack<Data<MyResponse<Msg>>>() {
            @Override
            public void onSuccess(Data<MyResponse<Msg>> obj) {
                if(back!=null&&obj!=null&&obj.getData().getData()!=null){
                    back.onSuccess(obj.getData().getData());
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });
    }
    public static void getAllMsg( OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Call<Data<MyResponse<Msg>>> jsonDataCall
                = api.getAllMsg(Util.userId);
        doNet(jsonDataCall, new OnNetBack<Data<MyResponse<Msg>>>() {
            @Override
            public void onSuccess(Data<MyResponse<Msg>> obj) {
                if(back!=null&&obj!=null&&obj.getData()!=null){
                    back.onSuccess(obj.getData().getData());
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });
    }

    public static void deleteFangZi(Integer id,OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Call<Data> jsonDataCall
                = api.deleteSchool(id);
        doNet(jsonDataCall, new OnNetBack<Data>() {
            @Override
            public void onSuccess(Data obj) {
                if(back!=null&&obj!=null&&obj.getData()!=null){
                    back.onSuccess(obj.getData());
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });
    }
    public static void deleteMsg(Msg msg,OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Gson gson = new Gson();
        String s = gson.toJson(msg);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        Call<Data> jsonDataCall
                = api.deleteMsgById(body);
        doNet(jsonDataCall, new OnNetBack<Data>() {
            @Override
            public void onSuccess(Data obj) {
                if(back!=null&&obj!=null&&obj.getData()!=null){
                    back.onSuccess(obj.getData());
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });
    }

    public static void getAbout( OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Call<Data<Map<String ,String>>> jsonDataCall
                = api.getAbout();
        doNet(jsonDataCall, new OnNetBack<Data<Map<String ,String>>>() {
            @Override
            public void onSuccess(Data<Map<String ,String>> obj) {
                if(back!=null&&obj!=null){
                    back.onSuccess(obj.getData().get("success"));
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });

    }
    public static void getNews( OnBack back) {
        // 步骤5:创建网络请求接口对象实例
        Api api = MyApplication.getNet();
        Call<Data<Map<String ,String>>> jsonDataCall
                = api.getNews();
        doNet(jsonDataCall, new OnNetBack<Data<Map<String ,String>>>() {
            @Override
            public void onSuccess(Data<Map<String ,String>> obj) {
                if(back!=null&&obj!=null){
                    back.onSuccess(obj.getData().get("success"));
                }
            }

            @Override
            public void onFail(String msg) {
                back.onFail(msg);
            }
        });

    }
}
