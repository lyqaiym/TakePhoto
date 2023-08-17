package com.example.takephoto;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

public class TestTakePhoto {
    /**
     * 拍照获取图片
     */
    public void doTakePhoto(Context mContext) {
        try {
            String mFileName = System.currentTimeMillis() + ".jpg";
            File mPhotoDir = new File(mContext.getCacheDir(), "saveImage");
            if (!mPhotoDir.exists()) {
                mPhotoDir.mkdirs();
            }
            File mCurrentPhotoFile = new File(mPhotoDir, mFileName);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            if (Build.VERSION.SDK_INT < 24) {

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                Uri photoOutputUri = getUriForFile(mContext, mCurrentPhotoFile);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (!mPhotoDir.exists()) {
                    mPhotoDir.mkdirs();
                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //拍照图片保存到指定的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (!mPhotoDir.exists()) {
                    mPhotoDir.mkdirs();
                }
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            }
            ((Activity) mContext).startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取uri
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = getUriForFile24(context, file);
            } else {
                fileUri = Uri.fromFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUri;
    }

    /**
     * android 7.0以上获取uri的方法
     *
     * @param context
     * @param file
     * @return
     */
    private static Uri getUriForFile24(Context context, File file) {
        String appid = context.getPackageName();
        Uri fileUri = FileProvider.getUriForFile(context, appid + ".fileProvider", file);
        return fileUri;
    }
}
