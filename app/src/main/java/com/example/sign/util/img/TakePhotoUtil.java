package com.example.sign.util.img;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.sign.util.Util;

import java.io.File;
import java.io.IOException;


/**
 * 打开相册，调用摄像头拍照
 * 使用步骤：
 * 1.调用startCamera或者startAlbum。分别为打开相机和相册。
 * 2.在onActivityResult监听相应的REQUEST
 * <p>
 * <p>
 * 可以从getTempTakePhotoUri获得默认的相机照相后的uri。注：相机和裁剪返回的data均有可能为Null所以均未使用
 * 若是从相册裁剪出来，可以用data.getdata获得uri。
 */
public class TakePhotoUtil {


    public final static int REQUEST_CAMERA = 1000;
    public final static int REQUEST_ALBUM = 1001;
    public final static int REQUEST_ALBUM_N = 10011;//7.0
    public final static int REQUEST_CROP = 1002;
    public final static String PROVIDER = "com.example.sign.fileprovider";


    /**
     * 打开相机
     */
    public static Uri startCamera(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = getTempTakePhotoUri(activity);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
        return uri;
    }

    /**
     * 调用相机拍照，文件名为filename，目录为TakePhotoUtil.getCacheDirectory
     *
     * @param activity
     * @param filename
     */
    public static Uri startCamera(Activity activity, String filename) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        Uri uri = getTakePhotoUri(activity, filename);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
        return uri;
    }

    /**
     * 打开图库
     */
    public static void startAlbum(final Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = getExternalCacheDirectory(activity, Environment.DIRECTORY_PICTURES);
            Uri dataUri = FileProvider.getUriForFile(activity, PROVIDER, new File
                    (file.getPath()));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, dataUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {  //存在
                activity.startActivityForResult(intent, REQUEST_ALBUM_N);
            } else {    //不存在
                Toast.makeText(activity, "打开相册失败！", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (intent.resolveActivity(activity.getPackageManager()) != null) {  //存在
                activity.startActivityForResult(intent, REQUEST_ALBUM);
            } else {    //不存在
                Toast.makeText(activity, "打开相册失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 调用系统剪切
     *
     * @param uri      被裁剪文件uri
     * @param activity
     * @return 返回裁剪后文件path
     */
    public static String cropImageUri(Uri uri, Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
  //      intent.putExtra("outputX", 720);
  //      intent.putExtra("outputY", 720);
        intent.putExtra("scale", false);
        intent.putExtra("return-data", false); //返回数据bitmap
        Uri temp = getTempCropUri(activity);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, temp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        try {
            activity.startActivityForResult(intent, REQUEST_CROP);
        } catch (Exception e) {
            Log.d("TakePhotoUtil", e.getMessage());
        }
        return temp.getPath();
    }


    /**
     * 删除拍照后保存的临时文件（在裁剪后调用）
     */
    public void deleteTakePhotoFile(Context context) {
        deleteTakePhotoFile("temp1.jpg",context);
    }

    private void deleteTakePhotoFile(String filename,Context context) {
        File file = new File(getCacheDirectory(context, "").getPath() + "/"
                + filename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 裁剪默认的uri
     *
     * @param context
     * @return
     */
    public static Uri getTempCropUri(Context context) {
        return Uri.fromFile(getTempCropFile(context));
    }


    /**
     * 裁剪默认的文件
     *
     * @param context
     * @return
     */
    public static File getTempCropFile(Context context) {
        File f = new File(getCacheDirectory(context, ""), "temp.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 返回调用拍照默认的uri
     *
     * @param context
     * @return
     */
    public static Uri getTempTakePhotoUri(Activity context) {
        return getTakePhotoUri(context, "temp1.jpg");
    }

    /**
     * 返回调用拍照后保存文件的uri
     *
     * @param activity
     * @param filename
     * @return
     */
    private static Uri getTakePhotoUri(Activity activity, String filename) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(activity, PROVIDER, new File(getCacheDirectory(activity, ""), filename));
        } else {
            return Uri.fromFile(new File(getCacheDirectory(activity, ""), filename));
        }

    }


    /**
     * 返回调用拍照默认的文件
     *
     * @param context
     * @return
     */
    private static File getTempTakePhotoFile(Context context) {
        File f = new File(getCacheDirectory(context, ""), "temp1.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * <br>功能简述:4.4及以上获取图片的方法
     * <br>功能详细描述:
     * <br>注意:
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPath(final Context context, final Uri uri) {
      //  uri.getAuthority()
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            //处理华为没有资源id，带的是文件名称  content://com.huawei.hidisk.fileprovider/root/storage/emulated/0/tencent/QQfile_recv/www.xls

            if( uri.getHost().contains("fileprovider")){
              //  String temp = uri.toString();
                //String url="/storage/emulated/0/tencent/QQfile_recv/www.xls";
                String uri_1=uri.getPath();
                String url=uri_1.substring(5, uri_1.length());

                return url;
            }

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    //获取行数据
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
               final int index = cursor.getColumnIndexOrThrow(column);
                //final int index = cursor.getColumnIndex(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(Context context, String type) {
        File appCacheDir = getExternalCacheDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type);
        }

        if (appCacheDir == null) {
            Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is mobile phone " +
                    "unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is make directory " +
                        "fail !");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为
     *                .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.getExternalCacheDir();
            } else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null) {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/"
                        + context.getPackageName() + "/cache/" + type);
            }

            if (appCacheDir == null) {
                Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard " +
                        "unknown exception !");
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is make " +
                            "directory fail !");
                }
                Log.d("TakePhotoUtil", "getExternalCacheDirectory: appCacheDir = " + appCacheDir
                        .getPath());
            }
        } else {
            Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard " +
                    "nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File appCacheDir;
        if (TextUtils.isEmpty(type)) {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            appCacheDir = new File(context.getFilesDir(), type);//
            // /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make " +
                    "directory fail !");
        }
        return appCacheDir;
    }

    public static Bitmap getBitmaoFromSd(String path) {
        Bitmap bitmap = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
