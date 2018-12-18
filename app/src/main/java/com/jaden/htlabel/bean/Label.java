package com.jaden.htlabel.bean;


import android.graphics.RectF;
import android.view.MotionEvent;

import com.jaden.htlabel.utils.Slog;

import java.io.Serializable;


/**
 * Created Date: 2018/12/13
 * Description: label
 */
public class Label implements Serializable{
    private int id;

    private String text; //内容
    private int textSize;
    private int textColor;

    private String date; //日期
    private int dateSize;
    private int dateColor;

    private float x;
    private float y;
    private float w;
    private float h;
    private int zOrder; //z方向的顺序
    private float spaceX;
    private float spaceY;
    private RectF background = new HtRectF();

    private int backgroundColor;

    private boolean editable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public float getSpaceX() {
        return spaceX;
    }

    public float getSpaceY() {
        return spaceY;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setSpaceX(float spaceX) {
        this.spaceX = spaceX;
    }

    public void setSpaceY(float spaceY) {
        this.spaceY = spaceY;
    }

    public void setText(String text) {
        Slog.i("text = " + text);
        this.text = text;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getzOrder() {
        return zOrder;
    }

    public void setzOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public void setBackground(RectF background) {
        this.background.set(background);
    }

    public RectF getBackground() {
        return background;
    }

    public float getH() {
        return h;
    }

    public float getW() {
        return w;
    }

    public void setH(float h) {
        this.h = h;
    }

    public void setW(float w) {
        this.w = w;
    }

    public void setEditable(boolean flag){
        this.editable = flag;
    }

    public boolean getEditable(){
        return editable;
    }

    public int getDateColor() {
        return dateColor;
    }

    public int getDateSize() {
        return dateSize;
    }

    public void setDateColor(int dateColor) {
        this.dateColor = dateColor;
    }

    public void setDateSize(int dateSize) {
        this.dateSize = dateSize;
    }

}
