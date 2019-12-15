package com.xxx.willing.ui.vote.window;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.base.dialog.BaseDialog;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.ui.app.activity.gvishop.my.address.pop.SubmitPop;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class VoteDetailsPop extends BaseDialog {

    private String img;
    private String name;
    private String details;

    @BindView(R.id.window_vote_user_img)
    ImageView mImg;
    @BindView(R.id.window_vote_user_name)
    TextView mName;
    @BindView(R.id.window_vote_user_details)
    TextView mDetails;

    public VoteDetailsPop(Context context, String img, String name, String details) {
        super(context);
        this.img = img;
        this.name = name;
        this.details = details;
    }

    public static VoteDetailsPop getInstance(Context context, String img, String name, String details) {
        return new VoteDetailsPop(context, img, name, details);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vote_details_pop;
    }

    @Override
    protected void initData() {
        GlideUtil.loadCircle(this.getContext(), img, mImg);
        mName.setText(name);
        mDetails.setText(details);

    }

    @OnClick(R.id.window_dis)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.window_dis:
                dismiss();
                break;
        }
    }
}
