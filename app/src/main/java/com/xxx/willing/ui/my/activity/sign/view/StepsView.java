package com.xxx.willing.ui.my.activity.sign.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.xxx.willing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 自定义签到View.
 *
 * @author Sorgs.
 * @date 2018/8/17.
 */
public class StepsView extends View {

    private StepCallback callback;

    /**
     * 线段的高度
     */
    private float mLineHeight;
    /**
     * 线段长度
     */
    private float mLineWidth;
    /**
     * 小图标大小
     */
    private float mSmallIconSize;
    /**
     * 大图标大小
     */
    private float mBigIconSize;

    /**
     * up的宽度
     */
    private float mUpWidth;

    /**
     * up的高度
     */
    private float mUpHeight;

    /**
     * up的间距
     */
    private float mUpPadding;

    /**
     * 文字大小
     */
    private float mTextSize;

    /**
     * 文字间距
     */
    private float mTextPadding;

    /**
     * 图标中心点Y
     */
    private float mCenterY;
    /**
     * 线段的左上方的Y
     */
    private float mLeftY;
    /**
     * 线段的右下方的Y
     */
    private float mRightY;

    /**
     * 数据源
     */
    private List<StepBean> mStepBeanList;

    /**
     * 未完成圆形颜色
     */
    private int mUnCompletedCircleColor = Color.parseColor("#DFE2E5");
    /**
     * 完成的圆形颜色
     */
    private int mCompletedCircleColor = Color.parseColor("#3C7FFF");
    /**
     * 未完成线条颜色
     */
    private int mUnCompletedLineColor = Color.parseColor("#DFE2E5");
    /**
     * 完成的线条颜色
     */
    private int mCompletedLineColor = Color.parseColor("#3C7FFF");
    /**
     * 未完成的天数颜色
     */
    private int mUnCompletedTextColor = Color.parseColor("#999999");
    /**
     * 完成的天数颜色
     */
    private int mCompletedTextColor = Color.parseColor("#333333");

    /**
     * 未完成的线段Paint
     */
    private Paint mUnCompletedLinePaint;
    /**
     * 完成的线段paint
     */
    private Paint mCompletedLinePaint;
    /**
     * 未完成天数文字
     */
    private Paint mUnCompletedTextPaint;
    /**
     * 完成天数文字
     */
    private Paint mCompletedTextPaint;
    /**
     * 未完成的圆形Paint
     */
    private Paint mUnCompletedCirclePaint;
    /**
     * 完成的圆形Paint
     */
    private Paint mCompletedCirclePaint;

    /**
     * 是否执行动画
     */
    private boolean isAnimation = false;

    /**
     * 记录重绘次数
     */
    private int mCount = 0;

    /**
     * 动画执行的时间 230毫秒
     */
    private final static int ANIMATION_TIME = 230;
    /**
     * 动画执行的间隔次数
     */
    private final static int ANIMATION_INTERVAL = 10;

    /**
     * 执行动画线段每次绘制的长度，线段的总长度除以总共执行的时间乘以每次执行的间隔时间
     */
    private float mAnimationWidth;

    /**
     * 执行动画的位置
     */
    private int mPosition;

    public StepsView(Context context) {
        this(context, null);
    }

    public StepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init() {
        mStepBeanList = new ArrayList<>();
        mLineHeight = getResources().getDimension(R.dimen.step_line_height);
        mLineWidth = getResources().getDimension(R.dimen.step_line_width);
        mSmallIconSize = getResources().getDimension(R.dimen.step_small_circle);
        mBigIconSize = getResources().getDimension(R.dimen.step_big_circle);
        mUpWidth = getResources().getDimension(R.dimen.step_up_width);
        mUpHeight = getResources().getDimension(R.dimen.step_up_height);
        mUpPadding = getResources().getDimension(R.dimen.step_up_padding);
        mTextSize = getResources().getDimension(R.dimen.step_text_size);
        mTextPadding = getResources().getDimension(R.dimen.step_text_padding);

        mAnimationWidth = (mLineWidth / ANIMATION_TIME) * ANIMATION_INTERVAL;

        //未完成线段画笔
        mUnCompletedLinePaint = new Paint();
        mUnCompletedLinePaint.setAntiAlias(true);
        mUnCompletedLinePaint.setColor(mUnCompletedLineColor);
        mUnCompletedLinePaint.setStrokeWidth(2);
        mUnCompletedLinePaint.setStyle(Paint.Style.FILL);

        //已完成线段画笔
        mCompletedLinePaint = new Paint();
        mCompletedLinePaint.setAntiAlias(true);
        mCompletedLinePaint.setColor(mCompletedLineColor);
        mCompletedLinePaint.setStrokeWidth(2);
        mCompletedLinePaint.setStyle(Paint.Style.FILL);

        //未完成文字画笔
        mUnCompletedTextPaint = new Paint();
        mUnCompletedTextPaint.setAntiAlias(true);
        mUnCompletedTextPaint.setColor(mUnCompletedTextColor);
        mUnCompletedTextPaint.setStrokeWidth(2);
        mUnCompletedTextPaint.setStyle(Paint.Style.FILL);
        mUnCompletedTextPaint.setTextSize(mTextSize);

        //已完成文字画笔
        mCompletedTextPaint = new Paint();
        mCompletedTextPaint.setAntiAlias(true);
        mCompletedTextPaint.setColor(mCompletedTextColor);
        mCompletedTextPaint.setStyle(Paint.Style.FILL);
        mCompletedTextPaint.setTextSize(mTextSize);

        //未完成圆形画笔
        mUnCompletedCirclePaint = new Paint();
        mUnCompletedCirclePaint.setAntiAlias(true);
        mUnCompletedCirclePaint.setColor(mUnCompletedCircleColor);
        mUnCompletedCirclePaint.setStrokeWidth(2);
        mUnCompletedCirclePaint.setStyle(Paint.Style.FILL);

        //已完成圆形画笔
        mCompletedCirclePaint = new Paint();
        mCompletedCirclePaint.setAntiAlias(true);
        mCompletedCirclePaint.setColor(mCompletedCircleColor);
        mCompletedCirclePaint.setStrokeWidth(2);
        mCompletedCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图标的中中心Y点
        mCenterY = mBigIconSize / 2 + mUpHeight + mUpHeight;
        //获取左上方Y的位置，获取该点的意义是为了方便画矩形左上的Y位置
        mLeftY = mCenterY - (mLineHeight / 2);
        //获取右下方Y的位置，获取该点的意义是为了方便画矩形右下的Y位置
        mRightY = mCenterY + mLineHeight / 2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimation) {
            drawSign(canvas);
        } else {
            drawUnSign(canvas);
        }
    }

    /**
     * 绘制签到(伴随签到动画)
     */
    @SuppressLint("DrawAllocation")
    private void drawSign(Canvas canvas) {
        //上一次的宽度
        float lastWidth = 0;
        for (int i = 0; i < mStepBeanList.size(); i++) {
            StepBean bean = mStepBeanList.get(i);

            //图标大小
            float iconSize;
            //首先判断是否是当天
            if (i == mPosition) {
                iconSize = mBigIconSize;
            } else {
                iconSize = mSmallIconSize;
            }

            //绘制线段
            if (i != mStepBeanList.size() - 1) {
                //最后一条不需要绘制
                if (mStepBeanList.get(i + 1).getState() == StepBean.STEP_COMPLETED) {
                    //下一个是已完成，当前才需要绘制蓝色
                    canvas.drawRect(lastWidth + iconSize, mLeftY, lastWidth + iconSize + mLineWidth, mRightY,
                            mCompletedLinePaint);
                } else {
                    //其余绘制灰色

                    //当前位置执行动画
                    if (i == mPosition - 1) {
                        //蓝色开始绘制的地方,
                        float endX = lastWidth + iconSize + mAnimationWidth * (mCount / ANIMATION_INTERVAL);
                        //绘制蓝色
                        canvas.drawRect(lastWidth + iconSize, mLeftY, endX, mRightY, mCompletedLinePaint);
                        //绘制灰色
                        canvas.drawRect(endX, mLeftY, lastWidth + iconSize + mLineWidth,
                                mRightY, mUnCompletedLinePaint);
                    } else {
                        canvas.drawRect(lastWidth + iconSize, mLeftY, lastWidth + iconSize + mLineWidth,
                                mRightY, mUnCompletedLinePaint);
                    }
                }
            }

            //绘制圆形
            if (bean.getState() == StepBean.STEP_COMPLETED) {
                //完成
                canvas.drawCircle(lastWidth + iconSize / 2,
                        mCenterY, iconSize / 2,
                        mCompletedCirclePaint);
            } else {
                //未完成
                canvas.drawCircle(lastWidth + iconSize / 2,
                        mCenterY, iconSize / 2,
                        mUnCompletedCirclePaint);
            }

            //绘制天数
            float textX;
            if (i == mPosition) {
                textX = lastWidth + (mBigIconSize - mSmallIconSize) / 2;
            } else {
                textX = lastWidth;
            }

            if (bean.getState() == StepBean.STEP_COMPLETED) {
                canvas.drawText(bean.getNumber() + "天",
                        textX,
                        mCenterY + mTextPadding,
                        mCompletedTextPaint);
            } else {
                canvas.drawText(bean.getNumber() + "天",
                        textX,
                        mCenterY + mTextPadding,
                        mUnCompletedTextPaint);
            }

            //绘制UP
            int drawId = bean.getDrawId();
            if (drawId != 0) {
                //需要UP才进行绘制
                Rect rectUp =
                        new Rect((int) (lastWidth + iconSize / 2 - mUpWidth / 2),
                                (int) (mCenterY - mUpPadding - mUpHeight),
                                (int) (lastWidth + iconSize / 2 + mUpWidth / 2),
                                (int) (mCenterY - mUpPadding));

                Drawable mUpIcon = getResources().getDrawable(drawId);
                mUpIcon.setBounds(rectUp);
                mUpIcon.draw(canvas);
            }
            lastWidth += iconSize + mLineWidth;
        }

        //记录重绘次数
        mCount = mCount + ANIMATION_INTERVAL;
        if (mCount <= ANIMATION_TIME) {
            //引起重绘
            postInvalidate();
        } else {
            //重绘完成
            mStepBeanList.get(mPosition).setState(StepBean.STEP_COMPLETED);
            isAnimation = false;
            mCount = 0;
            postInvalidate();
            if (callback != null) {
                callback.onStepSuccess();
                callback = null;
            }
        }
    }

    /**
     * 绘制初始状态的view
     */
    @SuppressLint("DrawAllocation")
    private void drawUnSign(Canvas canvas) {
        //上一次的宽度
        float lastWidth = 0;
        for (int i = 0; i < mStepBeanList.size(); i++) {
            StepBean bean = mStepBeanList.get(i);

            //图标大小
            float iconSize;
            //首先判断是否是正在进行中
            if (i == mPosition) {
                iconSize = mBigIconSize;
            } else {
                iconSize = mSmallIconSize;
            }

            //绘制线段
            if (i != mStepBeanList.size() - 1) {
                //最后一条不需要绘制
                if (mStepBeanList.get(i + 1).getState() == StepBean.STEP_COMPLETED) {
                    //下一个是已完成，当前才需要绘制蓝色
                    canvas.drawRect(lastWidth + iconSize, mLeftY, lastWidth + iconSize + mLineWidth, mRightY,
                            mCompletedLinePaint);
                } else {
                    //其余绘制灰色
                    canvas.drawRect(lastWidth + iconSize, mLeftY, lastWidth + iconSize + mLineWidth, mRightY,
                            mUnCompletedLinePaint);
                }
            }

            //绘制圆形
            if (bean.getState() == StepBean.STEP_COMPLETED) {
                //完成
                canvas.drawCircle(lastWidth + iconSize / 2,
                        mCenterY, iconSize / 2,
                        mCompletedCirclePaint);
            } else {
                //未完成
                canvas.drawCircle(lastWidth + iconSize / 2,
                        mCenterY, iconSize / 2,
                        mUnCompletedCirclePaint);
            }

            //绘制天数
            float textX;
            if (i == mPosition) {
                textX = lastWidth + (mBigIconSize - mSmallIconSize) / 2;
            } else {
                textX = lastWidth;
            }

            if (bean.getState() == StepBean.STEP_COMPLETED) {
                canvas.drawText(bean.getNumber() + "天",
                        textX,
                        mCenterY + mTextPadding,
                        mCompletedTextPaint);
            } else {
                canvas.drawText(bean.getNumber() + "天",
                        textX,
                        mCenterY + mTextPadding,
                        mUnCompletedTextPaint);
            }

            //绘制UP
            int drawId = bean.getDrawId();
            if (drawId != 0) {
                //需要UP才进行绘制
                Rect rectUp =
                        new Rect((int) (lastWidth + iconSize / 2 - mUpWidth / 2),
                                (int) (mCenterY - mUpPadding - mUpHeight),
                                (int) (lastWidth + iconSize / 2 + mUpWidth / 2),
                                (int) (mCenterY - mUpPadding));

                Drawable mUpIcon = getResources().getDrawable(drawId);
                mUpIcon.setBounds(rectUp);
                mUpIcon.draw(canvas);
            }
            lastWidth += iconSize + mLineWidth;
        }
    }

    /**
     * 设置流程步数
     *
     * @param stepsBeanList 流程步数
     */
    public void setStepNum(List<StepBean> stepsBeanList, int today) {
        if (stepsBeanList == null) {
            return;
        }
        mStepBeanList = stepsBeanList;
        for (int i = 0; i < mStepBeanList.size(); i++) {
            if (mStepBeanList.get(i).getNumber() == today) {
                mPosition = i;
                break;
            }
        }

        //引起重绘
        postInvalidate();
    }

    /**
     * 执行签到动画
     */
    public void startSignAnimation(StepCallback callback) {
        this.callback = callback;

        //线条从灰色变为蓝色
        isAnimation = true;
        //引起重绘
        postInvalidate();
    }

}
