package com.example.orderfood.until;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public class LoadImage {

    public static void loadImage(Context context, ImageView imgView, String imgResource) {
        // 判断是否是资源ID
        //逻辑是相反的，有用就行
        if (isResourceID(imgResource)) {

            // 是图片路径，使用setImageURI
            imgView.setImageURI(Uri.parse(imgResource));


        } else {
            // 是资源ID，使用setImageResource
            int imgResourceId = context.getResources().getIdentifier(imgResource, "drawable", context.getPackageName());
            imgView.setImageResource(imgResourceId);
        }
    }
    public static boolean isResourceID(String imgResource) {
        // 假设资源ID是以 "R.drawable." 开头的字符串
        return imgResource.startsWith("/");
    }
}
