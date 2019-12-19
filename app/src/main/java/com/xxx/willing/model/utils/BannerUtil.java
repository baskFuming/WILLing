package com.xxx.willing.model.utils;

import android.content.Context;
import android.widget.ImageView;

import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图工具类
 */
public class BannerUtil {

    public static void init(Banner banner, List<String> list, OnBannerListener onBannerListener) {
        List<String> title = new ArrayList<>();
        if (list == null) return;
        for (int i = 0; i < list.size(); i++) {
            title.add("");
        }
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置指示器在下方
        banner.setImageLoader(new MyLoader());
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
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(onBannerListener)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    //自定义的加载器
    private static class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUrlUtil.loadFillet(context, HttpConfig.HTTP_IMG_URL + path, R.drawable.shape_back_not_data, imageView);
        }
    }

}