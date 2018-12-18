package com.jaden.htlabel.bean;

import android.graphics.RectF;
import android.view.MotionEvent;


/**
 * Created Date: 2018/12/14
 * Description: 样式
 */
public class Model {
    private RectF background;
    private int bgColor;
    private int textSize;
    private int textColor;
    private String text;


    public int getTextSize() {
        return textSize;
    }

    public int getTextColor() {
        return textColor;
    }


    public int getBgColor() {
        return bgColor;
    }

    public RectF getRectF() {
        return background;
    }

    public String getText() {
        return text;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }


    public void setRectF(RectF rectF) {
        this.background = rectF;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setText(String text) {
        this.text = text;
    }
}
