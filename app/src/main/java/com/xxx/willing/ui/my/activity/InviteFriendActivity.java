package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ImageUtil;
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
    @BindView(R.id.invite_friend_link)
    TextView mLink;
    @BindView(R.id.main_content)
    TextView mContent;

    private String content;
    private String url;
    private Bitmap bitmap;

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
        mContent.setText(getString(R.string.content_save));

        content = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_INVITE_CODE);
        url = HttpConfig.INVITE_URL + content;
        bitmap = ZXingUtil.createQRCode(url, (int) getResources().getDimension(R.dimen.zxCode_size));

        mImage.setImageBitmap(bitmap);
        mCode.setText(content);
        mLink.setText(url);
    }

    @OnClick({R.id.invite_friend_copy, R.id.invite_friend_url, R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.invite_friend_url:
                KeyBoardUtil.copy(this, url);
                break;
            case R.id.invite_friend_copy:
                KeyBoardUtil.copy(this, content);
                break;
            case R.id.main_content:
                ImageUtil.saveImage(this, bitmap);
                break;
        }
    }
}
