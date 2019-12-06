package com.xxx.willing.ui.vote.fragment;

import android.content.Context;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.view.ExpandableTextView;

public class VoteItemFragment extends BaseFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData() {

    }





//
//    ExpandableTextView 用法
//    ExpandableTextView expandableTextView = findViewById(R.id.expanded_text);
//    int viewWidth = getWindowManager().getDefaultDisplay().getWidth() - dp2px(this, 20f);
//        expandableTextView.initWidth(viewWidth);
//        expandableTextView.setMaxLines(3);
//        expandableTextView.setHasAnimation(true);
//        expandableTextView.setCloseInNewLine(true);
//        expandableTextView.setOpenSuffixColor(getResources().getColor(R.color.colorAccent));
//        expandableTextView.setCloseSuffixColor(getResources().getColor(R.color.colorAccent));
//        expandableTextView.setOriginalText();


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        int res = 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        if (dpValue < 0)
            res = -(int) (-dpValue * scale + 0.5f);
        else
            res = (int) (dpValue * scale + 0.5f);
        return res;
    }

}
