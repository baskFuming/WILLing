package com.xxx.willing.ui.app.vote.activity.gvishop.my;

import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  @desc   商城首页
 *  @author FM
 *  @date   2019-12-04
 */

public class GVIMyFragment extends BaseFragment {
    @BindView(R.id.main_title)
    TextView mTitle;
    @BindView(R.id.main_content)
    TextView mContent;

    private String title;
    private String Url;

    @Override
    protected int getLayoutId() {
        return R.layout.gvi_my_fragment;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(R.string.gvi_shop);
        mTitle.setText(R.string.gvi_change);

    }
    @OnClick({R.id.main_return,R.id.main_content})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.main_return:
                getActivity().onBackPressed();
                break;
            case R.id.main_content:
                BaseWebActivity.actionStart(getActivity(),Url,title);
                break;
        }
    }
}
