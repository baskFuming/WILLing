package com.xxx.willing.ui.my.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 账户信息
 * @date 2019-11-27
 */

public class AccountInfoActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AccountInfoActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.account_img)
    ImageView mImg;
    @BindView(R.id.nick_name)
    TextView mNickName;
    @BindView(R.id.nick_phone)
    TextView mPhone;

    @Override
    protected String initTitle() {
        return getString(R.string.user_info);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.update_nick})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.update_nick://修改昵称
                ModifyNameActivity.actionStart(this);
                break;
        }
    }
}
