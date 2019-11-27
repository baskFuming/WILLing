package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 修改昵称
 * @date 2019-11-27
 */

public class ModifyNameActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ModifyNameActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.ed_nick_name)
    EditText mModifyName;

    @Override
    protected String initTitle() {
        return getString(R.string.modify_nickname);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(getString(R.string.content_save));
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content://保存
                break;
        }
    }
}
