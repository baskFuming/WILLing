package com.xxx.willing.ui.app.activity.gvishop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.base.fragment.FragmentManager;
import com.xxx.willing.ui.app.activity.gvishop.home.GVIHomeFragment;
import com.xxx.willing.ui.app.activity.gvishop.my.GVIMyFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc GVI商城
 * @date 2019-12-04
 */

public class GVIShopActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, GVIShopActivity.class);
        activity.startActivity(intent);
    }

    //页面下标
    private static final int VOTE_TYPE = R.id.main_vote;   //我的
    private static final int MY_TYPE = R.id.main_my;         //我的

    @BindView(R.id.main_vote_image)
    ImageView mVoteImage;
    @BindView(R.id.main_my_image)
    ImageView mMyImage;
    @BindView(R.id.main_vote_text)
    TextView mVoteText;
    @BindView(R.id.main_my_text)
    TextView mMyText;
    @BindView(R.id.main_content)
    TextView mContent;

    private String title;
    private String Url;

    private int nowType = VOTE_TYPE;   //当前选中下标
    private int lastType = VOTE_TYPE;   //上一个下标

    @Override
    protected String initTitle() {
        return getString(R.string.gvi_shop);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gvishop;
    }

    @Override
    protected void initData() {
        title = getString(R.string.gvi_change);
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(title);
        //加载首页数据
        selectorItem();
    }

    @OnClick({R.id.main_content, R.id.main_vote, R.id.main_my})
    public void OnClick(View view) {
        nowType = view.getId();
        if (nowType != lastType) {
            defaultItem();
            selectorItem();
            lastType = nowType;
        }

        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, Url, title);
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nowType = savedInstanceState.getInt("type", VOTE_TYPE);

        //切换数据
        selectorItem();
        defaultItem();
        lastType = nowType;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", nowType);
    }

    //选中的
    public void selectorItem() {
        switch (nowType) {
            case VOTE_TYPE:
                mVoteImage.setImageResource(R.mipmap.home_default_true);
                mVoteText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, GVIHomeFragment.class, R.id.main_frame);
                break;
            case MY_TYPE:
                mMyImage.setImageResource(R.mipmap.main_my_selection);
                mMyText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, GVIMyFragment.class, R.id.main_frame);
                break;
        }
    }

    //撤销上次选中的
    public void defaultItem() {
        switch (lastType) {
            case VOTE_TYPE:
                mVoteImage.setImageResource(R.mipmap.home_default);
                mVoteText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case MY_TYPE:
                mMyImage.setImageResource(R.mipmap.main_my_default);
                mMyText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
        }
    }
}
