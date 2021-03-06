package com.jaden.htlabel.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Toast;

import com.jaden.htlabel.bean.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class LabelUtil {
    /**
     * description: 根据点击区域获取label列表
     **/
    private static List<Label> getRegionLabels(List<Label> labels, MotionEvent event) {
        if(labels != null) {
            List<Label> regionLabels = new ArrayList<>();
            for (Label label : labels) {
                //Slog.i(label.toString() + " ====> left = " + label.getBackground().left + " right = " + label.getBackground().right + " top = " + label.getBackground().top + " bottom = " + label.getBackground().bottom);
                if (RectUtil.inRegion(label.getBackground(), event)) {
                    regionLabels.add(label);
                }
            }
            return regionLabels;
        }
        return null;
    }

    /**
     * description: 根据zOrder获取最上层的label
     **/
    private static Label getSelectedLabels(List<Label> regions) {
        if(regions != null) {
            Label label = null;
            int size = regions.size();
            for (int i = 0; i < size; i++) {
                Label l = regions.get(i);
                if (i == 0) {
                    label = l;
                } else {
                    if (label.getzOrder() < l.getzOrder()) {
                        label = l;
                    }
                }
            }
            return label;
        }
        return null;
    }

    /**
     * description: 获取点击区域的label
     **/
    public static Label getSelectedLabels(List<Label> labels, MotionEvent event) {
        List<Label> regionLabels = getRegionLabels(labels, event);
        return getSelectedLabels(regionLabels);
    }

    /**
     * description: 判断一个label是否在Rect范围内
     **/
    public static boolean isOutOfView(RectF rect, Label label) {
        RectF background = label.getBackground();
        float w = label.getW();
        float h = label.getH();
        //为了方便操作，如果有一半超出view也算超出view
        if (background.right - w/2 <= rect.left || background.left +w/2 >= rect.right
                || background.bottom - h/2 <= rect.top || background.top + h/2 >= rect.bottom) {
            return true;
        }
        return false;
    }

    /**
    *description: 根据label初始化rect，并保存在label的background里面
    **/
    public static void setRectFByLabel(RectF rect, Label label) {
        rect.set(label.getX(), label.getY(), label.getX() + label.getW(), label.getY() + label.getH());
        //将区域保存在label中
        label.setBackground(rect);
    }

    /**
    *description: 清掉所有label可编辑标记
    **/
    public static void clearEditable(List<Label> labels) {
        for (Label label : labels) {
            label.setEditable(false);
        }
    }

    public static float measureLabelDateW(Label label){
        Rect dateBonds = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(label.getDateSize());
        paint.getTextBounds(label.getDate(), 0, label.getDate().length(), dateBonds);
        float dateWidth = dateBonds.right-dateBonds.left;
        return label.getSpaceX()*2 + dateWidth; //space + max(date, text) + space
    }
}
