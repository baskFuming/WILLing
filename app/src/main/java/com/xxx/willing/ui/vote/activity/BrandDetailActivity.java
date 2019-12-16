package com.xxx.willing.ui.vote.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.utils.GlideUtil;

import butterknife.BindView;

public class BrandDetailActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, String title, String name, String content, String url) {
        Intent intent = new Intent(activity, BrandDetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        url = intent.getStringExtra("url");
    }

    @BindView(R.id.brand_detail_image)
    ImageView mImage;
    @BindView(R.id.brand_detail_name)
    TextView mName;
    @BindView(R.id.brand_detail_content)
    TextView mContent;

    private String name;
    private String title;
    private String content;
    private String url;

    @Override
    protected String initTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand_detail;
    }

    @Override
    protected void initData() {
        initBundle();

        mContent.setText(content);
        mName.setText(name);

        GlideUtil.loadFillet(this, url, mImage);
    }
}
