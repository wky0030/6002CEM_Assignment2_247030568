package com.example.sign.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign.MyApplication;
import com.example.sign.R;
import com.example.sign.activity.MainActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Util {
    //声明数据库帮助器的实例
    private static UserDBHelper userDBHelper;
    public static String userName="";
    public static Integer deviceId=0;
    public static String token="";
    public static int shopId=0;
    public static String sign="";
    public static int userId;

    public Util(Context c) { //获得数据库帮助器的实例
        if (userDBHelper == null) {
            userDBHelper = UserDBHelper.getInstance(c, 1);
        }
    }
    private static void getInstance(Context c) { //获得数据库帮助器的实例
        if (userDBHelper == null) {
            userDBHelper = UserDBHelper.getInstance(c, 1);
        }
    }

    @SuppressLint("DefaultLocale")
    public static  boolean Login(Context c,String name,String pwd ) {
        getInstance(c);
        //打开数据库帮助器的读连接
        userDBHelper.openReadLink();
        // 执行数据库帮助器的查询操作
        List<UserInfo> userInfoList = userDBHelper.query("1=1");
        boolean isLogin=false;
        for (int i = 0; i < userInfoList.size(); i++) {
            UserInfo userInfo = userInfoList.get(i);
            if(!isLogin&&name.equals(userInfo.name)&&pwd.equals(userInfo.password)){
                isLogin=true;
                userName=userInfo.name;
                sign=userInfo.sign;
            }
        }
        userDBHelper.closeLink();
        return isLogin;
    }
    public static void log(Object o){
        Log.i("tt",String.valueOf(o));
    }

    public static boolean regist(Context c,String name, String pwd) {
        List<UserInfo> all = getAll(c);
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).name.equals(name)){
                showAlert(c,"用户用重复",null);
                return false;
            }
        }
        getInstance(c);
        userDBHelper.openReadLink();
        UserInfo userInfo = new UserInfo();
        userInfo.password = pwd;
        userInfo.name = name;
        long result = userDBHelper.insert(userInfo);
        userDBHelper.closeLink();
        if(result==-1){
            show("注册失败");
        }else {
            show("注册成功");
        }
        return true;

    }
    public static List<UserInfo> getAll(Context c) {
        List<UserInfo> data;
        getInstance(c);
        userDBHelper.openReadLink();
        data=userDBHelper.query(null);
        userDBHelper.closeLink();
    return data;
    }
    public static void delete(Context c,String id) {
        getInstance(c);
        userDBHelper.openReadLink();
        userDBHelper.deleteById(id);
        userDBHelper.closeLink();
    }
    public static int update(Context c,long id,String sign) {
        int rusult=-1;
        List<UserInfo> all = getAll(c);
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).sign!=null&&sign!=null&&all.get(i).sign.equals(sign)){
                return rusult;
            }
        }
        getInstance(c);
        userDBHelper.openReadLink();
        rusult= userDBHelper.update(sign," _id=" + id);
        userDBHelper.closeLink();
        return rusult;
    }
    public static void show( String name) {
        Toast.makeText(MyApplication.getInstance(), name, Toast.LENGTH_SHORT).show();
    }
    public static  void showAlert(Context c,String msg,OnClick onClick){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Tips");
        builder.setMessage(msg);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        if(onClick!=null){
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(onClick!=null){
                        onClick.onDo();
                    }
                    dialog.dismiss();
                }
            });
        }

        //设置反面按钮
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(c, "你点击了不是", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        //显示对话框
        dialog.show();
    }


    /**
     * 自定义对话框
     */
    public static void showDialog(Context c,String title,String content,CallBack callBack) {
        final Dialog dialog = new Dialog(c, R.style.NormalDialogStyle);
        View view = View.inflate(c, R.layout.dialog_normal, null);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confirm = view.findViewById(R.id.confirm);
        TextView titletv = view.findViewById(R.id.title);
        TextView contenttv = view.findViewById(R.id.content);
        titletv.setText(title);
        cancel.setText("取消");
        confirm.setText("确定");
        contenttv.setText(content);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(true);
        //设置对话框的大小
//        view.setMinimumHeight((int) (DpUtil.getScreenHeight(c) * 0.2f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (DpUtil.getScreenWidth(c) * 0.75f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callBack.onDo(null);
            }
        });
        dialog.show();
    }
    public static void l(Object msg){
        Log.e("解析","消息="+String.valueOf(msg));
    }
    public static void showErr(Context context,Object msg){
        if(context!=null){
            Activity activity= (Activity) context;
            activity.runOnUiThread(() -> Toast.makeText(context,msg==null?"":String.valueOf(msg),Toast.LENGTH_LONG).show());
        }
    }

    /**
     * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
     *
     * @param content 将要生成一维码的内容
     * @return 返回生成好的一维码bitmap
     * @throws WriterException WriterException异常
     */
    public static Bitmap CreateOneDCode(String content, int w, int h) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, w, h);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (matrix.get(x, 0)) {
                    pixels[y * width + x] = 0xff000000;
                }
                else pixels[y * width + x] = 0xffffffff;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 将字符串转成图片
     * @param text
     * @param width
     * @param height
     * @return
     */
    private  static final int TXT_SIZE = 44;
    private  static final int FTXT_SIZE = 50;
    private  static final int y_offset = 10;
    public static Bitmap StringToBitmap(String text, int width, int height) {
        Bitmap newBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(newBitmap, 0, 0, null);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(FTXT_SIZE);
        textPaint.setStrokeWidth(20);
        textPaint.setColor(Color.BLACK);
        StaticLayout sl= new StaticLayout(text, textPaint, newBitmap.getWidth()-8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.translate(0, y_offset);
        canvas.drawColor(Color.WHITE);
        sl.draw(canvas);
        return newBitmap;
    }


    /**
     * 连接两个bitmap
     *  以第一个图为准
     * 优化算法  1.图片不需要铺满，只需要以统一合适的宽度。然后让imageview自己去铺满，不然长图合成长图会崩溃，这里以第一张图为例
     *2.只缩放不相等宽度的图片。已经缩放过的不需要再次缩放
     * @param bit1
     * @param bit2
     * @return
     */
    public static Bitmap CombBitmap(Bitmap bit1, Bitmap bit2) {
        Bitmap newBit = null;
        int width = bit1.getWidth();
        if (bit2.getWidth() != width) {
            int h2 = bit2.getHeight() * width / bit2.getWidth();
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            Bitmap newSizeBitmap2 = getNewSizeBitmap(bit2, width, h2);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(newSizeBitmap2, 0, bit1.getHeight(), null);
        } else {
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + bit2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(bit2, 0, bit1.getHeight(), null);
        }
        return newBit;
    }

    /**
     * 获取新的bitmap
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap getNewSizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bit1Scale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
        return bit1Scale;
    }

    /**
     *  创建带文字的一维码
     * @param content
     * @param w
     * @param h
     * @param stringPrefix
     * @return
     * @throws WriterException
     */
    public static Bitmap CreateOneDCodeAndString(String content, int w, int h, String stringPrefix) throws WriterException {
        return CombBitmap(CreateOneDCode(content, w, h-TXT_SIZE-y_offset), StringToBitmap(stringPrefix + content, w, FTXT_SIZE + y_offset));
    }
    public static String getValue(String value,String pre,String suffix){
        if(value==null){
            return "";
        }
        return pre+value+suffix;
    }
    public static String getValue(String value){
        if(value==null){
            return "";
        }
        return value;
    }
    public static boolean isNotEmpty(String el){
        if(el==null||el.trim().equals("")){
            return false;
        }
        return true;
    }

    public static void setEditEvent(EditText username, ImageView clear) {

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str=s.toString();
                if(str.length()>0){
                    clear.setVisibility(View.VISIBLE);
                }else {
                    clear.setVisibility(View.GONE);
                }
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    clear.setVisibility(View.GONE);
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
            }
        });
    }

    public static final String FILE_NAME=File.separator+"Download/SaleLog.txt";
    public static void writeFileToLocalStorage(String content) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILE_NAME);
            FileWriter writer = new FileWriter(file, true);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content=sdf.format(new Date())+":"+content+"\n\n";
            writer.append(content);
            writer.flush();
            writer.close();
            // 文件写入成功
        } catch (IOException e) {
            e.printStackTrace();
            // 文件写入发生错误
        }
    }
    public static String readFileToLocalStorage() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILE_NAME);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {  //
                if (!"".equals(lineTxt)) {
                    sb.append(lineTxt+"\n");
                }
            }
            isr.close();
            br.close();
            return sb.toString();
            // 文件写入成功
        } catch (IOException e) {
            e.printStackTrace();
            // 文件写入发生错误
        }
        return "";
    }
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String formatTime(Long createTime) {
        if(createTime==null){
            return "";
        }else {
            return sdf.format(createTime);
        }
    }
}
