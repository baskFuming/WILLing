package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.ConfigClass;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ZXingUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 邀请好友页
 * @Author xxx
 */
public class InviteFriendActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, InviteFriendActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.invite_friend_image)
    ImageView mImage;
    @BindView(R.id.invite_friend_code)
    TextView mCode;
    @BindView(R.id.main_title)
    TextView mTitle;
    @BindView(R.id.main_content)
    TextView mContent;

    private String content;
    private Bitmap bitmap;  //二维码

    @Override
    protected String initTitle() {
        return getString(R.string.invite_friend_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected void initData() {
        mTitle.setText(getString(R.string.invite_friend_title));
        mContent.setText(getString(R.string.content_save));
        content = ConfigClass.INVITE_URL + SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_INVITE_CODE);
        bitmap = ZXingUtil.createQRCode(content, (int) getResources().getDimension(R.dimen.zxCode_size));
        mImage.setImageBitmap(bitmap);
        mCode.setText(content);

    }

    @OnClick({R.id.invite_friend_copy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.invite_friend_copy:
                KeyBoardUtil.copy(this, content);
                break;
        }
    }
}
