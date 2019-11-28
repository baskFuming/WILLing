package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.journeyapps.barcodescanner.ViewfinderView;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.ui.my.activity.CallMeActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author FM
 * @desc 加盟申请
 * @date 2019-11-28
 */

public class JoinApplyActivity extends BaseTitleActivity {

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
    @BindView(R.id.ed_join_role)    //角色
            EditText mJoinRole;
    @BindView(R.id.ed_join_user_name)  //姓名
            EditText mName;
    @BindView(R.id.ed_join_phone)     //电话
            EditText mPhone;
    @BindView(R.id.ed_join_person_introduce)  //用户简介
            EditText mJoinPerIntroduce;
    @BindView(R.id.ed_join_ed_person_number)   //简介
            TextView mNumber2;


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

    }

    @OnTextChanged({R.id.ed_join_introduce, R.id.ed_join_person_introduce})
    public void OnTextChanged(CharSequence charSequence) {
        int length = charSequence.length();
        mNumber1.setText(length + "/500");
        mNumber2.setText(length + "/500");

    }

    @OnClick({R.id.ed_agree_click, R.id.re_click_card, R.id.ed_join_trans_photo})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ed_agree_click://结盟协议
                JoinWebActivity.actionStart(this);
                break;
            case R.id.re_click_card://证件照上传
                UpdateCardActivity.actionStart(this);
                break;
            case R.id.ed_join_trans_photo: //上传图片
                UpdatePhotoActivity.actionStart(this);
                break;


        }
    }

}
