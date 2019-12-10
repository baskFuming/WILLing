package com.xxx.willing.ui.my.activity.join;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener;
import com.lljjcoder.bean.CustomCityData;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.JoinInfoBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.utils.CameraUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.view.CityPickerUtil;
import com.xxx.willing.view.CityPickerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 加盟申请
 * @date 2019-11-28
 */

public class JoinApplyActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemChildClickListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JoinApplyActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.ed_join_name)   //加盟商名称
            EditText mJoinName;
    @BindView(R.id.ed_join_rand)   //品牌
            TextView mJoinRand;
    @BindView(R.id.ed_join_time)   //周期
            TextView mJoinTime;
    @BindView(R.id.ed_join_address) //地址
            TextView mAddress;
    @BindView(R.id.ed_join_lines)   //额度
            EditText mJoinLines;
    @BindView(R.id.ed_join_introduce)  //项目简介
            EditText mJoinIntroduce;
    @BindView(R.id.ed_join_ed_number)   //简介长度
            TextView mNumber1;
    @BindView(R.id.ed_join_trans_photo)   //宣传照片
            TextView mPhoto2;
    @BindView(R.id.ed_check)    //确认
            CheckBox mCheck;

    @BindView(R.id.team_linear)
    RecyclerView mRecycler;     //创始人团队


    private PopupMenu mCheckBrandMenu;
    private PopupMenu mCheckTimeMenu;
    private CityPickerView mCityPickerView;

    private List<JoinEntry> mJoinRoleList = new ArrayList<>();  //团队
    private List<File> mTransList = new ArrayList<>(); //宣传图片
    private List<JoinInfoBean.BrandListBean> mBrandList = new ArrayList<>();//品牌集合
    private List<String> mTimeList = new ArrayList<>(); //加盟周期
    private int checkTimeId;
    private int checkBrandId;
    private String province;
    private String city;
    private String district;

    private JoinAdapter mAdapter;
    private int position;

    @Override
    protected String initTitle() {
        return getString(R.string.my_join);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_applay;
    }

    @Override
    protected void initData() {
        //初始化加盟团队
        mJoinRoleList.add(new JoinEntry());
        mAdapter = new JoinAdapter(mJoinRoleList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.bindToRecyclerView(mRecycler);

        //初始化地址选择
        mCityPickerView = CityPickerUtil.getInstance(this, mJoinIntroduce, new OnCustomCityPickerItemClickListener() {
            @Override
            public void onSelected(CustomCityData province, CustomCityData city, CustomCityData district) {
                JoinApplyActivity.this.province = province.getName();
                JoinApplyActivity.this.city = city.getName();
                JoinApplyActivity.this.district = district.getName();

                String address = JoinApplyActivity.this.province + JoinApplyActivity.this.city + JoinApplyActivity.this.district;
                mAddress.setText(address);
                super.onSelected(province, city, district);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });

        getJoinInfo();
    }

    @SuppressLint("SetTextI18n")
    @OnTextChanged({R.id.ed_join_introduce})
    public void OnTextChanged(CharSequence charSequence) {
        int length = charSequence.length();
        mNumber1.setText(length + "/500");
    }

    @OnClick({R.id.ed_submit, R.id.ed_agree_click, R.id.ed_create_click, R.id.re_click_address, R.id.ed_join_trans_photo, R.id.ed_join_rand, R.id.ed_join_time})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ed_agree_click://结盟协议
                BaseWebActivity.actionStart(this, HttpConfig.JOIN_HELP_URL, getString(R.string.join_agree_web));
                break;
            case R.id.ed_create_click://添加信息
                mJoinRoleList.add(new JoinEntry());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.ed_join_trans_photo: //上传图片
                UpdatePhotoActivity.actionStart(this, mTransList);
                break;
            case R.id.re_click_address://选择地址
                if (mCityPickerView != null) {
                    mCityPickerView.showCityPicker();
                }
                break;
            case R.id.ed_join_rand:
                if (mCheckBrandMenu != null) {
                    mCheckBrandMenu.show();
                }
                break;
            case R.id.ed_join_time:
                if (mCheckTimeMenu != null) {
                    mCheckTimeMenu.show();
                }
                break;
            case R.id.ed_submit:
                submitJoin();
                break;
        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.re_click_card://证件照上传
                UpdateCardActivity.actionStart(this, mJoinRoleList.get(position));
                break;
            case R.id.join_photo_add://形象照上传
                CameraUtil.openPhoto(this);
                break;
        }
        this.position = position;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull @NonNull String[] permissions, @android.support.annotation.NonNull @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CameraUtil.openPhoto(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE + 2 && data != null) {
            List<File> transList = (List<File>) data.getSerializableExtra("transList");
            if (transList != null) {
                mTransList.clear();
                mTransList.addAll(transList);
            }
            if (mTransList.size() <= 0) {
                mPhoto2.setText("继续上传");
            } else {
                mPhoto2.setText("点击上传");
            }
        }

        if (resultCode == UIConfig.RESULT_CODE + 1 && data != null) {
            JoinEntry joinEntry = (JoinEntry) data.getSerializableExtra("joinEntry");
            if (joinEntry != null) {
                mJoinRoleList.set(position, joinEntry);
            }
            mAdapter.notifyItemChanged(this.position);
        }

        CameraUtil.onActivityResult(this, requestCode, resultCode, data, (bitmap, file) -> {
            JoinEntry joinEntry = mJoinRoleList.get(position);
            joinEntry.setFilePhoto(file);
            mAdapter.notifyItemChanged(this.position);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCityPickerView != null) {
            mCityPickerView = null;
        }
        if (mCheckBrandMenu != null) {
            mCheckBrandMenu.dismiss();
            mCheckBrandMenu = null;
        }
        if (mCheckTimeMenu != null) {
            mCheckTimeMenu.dismiss();
            mCheckTimeMenu = null;
        }
    }

    //初始化选择品牌
    private void initCheckBrandMenu() {
        mCheckBrandMenu = new PopupMenu(this, mJoinRand);
        Menu menu = mCheckBrandMenu.getMenu();
        for (int i = 0; i < mBrandList.size(); i++) {
            menu.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, mBrandList.get(i).getName());
        }
        mCheckBrandMenu.setOnMenuItemClickListener(menuItem -> {
            JoinInfoBean.BrandListBean brandListBean = mBrandList.get(menuItem.getItemId() - 1);
            checkBrandId = brandListBean.getId();
            String time = brandListBean.getName();
            mJoinRand.setText(time);
            return false;
        });
    }

    //初始化选择品牌
    private void initCheckTimeMenu() {
        mCheckTimeMenu = new PopupMenu(this, mJoinTime);
        Menu menu = mCheckTimeMenu.getMenu();
        for (int i = 0; i < mTimeList.size(); i++) {
            menu.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, mTimeList.get(i));
        }
        mCheckTimeMenu.setOnMenuItemClickListener(menuItem -> {
            Object bean = mTimeList.get(menuItem.getItemId() - 1);
            checkTimeId = menuItem.getItemId();
            String time = (String) bean;
            mJoinTime.setText(time);
            return false;
        });
    }

    /**
     * @Model 申请加盟
     */
    private void submitJoin() {
        String joinName = mJoinName.getText().toString();
        if (joinName.isEmpty()) {
            ToastUtil.showToast("加盟商名称不能为空");
            showEditError(mJoinName);
            return;
        }
        if (checkBrandId == 0) {
            ToastUtil.showToast("请选择品牌");
            showEditError(mJoinRand);
            return;
        }
        if (checkTimeId == 0) {
            ToastUtil.showToast("请选择加盟周期");
            showEditError(mJoinTime);
            return;
        }
        String name = mJoinName.getText().toString();
        if (name.isEmpty()) {
            showEditError(mJoinName);
            ToastUtil.showToast("加盟商");
            return;
        }
        String address = province + city + district;
        if (province == null || city == null || province.isEmpty() || city.isEmpty()) {
            showEditError(mAddress);
            ToastUtil.showToast("地址不能为空");
            return;
        }
        String joinIntroduce = mJoinIntroduce.getText().toString();
        if (joinIntroduce.isEmpty()) {
            showEditError(mJoinName);
            ToastUtil.showToast("节点介绍不能为空");
            return;
        }
        String joinLines = mJoinLines.getText().toString();

        List<JoinEntry> data = mAdapter.getData(this);
        if (data == null) {
            return;
        }

        if (mTransList.size() <= 1) {
            showEditError(mPhoto2);
            ToastUtil.showToast("请先上传宣传照片");
            return;
        }
        boolean checked = mCheck.isChecked();
        if (checked) {
            showEditError(mCheck);
            ToastUtil.showToast("请先阅读协议");
            return;
        }

        List<File> list = new ArrayList<>();
        for (JoinEntry joinEntry : data) {
            list.add(joinEntry.getFileHand());
            list.add(joinEntry.getFileBack());
            list.add(joinEntry.getFileFront());
            list.add(joinEntry.getFilePhoto());
        }

        for (int i = 0; i < mTransList.size(); i++) {
            File file = mTransList.get(i);
            if (file != null) {
                list.add(file);
            }
        }
        Api.getInstance().upLoadPhotoMap(Api.getMapFileRequestBody(list))
                .flatMap((Function<BaseBean<Map<String, String>>, ObservableSource<BaseBean<BooleanBean>>>) bean -> {
                    if (bean == null) throw new RuntimeException();
                    Map<String, String> map = bean.getData();
                    if (map == null) throw new RuntimeException();

                    JSONArray list1 = new JSONArray();
                    for (int i = 0; i < data.size(); i++) {
                        JoinEntry joinEntry = data.get(i);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("cardFront", Objects.requireNonNull(map.get(joinEntry.getFileHand().getName())));
                        jsonObject.put("cardBack", Objects.requireNonNull(map.get(joinEntry.getFileFront().getName())));
                        jsonObject.put("holdCard", Objects.requireNonNull(map.get(joinEntry.getFileBack().getName())));
                        jsonObject.put("userImg", Objects.requireNonNull(map.get(joinEntry.getFilePhoto().getName())));
                        jsonObject.put("userRole", joinEntry.getRole());
                        jsonObject.put("name", joinEntry.getName());
                        jsonObject.put("phone", joinEntry.getPhone());
                        list1.put(jsonObject);
                    }

                    List<String> list2 = new ArrayList<>();
                    for (int i = 0; i < mTransList.size(); i++) {
                        File file = mTransList.get(i);
                        if (file != null) {
                            list2.add(map.get(file.getName()));
                        }
                    }

                    return Api.getInstance().submitJoin(joinName, checkBrandId, checkTimeId, address, joinIntroduce, joinLines, list2, list1.toString());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> data) {
                        ToastUtil.showToast(getString(R.string.join_success));
                        finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    /**
     * @Model 获取加盟信息
     */
    private void getJoinInfo() {
        Api.getInstance().getJoinInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<JoinInfoBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<JoinInfoBean> bean) {
                        if (bean != null) {
                            JoinInfoBean data = bean.getData();
                            if (data != null) {
                                mBrandList.clear();
                                mBrandList.addAll(data.getBrandList());
                                mTimeList.clear();
                                mTimeList.addAll(data.getTimeList());
                                initCheckBrandMenu();
                                initCheckTimeMenu();
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }
}
