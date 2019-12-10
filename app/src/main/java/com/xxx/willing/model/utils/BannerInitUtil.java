package com.xxx.willing.model.utils;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoaderInterface;
import com.xw.banner.view.RoundAngleImageView;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义轮播图以及指示器样式
 */
public class BannerInitUtil {

    public static void init(Banner banner, List<String> list, int style, OnBannerListener onBannerListener) {
        List<String> title = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            title.add("");
        }
        //设置banner样式
        // 设置图片加载器
        if (style == 1) {
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setImageLoader((ImageLoaderInterface) new OrdinaryImageLoder());
        } else if (style == 2) {
            banner.setBannerStyle(BannerConfig.CUSTOM_INDICATOR);
            banner.setImageLoader(new CustomRoundedImageLoader());
        } else {
            banner.setBannerStyle(BannerConfig.CUSTOM_INDICATOR);
            banner.setImageLoader((ImageLoaderInterface) new OrdinaryImageLoder());
        }
        //设置图片网址或地址的集合
        banner.setImages(list);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                .setOnBannerListener(onBannerListener)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    private static class CustomRoundedImageLoader extends com.xw.banner.loader.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUtil.loadFillet(context, (String) path, imageView);
        }

        @Override
        public ImageView createImageView(Context context) {
            RoundAngleImageView roundedImg = new RoundAngleImageView(context);
            return roundedImg;
        }
    }

    private static class OrdinaryImageLoder extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into((ImageView) imageView);
        }
    }

}