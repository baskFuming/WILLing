package com.xxx.willing.ui.my.activity.sign.view;

/**
 * description: 签到bean.
 *
 * @author Sorgs.
 * @date 2018/8/17.
 */
public class StepBean {
    /**
     * 未完成
     */
    public static final int STEP_UNDO = -1;
    /**
     * 已完成
     */
    public static final int STEP_COMPLETED = 1;

    private int state;
    private int number;
    private int drawId;

    public StepBean(int state, int number, int drawId) {
        this.state = state;
        this.number = number;
        this.drawId = drawId;
    }

    public StepBean(int state, int number) {
        this.state = state;
        this.number = number;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    public int getDrawId() {
        return drawId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
