package com.xxx.willing.model.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xxx.willing.R;

import java.io.File;

/**
 * Glide工具类
 */
public class GlideUtil {

    /**
     * 加载圆角图片
     */
    public static void load(Context context, String url, final ImageView imageView) {
        if (context != null) {
            Util.loadImage(Glide.with(context).load(url), imageView);
        }
    }

    public static void load(Context context, File url, final ImageView imageView) {
        if (context != null) {
            Util.loadImage(Glide.with(context).load(url), imageView);
        }
    }

    public static void loadCircle(Context context, String url, final ImageView imageView) {
        if (context != null) {
            Util.loadCircleImage(Glide.with(context).load(url), context, imageView);
        }
    }

    public static void loadFillet(Context context, String url, final ImageView imageView) {
        if (context != null) {
            Util.loadFilletImage(Glide.with(context).load(url), context, imageView);
        }
    }

    public static void loadBack(Context context, String url, final View view) {
        if (context != null) {
            Util.loadBackGround(Glide.with(context).load(url), context, view);
        }
    }

    public static void loadBack(Context context, File url, final View view) {
        if (context != null) {
            Util.loadBackGround(Glide.with(context).load(url), context, view);
        }
    }

    //工具类
    private static class Util {

        //加载原图
        private static void loadImage(DrawableTypeRequest<?> drawableTypeRequest, final ImageView imageView) {
            drawableTypeRequest
                    .error(R.mipmap.my_icon)//设置缓存
                    .into(imageView);
        }

        //加载圆形
        private static void loadCircleImage(DrawableTypeRequest<?> drawableTypeRequest, final Context context, final ImageView imageView) {
            drawableTypeRequest.asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .error(R.mipmap.my_icon)//设置缓存
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }

        //加载圆角
        private static void loadFilletImage(DrawableTypeRequest<?> drawableTypeRequest, final Context context, final ImageView imageView) {
            drawableTypeRequest.asBitmap()
                    .error(R.mipmap.my_icon)//设置缓存
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(10);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }


        //加载背景图
        private static void loadBackGround(DrawableTypeRequest<?> drawableTypeRequest, final Context context, final View view) {
            drawableTypeRequest.asBitmap()
                    .error(R.mipmap.my_icon)//设置缓存
                    .into(new SimpleTarget<Bitmap>() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(10);
                            view.setBackground(circularBitmapDrawable);
                        }
                    });
        }
    }

}
