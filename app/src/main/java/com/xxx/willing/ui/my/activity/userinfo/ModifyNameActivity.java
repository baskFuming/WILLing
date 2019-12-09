package com.xxx.willing.ui.my.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
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

    public static void actionStart(Activity activity, String name) {
        Intent intent = new Intent(activity, ModifyNameActivity.class);
        intent.putExtra("name", name);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        nickName = intent.getStringExtra("name");
    }


    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.ed_nick_name)
    EditText mModifyName;
    private String nickName;

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
        mModifyName.setText(nickName);
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(getString(R.string.content_save));
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content://保存
                savaName(nickName);
                break;
        }
    }

    private void savaName(String nickName) {

    }
}
