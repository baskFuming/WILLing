package com.xxx.willing.ui.app.activity.vote;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.xxx.willing.R;
import com.xxx.willing.base.dialog.BaseDialog;
import com.xxx.willing.model.utils.KeyBoardUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class VoteWindow extends BaseDialog {

    private Callback callback;

    private VoteWindow(Context context) {
        super(context);
    }

    public static VoteWindow getInstance(Context context) {
        return new VoteWindow(context);
    }

    @BindView(R.id.window_vote_edit)
    public EditText mVoteEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.window_vote;
    }

    @Override
    protected void initData() {
        setCancelable(true); // 是否可以按“返回键”消失
    }


    @OnClick({R.id.window_vote_btn, R.id.window_vote_return})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.window_vote_btn:
                int amount;
                try {
                    amount = Integer.valueOf(mVoteEdit.getText().toString());
                } catch (Exception e) {
                    amount = 0;
                }
                if (callback != null) callback.callback(amount);
                break;
            case R.id.window_vote_return:
                KeyBoardUtil.closeKeyBord(getContext(), mVoteEdit);
                dismiss();
                break;
        }
    }

    public interface Callback {
        void callback(int amount);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
