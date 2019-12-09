package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.utils.CameraUtil;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.model.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 上传证件照
 * @date 2019-11-28
 */

public class UpdateCardActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, JoinEntry joinEntry) {
        Intent intent = new Intent(activity, UpdateCardActivity.class);
        intent.putExtra("entry", joinEntry);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    private void initBundle() {
        Intent intent = getIntent();
        joinEntry = (JoinEntry) intent.getSerializableExtra("entry");
    }

    private static final int TYPE_CARD_FRONT = 1;
    private static final int TYPE_CARD_BACK = 2;
    private static final int TYPE_HAND_CARD = 3;

    @BindView(R.id.img_card_front)
    ImageView mImgFront;
    @BindView(R.id.img_card_back)
    ImageView mImgBack;
    @BindView(R.id.img_hold_card)
    ImageView mImgHand;
    @BindView(R.id.tv_card_front)
    TextView mTvFront;
    @BindView(R.id.tv_card_back)
    TextView mTvBack;
    @BindView(R.id.tv_hold_card)
    TextView mTvHand;
    @BindView(R.id.li_card_front)
    LinearLayout mLiFront;
    @BindView(R.id.li_card_back)
    LinearLayout mLiBack;
    @BindView(R.id.li_hold_card)
    LinearLayout mLiHand;

    private File fileFront;
    private File fileBack;
    private File fileHand;

    private JoinEntry joinEntry;
    private int type;

    @Override
    protected String initTitle() {
        return getString(R.string.update_card);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_card;
    }

    @Override
    protected void initData() {
        initBundle();

        if (joinEntry == null) {
            joinEntry = new JoinEntry();
        }
        fileFront = joinEntry.getFileFront();
        fileBack = joinEntry.getFileBack();
        fileHand = joinEntry.getFileHand();

        if (fileFront != null) {
            mImgFront.setVisibility(View.GONE);
            mTvFront.setVisibility(View.GONE);
            GlideUtil.loadBack(this, fileFront, mLiFront);
        }
        if (fileBack != null) {
            mImgBack.setVisibility(View.GONE);
            mTvBack.setVisibility(View.GONE);
            GlideUtil.loadBack(this, fileFront, mLiBack);
        }
        if (fileHand != null) {
            mImgHand.setVisibility(View.GONE);
            mTvHand.setVisibility(View.GONE);
            GlideUtil.loadBack(this, fileFront, mLiHand);
        }
    }

    @OnClick({R.id.img_card_front, R.id.img_card_back, R.id.img_hold_card, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_card_front:
                type = TYPE_CARD_FRONT;
                CameraUtil.openPhoto(this);
                break;
            case R.id.img_card_back:
                type = TYPE_CARD_BACK;
                CameraUtil.openPhoto(this);
                break;
            case R.id.img_hold_card:
                type = TYPE_HAND_CARD;
                CameraUtil.openPhoto(this);
                break;
            case R.id.btn_save:
                submitJoin();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CameraUtil.openPhoto(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CameraUtil.onActivityResult(this, requestCode, resultCode, data, (bitmap, file) -> {
            switch (type) {
                case TYPE_CARD_FRONT:
                    GlideUtil.loadBack(this, file, mLiFront);
                    mImgFront.setVisibility(View.GONE);
                    mTvFront.setVisibility(View.GONE);
                    fileFront = file;
                    break;
                case TYPE_CARD_BACK:
                    GlideUtil.loadBack(this, file, mLiBack);
                    mImgBack.setVisibility(View.GONE);
                    mTvBack.setVisibility(View.GONE);
                    fileBack = file;
                    break;
                case TYPE_HAND_CARD:
                    GlideUtil.loadBack(this, file, mLiHand);
                    mImgHand.setVisibility(View.GONE);
                    mTvHand.setVisibility(View.GONE);
                    fileHand = file;
                    break;
            }
        });
    }

    /**
     * 返回
     */
    private void submitJoin() {
        if (fileFront == null) {
            showEditError(mLiFront);
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }
        if (fileBack == null) {
            showEditError(mLiBack);
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }
        if (fileHand == null) {
            showEditError(mLiHand);
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }

        joinEntry.setFileFront(fileFront);
        joinEntry.setFileBack(fileBack);
        joinEntry.setFileHand(fileHand);

        Intent intent = new Intent();
        intent.putExtra("joinEntry", joinEntry);
        setResult(UIConfig.RESULT_CODE + 1, intent);
        finish();
    }
}
