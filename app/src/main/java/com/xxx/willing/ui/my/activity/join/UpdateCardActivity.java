package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.utils.CameraUtil;
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
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        joinEntry = (JoinEntry) intent.getSerializableExtra("entry");
    }

    private static final int TYPE_HAND = 1;
    private static final int TYPE_REVERSE = 2;
    private static final int TYPE_POSITIVE = 3;

    @BindView(R.id.img_positive)
    ImageView mImgPos;
    @BindView(R.id.img_reverse)
    ImageView mImgRev;
    @BindView(R.id.img_hand)
    ImageView mImgHand;

    private File fileHand;
    private File fileReverse;
    private File filePositive;

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
        fileHand = joinEntry.getFileHand();
        fileReverse = joinEntry.getFileReverse();
        filePositive = joinEntry.getFilePositive();

        if (fileHand != null) {
            mImgHand.setImageBitmap(BitmapFactory.decodeFile(fileHand.getName()));
        }
        if (fileReverse != null) {
            mImgRev.setImageBitmap(BitmapFactory.decodeFile(fileReverse.getName()));
        }
        if (filePositive != null) {
            mImgPos.setImageBitmap(BitmapFactory.decodeFile(filePositive.getName()));
        }
    }

    @OnClick({R.id.img_positive, R.id.img_reverse, R.id.img_hand, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_positive:
                type = TYPE_POSITIVE;
                CameraUtil.openPhoto(this);
                break;
            case R.id.img_reverse:
                type = TYPE_REVERSE;
                CameraUtil.openPhoto(this);
                break;
            case R.id.img_hand:
                type = TYPE_HAND;
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
                case TYPE_HAND:
                    mImgHand.setImageBitmap(bitmap);
                    fileHand = file;
                    break;
                case TYPE_REVERSE:
                    mImgRev.setImageBitmap(bitmap);
                    fileReverse = file;
                    break;
                case TYPE_POSITIVE:
                    mImgPos.setImageBitmap(bitmap);
                    filePositive = file;
                    break;
            }
        });
    }

    /**
     * 返回
     */
    private void submitJoin() {
        if (fileHand == null) {
            showEditError(mImgHand);
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }
        if (fileReverse == null) {
            showEditError(mImgRev);
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }
        if (filePositive == null) {
            showEditError(mImgPos);
            ToastUtil.showToast(R.string.update_card_error);
            return;
        }

        joinEntry.setFileHand(fileHand);
        joinEntry.setFileReverse(fileReverse);
        joinEntry.setFilePositive(filePositive);

        finish();
    }
}
