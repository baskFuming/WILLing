package com.xxx.willing.model.glide;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xxx.willing.R;

/**
 * Glide工具类
 */
public class GlideUrlUtil {

    /**
     * 加载Url图片
     */
    public static void load(Context context, String url, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(imageView);
        }
    }

    /**
     * 加载动图Url图片
     */
    public static void loadGif(Context context, String url, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .asGif()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(imageView);
        }
    }

    /**
     * 加载圆形Url图片
     */
    public static void loadCircle(Context context, String url, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(new CircleTransformation(context, imageView));
        }
    }

    /**
     * 加载圆角Url图片
     */
    public static void loadFillet(Context context, String url, int defaultImageId, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(new FilletTransformation(context, imageView));
        }
    }

    /**
     * 加载背景Url图片
     */
    public static void loadBack(Context context, String url, int defaultImageId, View view) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(defaultImageId)//设置加载中图片
                    .fallback(defaultImageId)
                    .error(defaultImageId)
                    .into(new BackTransformation(context, view));
        }
    }

}
