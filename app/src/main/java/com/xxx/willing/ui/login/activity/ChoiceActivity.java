package com.xxx.willing.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.ui.login.area.AreaCodeModel;
import com.xxx.willing.ui.login.area.PhoneAreaCodeAdapter;
import com.xxx.willing.ui.login.area.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
/**
 *  @desc   区号
 *  @author FM
 *  @date   2019-11-28
 */

public class ChoiceActivity extends BaseActivity implements OnQuickSideBarTouchListener {

    public static final int resultCode = 0x1110;
    public static final String DATAKEY = "AreaCodeModel";
    private boolean isEnglish;
    private List<String> sections = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private List<AreaCodeModel> datalist;

    @BindView(R.id.zhongwenqiehuan)
    TextView mZhongwenqiehuan;
    //    @BindView(R.id.view)
//    View view;
    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.llTitle)
    ConstraintLayout llTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.quickSideBarTipsView)
    QuickSideBarTipsView quickSideBarTipsView;
    @BindView(R.id.quickSideBarView)
    QuickSideBarView quickSideBarView;

    /**
     * 地区选择
     *
     * @param
     */
    public static Intent newInstance(Context context, String title, String titleTextColor, String titleColor, String stickHeaderColor) {
        Intent intent = new Intent(context, ChoiceActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("titleColor", titleColor);
        intent.putExtra("titleTextColor", titleTextColor);
        intent.putExtra("stickHeaderColor", stickHeaderColor);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice;
    }

    @Override
    protected void initData() {
        quickSideBarView.setOnQuickSideBarTouchListener(this);

        String titleColor = getIntent().getStringExtra("titleColor");
        String stickHeaderColor = getIntent().getStringExtra("stickHeaderColor");
        String title = getIntent().getStringExtra("title");
        String titleTextColor = getIntent().getStringExtra("titleTextColor");
        //读取数据
        String json = Utils.readAssetsTxt(this, "phoneAreaCode");
        datalist = Utils.jsonToList(json);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sortList(datalist);
        final PhoneAreaCodeAdapter adapter = new PhoneAreaCodeAdapter();
        adapter.setDataList(datalist);
        if (!TextUtils.isEmpty(stickHeaderColor)) adapter.setStickHeaderColor(stickHeaderColor);
        recyclerView.setAdapter(adapter);
        //设置recyclerView需要的Decoration;
        StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(decoration);
        adapter.setOnItemClickListener(model -> {
            Intent intent = new Intent();
            intent.putExtra(DATAKEY, model);
            setResult(resultCode, intent);
            finish();
        });
        mZhongwenqiehuan.setOnClickListener(v -> {
            isEnglish = !isEnglish;
            mZhongwenqiehuan.setText(isEnglish ? "中文" : "English");
            sortList(datalist);
            adapter.setDataList(datalist);
            adapter.setEnglish(isEnglish);
        });
        fanhui.setOnClickListener(v -> finish());
    }

    /**
     * 根据国家中文拼音首字母排序
     *
     * @param datalist
     */
    private void sortList(List<AreaCodeModel> datalist) {
        Collections.sort(datalist, (o1, o2) -> {
                    if (isEnglish) {
                        return Utils.getFirstPinYin(o1.getEn())
                                .compareTo(Utils.getFirstPinYin(o2.getEn()));
                    }
                    return Utils.getFirstPinYin(o1.getName())
                            .compareTo(Utils.getFirstPinYin(o2.getName()));
                }
        );
        sections.clear();
        for (AreaCodeModel area : datalist) {
            String section = "";
            if (isEnglish) {
                section = Utils.getFirstPinYin(area.getEn());
            } else {
                section = Utils.getFirstPinYin(area.getName());
            }
            if (!sections.contains(section)) sections.add(section);
        }
        quickSideBarView.setLetters(sections);
    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        layoutManager.scrollToPositionWithOffset(index(letter), 0);
    }

    private int index(String letter) {
        for (int i = 0; i < datalist.size(); i++) {
            AreaCodeModel area = datalist.get(i);
            String section = "";
            if (isEnglish) {
                section = Utils.getFirstPinYin(area.getEn());
            } else {
                section = Utils.getFirstPinYin(area.getName());
            }
            if (TextUtils.equals(letter, section)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onLetterTouching(boolean touching) {
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.GONE);
    }
}
