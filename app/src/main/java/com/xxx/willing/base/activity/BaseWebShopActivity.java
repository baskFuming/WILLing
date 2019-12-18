package com.xxx.willing.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.http.js.ShopJsVo;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;

import butterknife.BindView;

/**
 * @author FM
 * @desc GVI商城通用web页面
 * @date 2019-12-13
 */

public class BaseWebShopActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, String title, int id) {
        Intent intent = new Intent(activity, BaseWebShopActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getIntExtra("id", 0);
    }

    @BindView(R.id.use_help_web)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    private String title;
    private int id;

    @Override
    protected String initTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_shop;
    }

    @Override
    protected void initData() {
        initBundle();
        initDate();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initDate() {
        String token = SharedPreferencesUtil.getInstance().getString(SharedConst.ENCRYPT_VALUE_TOKEN);
        final WebSettings webSetting;
        webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);  //支持js
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);  //支持弹窗
        webSetting.setBlockNetworkImage(false);
        mWebView.post(() -> webSetting.setTextZoom(getWindow().getDecorView().getWidth() / 375 * 100));
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        String launcher = SharedPreferencesUtil.getInstance().getString(SharedConst.CONSTANT_LAUNCHER);
        try {
            launcher = launcher.split(",")[1];
        } catch (Exception e) {
            launcher = "zh";
        }
        mWebView.loadUrl(HttpConfig.SHOP_DETAIL_URL + "?language=" + launcher + "&id=" + id + "&token=" + token);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有证书
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                try {
                    ShopJsVo shopJsVo = new Gson().fromJson(message, ShopJsVo.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.GONE);
                    }
                } else {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.VISIBLE);
                        mProgress.setProgress(newProgress);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }


    @Override
    protected void onPause() {
        mWebView.reload();
        mWebView.onPause();
        super.onPause();
    }
}
