package com.xxx.willing.ui.main.wheels;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.ui.main.MainActivity;

import butterknife.BindView;

/**
 * @author FM
 * @desc 引导页面
 * @date 2019-12-13
 */

public class GuidePageActivity extends BaseActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, GuidePageActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.contentFrameLayout)
    PageFrameLayout pageFrameLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initData() {
        // 设置资源文件和选中圆点
        pageFrameLayout.setUpViews(new int[]{
                R.layout.page_tab1,
                R.layout.page_tab2,
                R.layout.page_tab3,
                R.layout.page_tab4
        }, R.mipmap.banner_on, R.mipmap.banner_off);
    }
}
