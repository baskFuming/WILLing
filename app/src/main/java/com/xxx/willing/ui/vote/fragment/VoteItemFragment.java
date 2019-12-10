package com.xxx.willing.ui.vote.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.BrandBean;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.vote.adapter.VoteItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @date 2019-12-07
 */

public class VoteItemFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    public static VoteItemFragment getInstance(BrandBean brandBean) {
        VoteItemFragment voteFragment = new VoteItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", brandBean);
        voteFragment.setArguments(bundle);
        return voteFragment;
    }

    private void initBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (BrandBean) bundle.getSerializable("bean");
        } else {
            bean = new BrandBean();
        }
    }

    @BindView(R.id.vote_item_content)
    TextView mContent;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;

    private BrandBean bean;
    private int page = UIConfig.PAGE_DEFAULT;
    private VoteItemAdapter mAdapter;
    private List<FranchiseeBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vote_item;
    }

    @Override
    protected void initData() {
        initBundle();

        mContent.setText(bean.getDetails());

        mAdapter = new VoteItemAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        getBrandList();
    }

    @OnClick({R.id.vote_item_linear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.vote_item_linear:

                break;
        }
    }

    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        getBrandList();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getBrandList();
    }

    /**
     * @Model 获取品牌列表
     */
    private void getBrandList() {
        Api.getInstance().getFranchiseeList(bean.getId(), page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<FranchiseeBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<FranchiseeBean>> bean) {
                        PageBean<FranchiseeBean> data = bean.getData();
                        if (data != null) {
                            List<FranchiseeBean> list = data.getList();
                            if (list != null && list.size() != 0) {
                                mList.clear();
                                mList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }

}
