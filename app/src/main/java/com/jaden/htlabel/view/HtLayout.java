package com.jaden.htlabel.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.jaden.htlabel.bean.Label;
import com.jaden.htlabel.bean.Model;
import com.jaden.htlabel.input.HtLayoutInput;
import com.jaden.htlabel.interfaces.HtModel;
import com.jaden.htlabel.utils.DensityUtil;
import com.jaden.htlabel.utils.KeyBoardUtil;
import com.jaden.htlabel.utils.LabelUtil;
import com.jaden.htlabel.utils.ModelUtil;
import com.jaden.htlabel.utils.PointUtil;
import com.jaden.htlabel.utils.RectUtil;
import com.jaden.htlabel.utils.SqlUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created Date: 2018/12/13
 * Description:
 */
public class HtLayout extends View{
    private static final int COLOR_EDIT = Color.parseColor("#45344533");
    private static final float MODEL_HEIGHT = DensityUtil.dp2px(50); //50dp
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //整个布局分为两个部分，一个是专门用来展示label的范围，一个是专门用来展示样式的范围
    private RectF mLabelRect = new RectF(); //label的范围
    private RectF mModelRect = new RectF(); //model的范围

    private Rect mDirtyRect = new Rect(); //绘制的脏区域

    private List<Label> labels = new LinkedList<>();//由于便签经常会被删除及新增，用LinkedList提升效率
    private Label selectedLabel = null;

    private List<HtModel> models = new ArrayList<>();
    private HtModel selectModel = null;

    private RectF mBackground = new RectF(); //label的范围
    private Rect mTextBonds = new Rect(); //label文字的范围
    private Rect mDateBonds = new Rect(); //label时间的范围

    private PointF currentPoint = new PointF(); //当前的touch区域
    private PointF lastPoint = new PointF(); //上一次的touch区域

    private boolean moveFlag = false; //选中的label是否可以移动
    private boolean clickFlag = false; //选中的label是否点击

    HtLayoutInput ic;

    public HtLayout(Context context) {
        super(context);
    }

    public HtLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HtLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
    *description: 绘制label列表
    **/
    public void setLabels(List<Label> labels) {
        if(labels != null) {
            this.labels.clear();
            this.labels.addAll(labels);
            invalidate();
        }
    }

    /**
    *description: 保存当前选中的label
    **/
    private void setLabel(Label label) {
        this.selectedLabel = label;
    }

    /**
    *description: 绘制HtModel列表
    **/
    public void setModels(List<HtModel> models) {
        if(models != null) {
            this.models.clear();
            this.models.addAll(models);
            invalidate();
        }
    }

    /**
    *description: 移动label，刷新单个label，提升效率
    **/
    private void moveLabel(Label label, PointF current, PointF last){
        float oldX = label.getX();
        float oldY = label.getY();
        float newX = oldX + current.x - last.x;
        float newY = oldY + current.y - last.y;
        label.setX(newX);
        label.setY(newY);
        invalidate(label);
    }

    /**
    *description: 点击label事件
    **/
    private void clickLabel(Label label){
        label.setEditable(true);
        invalidate(label);

        //输入法相关，使其能够与view建立联系
        if(ic != null && selectedLabel != null){
            ic.setLabel(selectedLabel);
        }
        KeyBoardUtil.toggleSoftInput(this);
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
    }

    /**
    *description: 绘制单个label
    **/
    private void drawLabel(Label label, Canvas canvas){
        mPaint.setColor(label.getBackgroundColor());
        mPaint.setStyle(Paint.Style.FILL);
        //求取一些参数
        mPaint.setTextSize(label.getTextSize());
        mPaint.getTextBounds(label.getText(), 0, label.getText().length(), mTextBonds);
        float textWidth = mTextBonds.right-mTextBonds.left;
        float textHeight = mTextBonds.bottom-mTextBonds.top;
        mPaint.setTextSize(label.getDateSize());
        mPaint.getTextBounds(label.getDate(), 0, label.getDate().length(), mDateBonds);
        float dateWidth = mDateBonds.right-mDateBonds.left;
        float dateHeight = mDateBonds.bottom-mDateBonds.top;
        float wMax = Math.max(textWidth, dateWidth);
        //绘制背景
        float w = label.getSpaceX()*2 + wMax; //space + max(date, text) + space
        float h = label.getSpaceY()*3 + dateHeight + textHeight; //space + date + space + text + space
        label.setW(w);
        label.setH(h);
        LabelUtil.setRectFByLabel(mBackground, label);
        canvas.drawRect(mBackground, mPaint);
        //绘制日期
        mPaint.setColor(label.getDateColor());
        canvas.drawText(label.getDate(), mBackground.left + (wMax-dateWidth)/2 + label.getSpaceX(), mBackground.top + label.getSpaceY() - mDateBonds.top, mPaint);
        //绘制文字
        mPaint.setColor(label.getTextColor());
        mPaint.setTextSize(label.getTextSize());
        float textOffsetX = mBackground.left + (wMax-textWidth)/2 + label.getSpaceX();
        float textOffsetY = mBackground.bottom - label.getSpaceY() - mTextBonds.bottom;
        canvas.drawText(label.getText(), textOffsetX, textOffsetY, mPaint);
        //绘制可编辑的蒙版
        if(label.getEditable()){ //如果可编辑，那么就将文字表面附上一层蒙版
            mPaint.setColor(COLOR_EDIT);
            canvas.drawRect(mTextBonds.left + textOffsetX, mTextBonds.top + textOffsetY,
                    mTextBonds.right + textOffsetX, mTextBonds.bottom + textOffsetY, mPaint);
        }
    }

    /**
    *description: 绘制所有的label
    **/
    private void drawLabels(List<Label> labels, Canvas canvas){
        for(int i=0; i<labels.size(); i++){
            Label label = labels.get(i);
            label.setzOrder(i);
            drawLabel(label, canvas);
        }
    }

    /**
    *description: 绘制所有的样式
    **/
    private void drawModels(List<HtModel> htModels, Canvas canvas){
        int size = htModels.size();
        if(size > 0) {
            float w = getWidth() * 1.0f / size;
            float h = MODEL_HEIGHT;
            for (int i = 0; i < size; i++) {
                HtModel model = htModels.get(i);
                drawModel(model.createModel(new RectF(i*w, 0, (i+1)*w, MODEL_HEIGHT)), canvas);
            }
        }
    }

    /**
    *description: 绘制单个样式
    **/
    private void drawModel(Model model, Canvas canvas){
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(model.getBgColor());
        canvas.drawRect(model.getRectF(), mPaint);
        mPaint.setTextSize(model.getTextSize());
        mPaint.setColor(model.getTextColor());
        mPaint.getTextBounds(model.getText(), 0, model.getText().length(), mTextBonds);
        float x = model.getRectF().left + ((model.getRectF().right - model.getRectF().left) - (mTextBonds.right - mTextBonds.left))/2 + mTextBonds.left;
        float y = model.getRectF().top + ((model.getRectF().bottom - model.getRectF().top) - (mTextBonds.bottom - mTextBonds.top))/2 - mTextBonds.top;
        canvas.drawText(model.getText(), x, y, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mLabelRect.set(0, MODEL_HEIGHT, getWidth(), getHeight());
        mModelRect.set(0, 0, getWidth(), MODEL_HEIGHT);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(RectUtil.inRegion(mLabelRect, event)) {//点击label区域
                        if (selectedLabel != null) {
                            selectedLabel.setEditable(false);
                        }
                        selectedLabel = LabelUtil.getSelectedLabels(labels, event);
                        if (selectedLabel != null) {
                            PointUtil.motion2point(lastPoint, event);
                            moveFlag = true;
                            clickFlag = true;
                        } else {
                            KeyBoardUtil.hideKeyboard(this);
                        }
                    }else if(RectUtil.inRegion(mModelRect, event)){ //HTModel区域
                        selectModel = ModelUtil.getRegionHtModel(models, event);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (moveFlag && selectedLabel != null) {
                        PointUtil.motion2point(currentPoint, event);
                        if (PointUtil.isMove(currentPoint, lastPoint, 5)) { //判断是否移动
                            clickFlag = false;
                            moveLabel(selectedLabel, currentPoint, lastPoint);
                            lastPoint.set(currentPoint);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (moveFlag && selectedLabel != null) {
                        if (clickFlag) {//点击标签事件
                            clickLabel(selectedLabel);
                        } else {//移动标签事件
                            if (LabelUtil.isOutOfView(mLabelRect, selectedLabel)) { //移到View外
                                if(labels.remove(selectedLabel)) {
                                }
                                invalidate();
                            }
                        }
                        SqlUtil.get().saveObject(this.labels, SqlUtil.LABEL_LIST);
                    }
                    if(selectModel != null && !RectUtil.inRegion(mModelRect, event)){ //如果手指离开的时候，不在HTModel区域，那么就创建一个label标签
                        Label label = selectModel.createLabel(event);
                        labels.add(label);
                        invalidate();
                        SqlUtil.get().saveObject(this.labels, SqlUtil.LABEL_LIST);
                    }
                    selectModel = null;
                    moveFlag = false;
                    break;
            }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.models != null) {
            drawModels(this.models, canvas);
        }
        if(this.labels != null) {
            drawLabels(this.labels, canvas);

        }
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI;
        outAttrs.inputType = InputType.TYPE_NULL;
        ic = new HtLayoutInput(this, true);
        ic.setLabel(selectedLabel); //初始化的时候绑定Label
        ic.setListener(new HtLayoutInput.Listener() {
            @Override
            public void onInputComplete() {
                invalidate(selectedLabel);
                SqlUtil.get().saveObject(labels, SqlUtil.LABEL_LIST);
            }

            @Override
            public void onDelComplete() {
                invalidate(selectedLabel);
                SqlUtil.get().saveObject(labels, SqlUtil.LABEL_LIST);
            }
        });
        return ic;
    }

    /**
    *description: 局部更新
    **/
    private void invalidate(Label label){
        LabelUtil.setRectFByLabel(mBackground, label);
        mBackground.roundOut(mDirtyRect); //确保脏区域比矩形区域大
        invalidate(mDirtyRect);
    }

    /**
    *description: 隐藏keyboard
    **/
    public void detachView(){
        KeyBoardUtil.hideKeyboard(this);
    }

}
