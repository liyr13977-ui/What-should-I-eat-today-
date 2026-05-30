package com.example.orderfood.until;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileImageUntil {
    public static void saveImageUriToFile(Uri uri, Context context,String path) {
        CustomTarget<Bitmap> target = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                File file=new File(path);

                try{
                    FileOutputStream fos=new FileOutputStream(file);
                    resource.compress(Bitmap.CompressFormat.PNG,100,fos);
                    fos.flush();
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };

        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(target);
    }
    public static String getImageName(){
        String pictureName="/"+ UUID.randomUUID().toString().replace("-","")+".png";//图片名字，进行拼接
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+pictureName;
    }




}
