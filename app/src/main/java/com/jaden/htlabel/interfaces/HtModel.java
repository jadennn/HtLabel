package com.jaden.htlabel.interfaces;

import android.graphics.RectF;
import android.view.MotionEvent;

import com.jaden.htlabel.bean.Label;
import com.jaden.htlabel.bean.Model;

/**
 * Created Date: 2018/12/14
 * Description: 样式布局
 */
public abstract class HtModel {
    public RectF rectF = new RectF();
    public abstract Label createLabel(MotionEvent event); //创建label
    public abstract Model createModel(RectF rectF); //创建Model
    public RectF getRectF(){
        return rectF;
    }
}
