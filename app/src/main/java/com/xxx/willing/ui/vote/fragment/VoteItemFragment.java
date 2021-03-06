package com.xxx.willing.ui.vote.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
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
import com.xxx.willing.ui.app.activity.vote.JoinDetailsActivity;
import com.xxx.willing.ui.vote.activity.BrandDetailActivity;
import com.xxx.willing.ui.vote.adapter.VoteItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @date 2019-12-07
 */

public class VoteItemFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

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
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.lin_1)
    LinearLayout mNotData2;

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
        mAdapter.setOnItemChildClickListener(this);

        getFranchiseeList();
    }

    @OnClick({R.id.vote_item_linear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.vote_item_linear:
                BrandDetailActivity.actionStart(getActivity(), bean);
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        FranchiseeBean franchiseeBean = mList.get(position);
        JoinDetailsActivity.actionStart(getActivity(), franchiseeBean.getId(), franchiseeBean.getStatus());
    }

    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        getFranchiseeList();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getFranchiseeList();
    }

    /**
     * @Model 获取投票加盟列表
     */
    private void getFranchiseeList() {
        Api.getInstance().getFranchiseeList(bean.getId(), page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<FranchiseeBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<FranchiseeBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
//                            mNotData2.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        PageBean<FranchiseeBean> data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
//                            mNotData2.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        List<FranchiseeBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
//                            mNotData2.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mNotData.setVisibility(View.GONE);
//                        mNotData2.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.VISIBLE);
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mList.clear();
                        }
                        mList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mList.size() == 0) {
                            mNotData.setVisibility(View.VISIBLE);
//                            mNotData2.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.GONE);
                        }
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }


}
