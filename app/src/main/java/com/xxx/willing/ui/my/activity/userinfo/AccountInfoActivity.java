package com.xxx.willing.ui.my.activity.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.BitmapUtils;
import com.xxx.willing.model.utils.CameraUtil;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.model.utils.PermissionUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.area.SelectPhoneCode;
import com.xxx.willing.ui.my.activity.window.SetIconPopup;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 账户信息
 * @date 2019-11-27
 */

public class AccountInfoActivity extends BaseTitleActivity implements SetIconPopup.Callback {

    public static void actionStart(Activity activity, String icon, String name, String phone) {
        Intent intent = new Intent(activity, AccountInfoActivity.class);
        intent.putExtra("icon", icon);
        intent.putExtra("icon", name);
        intent.putExtra("icon", phone);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        icon = intent.getStringExtra("icon");
        nickName = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
    }

    @BindView(R.id.account_img)
    ImageView mImg;
    @BindView(R.id.nick_name)
    TextView mNickName;
    @BindView(R.id.nick_phone)
    TextView mPhone;

    private String nickName;
    private String phone;
    private String icon;

    private SetIconPopup mSetIconPopup;

    public static final int PHOTO = 1;  //相册状态
    public static final int CAMERA = 2; //相机状态
    private int tag;

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
        initBundle();
        GlideUtil.loadCircle(this, icon, GlideUtil.MY_ICON_DEFAULT, mImg);
        mNickName.setText(nickName);
        mPhone.setText(phone);
        mSetIconPopup = SetIconPopup.getInstance(this, this);
    }

    @OnClick({R.id.account_img, R.id.update_nick})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.account_img://头像弹框
                if (mSetIconPopup != null) {
                    mSetIconPopup.show();
                }
                break;
            case R.id.update_nick://修改昵称
                ModifyNameActivity.actionStart(this,nickName);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSetIconPopup != null) {
            mSetIconPopup.dismiss();
            mSetIconPopup = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_PERMISSION_CODE) {
            //确认相机功能 打开相机/相册
            switch (tag) {
                case PHOTO:
                    CameraUtil.openPhoto(this);
                    break;
                case CAMERA:
                    CameraUtil.openCamera(this);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UIConfig.REQUEST_CODE && resultCode == UIConfig.RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (name != null && !name.isEmpty()) {
                nickName = name;
                mNickName.setText(name);
            }
        } else {
            CameraUtil.onActivityResult(this, requestCode, resultCode, data, new CameraUtil.CallBack() {
                @Override
                public void callback(Bitmap bitmap, File file) {
                    mImg.setImageBitmap(bitmap);
                    upLoadIcon(file);
                }
            });
        }
    }

    @Override
    public void onCamera() {
        tag = CAMERA;
        CameraUtil.openCamera(this);
        mSetIconPopup.dismiss();
    }

    @Override
    public void onPhoto() {
        tag = PHOTO;
        CameraUtil.openPhoto(this);
        mSetIconPopup.dismiss();
    }
    /**
     * @Model 上传头像
     */
    private void upLoadIcon(final File file) {
        String base64 = null;
        if (file != null) {
            base64 = "data:image/jpeg;base64," + BitmapUtils.imgToBase64(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
        if (base64 == null) {
            ToastUtil.showToast("图片格式有误");
            return;
        }
//        Api.getInstance().upLoadIcon(base64)
//                .flatMap(new Function<BaseBean<String>, ObservableSource<BaseBean<Object>>>() {
//                    @Override
//                    public ObservableSource<BaseBean<Object>> apply(BaseBean<String> stringBaseBean) {
//                        return Api.getInstance().setIcon(StatusData.userId, stringBaseBean.getData());
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiCallback<Object>(this) {
//                    @Override
//                    public void onSuccess(Object data) {
//                        ToastUtil.showToast(getString(R.string.modify_success));
//                        //发送EventBus 更新首页
//                        EventBus.getDefault().post(new UserInfoEntry(nickName, icon));
//                    }
//
//                    @Override
//                    public void onError(int errorCode, String errorMessage) {
//                        ToastUtil.showToast(errorMessage);
//                    }
//
//                    @Override
//                    public void onStart(Disposable d) {
//                        super.onStart(d);
//                        showLoading();
//                    }
//
//                    @Override
//                    public void onEnd() {
//                        super.onEnd();
//                        hideLoading();
//                    }
//                });
    }
}
