package com.xxx.willing.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xxx.willing.R;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;

import butterknife.BindView;

/**
 * @author FM
 * @desc 通用WEb
 * @date 2019-12-03
 */

public class BaseWebActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, String webUrl, String title) {
        Intent intent = new Intent(activity, BaseWebActivity.class);
        intent.putExtra("webUrl", webUrl);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    private void initBund() {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra("webUrl");
        title = intent.getStringExtra("title");
    }

    @BindView(R.id.use_help_web)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    private String webUrl;
    private String title;

    @Override
    protected String initTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_help;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        initBund();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        final WebSettings webSetting;
        webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);  //支持js
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);  //支持弹窗
        webSetting.setBlockNetworkImage(false);
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                webSetting.setTextZoom(getWindow().getDecorView().getWidth() / 375 * 100);
            }
        });
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
        mWebView.loadUrl(webUrl + launcher);

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
                    mProgress.setVisibility(View.GONE);
                } else {
                    mProgress.setVisibility(View.VISIBLE);
                    mProgress.setProgress(newProgress);
                }
            }
        });
    }
}
