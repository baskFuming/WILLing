package com.xxx.willing.ui.app.vote.activity;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * @author FM
 * @desc 学习
 * @date 2019-12-03
 */

public class StudyActivity extends BaseActivity {
    public static void actionStart(Activity activity, String url) {
        Intent intent = new Intent(activity, StudyActivity.class);
        intent.putExtra("Url", url);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        intent.getStringExtra("Url");
    }

    private String userId;
    private String Url;
    private String restart = null;
    private String score = null;
    private int gameid;
    private String funName;
    private String gameName;
    private String amount;
    @BindView(R.id.main_title)
    TextView title;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    @BindView(R.id.game_web)
    WebView mWebView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected void initData() {
        initBundle();
    }

}
