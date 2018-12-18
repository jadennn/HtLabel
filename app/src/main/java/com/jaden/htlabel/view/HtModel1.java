package com.jaden.htlabel.view;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.jaden.htlabel.bean.Label;
import com.jaden.htlabel.bean.Model;
import com.jaden.htlabel.interfaces.HtModel;
import com.jaden.htlabel.utils.DateUtil;
import com.jaden.htlabel.utils.DensityUtil;

/**
 * Created Date: 2018/12/14
 * Description:
 */
public class HtModel1 extends HtModel{

    @Override
    public Label createLabel(MotionEvent event) {
        Label label = new Label();
        label.setBackgroundColor(Color.parseColor("#F04E7B"));
        label.setSpaceX(DensityUtil.dp2px(3));
        label.setSpaceY(DensityUtil.dp2px(3));
        label.setTextColor(Color.parseColor("#FFFFFF"));
        label.setTextSize((int) DensityUtil.dp2px(20));
        label.setText("");
        label.setX(event.getX());
        label.setY(event.getY());
        label.setDate(DateUtil.getFormatDate());
        label.setDateSize((int) DensityUtil.dp2px(13));
        label.setDateColor(Color.parseColor("#653453"));
        return label;
    }

    @Override
    public Model createModel(RectF rectF) {
        this.rectF.set(rectF);
        Model model = new Model();
        model.setRectF(rectF);
        model.setBgColor(Color.parseColor("#F04E7B"));
        model.setTextColor(Color.parseColor("#FFFFFF"));
        model.setTextSize((int) DensityUtil.dp2px(25));
        model.setText("胡兔");
        return model;
    }
}