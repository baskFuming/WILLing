package com.xxx.willing.base.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xxx.willing.R;


/**
 * 需要Title展示的Activity
 */
public abstract class BaseTitleActivity extends BaseActivity {

    protected abstract String initTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //Title字体
            ((TextView) findViewById(R.id.main_title)).setText(initTitle());

            //返回点击事件
            findViewById(R.id.main_return).setOnClickListener(v -> {
                //结束页面
                finish();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
