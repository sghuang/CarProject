package com.littleant.carrepair.demo;

import android.os.Bundle;
import android.view.View;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

/**
 * 个人中心
 */
public class UserCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_user_center;
    }

    @Override
    public void onClick(View v) {

    }
}
