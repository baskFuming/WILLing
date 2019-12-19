package com.xxx.willing.model.glide;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Glide工具类
 */
public class GlideFileUtil {

    public static void loadUrl(Context context, File file, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(file)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(imageView);
        }
    }

    public static void loadGif(Context context, File file, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(file)
                    .asGif()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(imageView);
        }
    }

    public static void loadCircle(Context context, File file, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(file)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(new CircleTransformation(context, imageView));
        }
    }

    public static void loadFillet(Context context, File file, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(file)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(new FilletTransformation(context, imageView));
        }
    }

    public static void loadBack(Context context, File file, int defaultImageId, View view) {
        if (context != null) {
            Glide.with(context)
                    .load(file)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(new BackTransformation(context, view));
        }
    }

}
