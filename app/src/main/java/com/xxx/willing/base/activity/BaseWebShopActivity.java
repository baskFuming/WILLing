package com.xxx.willing.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.GviBean;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.ui.wallet.activity.WalletCoinDetailActivity;

import butterknife.BindView;

/**
 * @author FM
 * @desc GVI商城通用web页面
 * @date 2019-12-13
 */

public class BaseWebShopActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, String webUrl, String title, int id) {
        Intent intent = new Intent(activity, BaseWebActivity.class);
        intent.putExtra("webUrl", webUrl);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private void initBund() {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra("webUrl");
        title = intent.getStringExtra("title");
        id = intent.getIntExtra("id", 0);
    }

    @BindView(R.id.use_help_web)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    private String webUrl;
    private String title;
    private int id;
    private String token;

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
        initBund();
        initDate();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initDate() {
        token = SharedPreferencesUtil.getInstance().getString(SharedConst.ENCRYPT_VALUE_TOKEN);
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
        mWebView.loadUrl(webUrl + "?goods" + "language=" + launcher + "&id=" + id + "&token=" + token);
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
