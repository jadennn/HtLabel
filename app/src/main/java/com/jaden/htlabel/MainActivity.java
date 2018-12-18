package com.jaden.htlabel;

import android.os.Bundle;

import com.jaden.htlabel.bean.Label;
import com.jaden.htlabel.interfaces.HtModel;
import com.jaden.htlabel.utils.ColorUtil;
import com.jaden.htlabel.utils.DateUtil;
import com.jaden.htlabel.utils.DensityUtil;
import com.jaden.htlabel.utils.LabelUtil;
import com.jaden.htlabel.utils.Slog;
import com.jaden.htlabel.utils.SqlUtil;
import com.jaden.htlabel.view.HtLayout;
import com.jaden.htlabel.view.HtModel1;
import com.jaden.htlabel.view.HtModel2;
import com.jaden.htlabel.view.HtModel3;
import com.jaden.htlabel.view.HtModel4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity {
    HtLayout mHtLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHtLayout = findViewById(R.id.ht_layout);
        SqlUtil.get().init(getApplicationContext());
        init();
    }

    private void init(){
        List<Label> list = (List<Label>)SqlUtil.get().getObject(SqlUtil.LABEL_LIST);
        if(list != null){
            LabelUtil.clearEditable(list);
            mHtLayout.setLabels(list);
        }

        HtModel model1 = new HtModel1();
        HtModel model2 = new HtModel2();
        HtModel model3 = new HtModel3();
        HtModel model4 = new HtModel4();
        List<HtModel> models = new ArrayList<>();

        models.add(model4);
        models.add(model1);
        models.add(model3);
        models.add(model2);
        mHtLayout.setModels(models);
    }

    @Override
    protected void onDestroy() {
        mHtLayout.detachView();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mHtLayout.detachView();
        super.onPause();
    }
}
