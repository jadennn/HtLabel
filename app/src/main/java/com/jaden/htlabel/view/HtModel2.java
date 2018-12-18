package com.jaden.htlabel.view;

import android.graphics.Color;
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
public class HtModel2 extends HtModel{

    @Override
    public Label createLabel(MotionEvent event) {
        Label label = new Label();
        label.setBackgroundColor(Color.parseColor("#3EE6c2"));
        label.setSpaceX(DensityUtil.dp2px(4));
        label.setSpaceY(DensityUtil.dp2px(5));
        label.setTextColor(Color.parseColor("#a33253"));
        label.setTextSize((int) DensityUtil.dp2px(18));
        label.setText("");
        label.setX(event.getX());
        label.setY(event.getY());
        label.setDate(DateUtil.getFormatDate());
        label.setDateSize((int) DensityUtil.dp2px(12));
        label.setDateColor(Color.parseColor("#864532"));
        return label;
    }

    @Override
    public Model createModel(RectF rectF) {
        this.rectF.set(rectF);
        Model model = new Model();
        model.setRectF(rectF);
        model.setBgColor(Color.parseColor("#3EE6c2"));
        model.setTextColor(Color.parseColor("#a33253"));
        model.setTextSize((int) DensityUtil.dp2px(25));
        model.setText("胡兔");
        return model;
    }
}
