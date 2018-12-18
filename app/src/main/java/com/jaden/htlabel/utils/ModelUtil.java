package com.jaden.htlabel.utils;

import android.view.MotionEvent;

import com.jaden.htlabel.bean.Model;
import com.jaden.htlabel.interfaces.HtModel;

import java.util.List;

/**
 * Created Date: 2018/12/14
 * Description:
 */
public class ModelUtil {
    /**
     * description: 根据点击区域获取Model
     **/
    public static Model getRegionModel(List<Model> models, MotionEvent event) {
        for (Model model : models) {
             if (RectUtil.inRegion(model.getRectF(), event)) {
                return model;
            }
        }
        return null;
    }

    /**
     * description: 根据点击区域获取Model
     **/
    public static HtModel getRegionHtModel(List<HtModel> htModels, MotionEvent event) {
        for (HtModel model : htModels) {
            if (RectUtil.inRegion(model.getRectF(), event)) {
                return model;
            }
        }
        return null;
    }
}
