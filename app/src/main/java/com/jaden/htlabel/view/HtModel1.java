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
import com.jaden.htlabel.utils.LabelUtil;

/**
 * Created Date: 2018/12/14
 * Description:
 */
public class HtModel1 extends HtModel{

    @Override
    public Label createLabel(MotionEvent event) {
        Label label = new Label();
        label.setBackgroundColor(Color.parseColor("#e75420"));
        label.setSpaceX(DensityUtil.dp2px(3));
        label.setSpaceY(DensityUtil.dp2px(3));
        label.setTextColor(Color.parseColor("#FFFFFF"));
        label.setTextSize((int) DensityUtil.dp2px(20));
        label.setText("");
        label.setDate(DateUtil.getFormatDate());
        label.setDateSize((int) DensityUtil.dp2px(13));
        label.setDateColor(Color.parseColor("#FFFFFF"));
        label.setX(event.getX() - LabelUtil.measureLabelDateW(label) / 2);//绘制中间的位置为触摸的位置
        label.setY(event.getY());
        return label;
    }

    @Override
    public Model createModel(RectF rectF) {
        this.rectF.set(rectF);
        Model model = new Model();
        model.setRectF(rectF);
        model.setBgColor(Color.parseColor("#e75420"));
        model.setTextColor(Color.parseColor("#FFFFFF"));
        model.setTextSize((int) DensityUtil.dp2px(20));
        model.setText("胡兔");
        return model;
    }
}
