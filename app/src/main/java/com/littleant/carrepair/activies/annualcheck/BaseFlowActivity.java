package com.littleant.carrepair.activies.annualcheck;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

public abstract class BaseFlowActivity extends BaseActivity {

    protected View include2;

    //代驾年检控件
    protected CheckBox lacf_tv_fill, lacf_tv_pick, lacf_tv_start, lacf_tv_return;
    protected TextView lacf_tv_check_know;

    //自驾年检控件
    protected CheckBox locf_tv_fill, locf_tv_start;
    protected TextView locf_tv_check_know;

    @Override
    protected void init() {
        super.init();
        lacf_tv_fill = findViewById(R.id.lacf_tv_fill);
        lacf_tv_pick = findViewById(R.id.lacf_tv_pick);
        lacf_tv_start = findViewById(R.id.lacf_tv_start);
        lacf_tv_return = findViewById(R.id.lacf_tv_return);
        lacf_tv_check_know = findViewById(R.id.lacf_tv_check_know);

        locf_tv_fill = findViewById(R.id.locf_tv_fill);
        locf_tv_start = findViewById(R.id.locf_tv_start);
        locf_tv_check_know = findViewById(R.id.locf_tv_check_know);
    }

}
